package pt.ads.server.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.stereotype.Service;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLResultValue;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import pt.ads.server.algorithm.AlgorithmDefaults;
import pt.ads.server.algorithm.AlgorithmFactory;
import pt.ads.server.algorithm.ProblemFactory;
import pt.ads.server.dto.*;
import pt.ads.server.exceptions.AlgorithmException;
import pt.ads.server.exceptions.AlgorithmExecutionException;
import pt.ads.server.exceptions.AlgorithmInputsException;
import pt.ads.server.owl.OWLQueryBuilder;

@Service
@Slf4j
@SuppressWarnings("FieldCanBeLocal")
public class AlgorithmServiceImpl implements AlgorithmService {

	private final AlgorithmDefaults algorithmDefaults;

	private final OwlService owlService;
	private final OWLOntology owlOntology;
	private final SQWRLQueryEngine owlQueryEngine;


    public AlgorithmServiceImpl(AlgorithmDefaults algorithmDefaults, OwlService owlService) throws IOException, OWLOntologyCreationException {
		this.algorithmDefaults = algorithmDefaults;
		this.owlService = owlService;

        this.owlOntology = owlService.loadOntology();
        this.owlQueryEngine = owlService.loadQueryEngine(this.owlOntology);
    }

	@Override
	public <T extends Solution<?>> Experiment<T, List<T>> getAlgorithms(AlgorithmInputs inputs) throws AlgorithmException {
		fillDefaults(inputs);

    	if (inputs.objectives.isEmpty())
    		throw new AlgorithmInputsException("You must specify at least one objective");

		Problem<T> problem = ProblemFactory.getProblem(inputs.type, inputs.variables, inputs.objectives);
		log.debug("Problem: {}", problem);

		Collection<String> algorithmNames = findBestAlgorithms(inputs);
		log.debug("Possible algorithms: {}", algorithmNames);

		List<Algorithm<List<T>>> algorithms = initializeAlgorithms(algorithmNames, inputs.options, problem);

		if (algorithms.isEmpty())
			throw new AlgorithmExecutionException("No appropriate algorithm implementation found for " + algorithmNames);

		return new Experiment<>(problem, algorithms, algorithmNames);
	}

	@NonNull
	private <T extends Solution<?>> List<Algorithm<List<T>>> initializeAlgorithms(Collection<String> algorithmNames, AlgorithmOptions options, Problem<T> problem) {
		List<Algorithm<List<T>>> algorithms = new ArrayList<>(0);

    	for (String name : algorithmNames) {
			try {
				Algorithm<List<T>> algorithmReflection = AlgorithmFactory.getAlgorithmReflection(name, options, problem);
				if (algorithmReflection != null)
					algorithms.add(algorithmReflection);
			} catch (Exception e) {
				log.warn("Unable to instantiate algorithm though reflection: {}", name, e);
			}

			try {
				Algorithm<List<T>> algorithmFallback = AlgorithmFactory.getAlgorithmFallback(name, options, problem);
				if (algorithmFallback != null)
					algorithms.add(algorithmFallback);
			} catch (Exception e) {
				log.warn("Unable to instantiate algorithm though fallback: {}", name, e);
			}
		}

		return algorithms;
	}

	private Collection<String> findBestAlgorithms(AlgorithmInputs inputs) throws AlgorithmException {
		try {
			List<String> algorithmNames = findAlgorithmsOWL(inputs);
			log.debug("Found suitable algorithms: " + algorithmNames);

			if (algorithmNames.isEmpty())
				throw new AlgorithmExecutionException("No appropriate algorithm found based on your input");

			return algorithmNames;
		} catch (SQWRLException | SWRLParseException e) {
			throw new AlgorithmExecutionException("Error querying knowledge database for user input", e);
		}
	}

	private List<String> findAlgorithmsOWL(AlgorithmInputs inputs) throws SQWRLException, SWRLParseException {
		String query = new OWLQueryBuilder()
				.minObjectives(inputs.objectives.size())
				.maxObjectives(inputs.objectives.size())
				.heavyProcessing(inputs.options.heavyProcessing)
				.build();

		SQWRLResult result = owlService.executeQuery(query, owlQueryEngine);
		List<SQWRLResultValue> results = result.getColumn("alg");

		return results.stream()
				.map(Object::toString)
				.map(AlgorithmServiceImpl::normalize)
				.collect(Collectors.toList());
	}

	private static String normalize(String value) {
		if (value == null)
			return "";

		if (value.startsWith(":"))
			value = value.substring(1);

		return value
				.trim()
				.toUpperCase();
	}

	@Override
	public <T extends Solution<?>> AlgorithmListResults<T, List<T>> executeAlgorithms(AlgorithmInputs inputs, Experiment<T, List<T>> experiment) {
    	// Execute the algorithms one by one until once succeeds, and then fetch it's results
		for (Algorithm<List<T>> algorithm : experiment.algorithms) {
			try {
				algorithm.run();
				List<T> results = algorithm.getResult();

				// TODO: Flip objective if it should NOT be minimized (use this: experiment.problem.minimizeObjective(i))
				// TODO: Remove the solutions that are worse in every objective - leave only the best solution or the solutions that offer compromises (e.g. faster production, but more expensive)

				return new AlgorithmListResults<>(inputs, experiment.problem, new AlgorithmResults<>(algorithm, results));
			} catch (Exception ignored) { }
		}

		// Failed executing all algorithms
		log.warn("Unable to execute all instances of the algorithms: {}", experiment.algorithms);
		return new AlgorithmListResults<>(inputs, experiment.problem, null);
	}

	private void fillDefaults(@NonNull AlgorithmInputs inputs) {
		if (inputs.objectives == null)
			inputs.objectives = new ArrayList<>(0);

		if (inputs.options == null)
			inputs.options = new AlgorithmOptions();

		AlgorithmOptions options = inputs.options;

		if (options.populationSize == null)
			options.populationSize = algorithmDefaults.populationSize;
		if (options.iterations == null)
			options.iterations = algorithmDefaults.iterations;

		if (options.crossoverProbability == null)
			options.crossoverProbability = algorithmDefaults.crossoverProbability;
		if (options.crossoverDistributionIndex == null)
			options.crossoverDistributionIndex = algorithmDefaults.crossoverDistributionIndex;

		if (options.mutationProbability == null)
			options.mutationProbability = algorithmDefaults.mutationProbability;
		if (options.mutationDistributionIndex == null)
			options.mutationDistributionIndex = algorithmDefaults.mutationDistributionIndex;
	}

}
