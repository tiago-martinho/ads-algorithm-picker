package pt.ads.server.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
	public <T extends Solution<?>> Experiment<T, List<T>> getAlgorithm(AlgorithmInputs inputs) throws AlgorithmException {
		fillDefaults(inputs);

    	if (inputs.numberOfObjectives < 1)
    		throw new AlgorithmInputsException("Number of objectives must be positive");

		Problem<T> problem = ProblemFactory.getProblem(inputs.type, inputs.variables, inputs.numberOfObjectives);

		Collection<String> algorithmNames = getAlgorithmNames(inputs);
		Collection<Algorithm<List<T>>> algorithms = getAlgorithmsFromNames(algorithmNames, inputs.options, problem);

		if (algorithms.isEmpty())
			throw new AlgorithmExecutionException("No appropriate algorithm implementation found for " + algorithmNames);

		return new Experiment<>(problem, algorithms);
	}

	private void fillDefaults(@NotNull AlgorithmInputs inputs) {
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

	private <T extends Solution<?>> Collection<Algorithm<List<T>>> getAlgorithmsFromNames(Collection<String> algorithmNames, AlgorithmOptions options, Problem<T> problem) {
		Collection<Algorithm<List<T>>> algorithms = new ArrayList<>(algorithmNames.size());

		for (String name : algorithmNames) {
			Algorithm<List<T>> algorithm = AlgorithmFactory.getAlgorithm(name, options, problem);
			if (algorithm != null) {
				algorithms.add(algorithm);
			}
		}

		return algorithms;
	}

	private Collection<String> getAlgorithmNames(AlgorithmInputs inputs) throws AlgorithmException {
		try {
			List<String> algorithmNames = getAlgorithmNamesFromOWL(inputs);

			if (algorithmNames.isEmpty())
				throw new AlgorithmExecutionException("No appropriate algorithm found based on your input");

			return algorithmNames;
		} catch (SQWRLException | SWRLParseException e) {
			throw new AlgorithmExecutionException("Error querying knowledge database for user input", e);
		}
	}

	private List<String> getAlgorithmNamesFromOWL(AlgorithmInputs inputs) throws SQWRLException, SWRLParseException {
		String query = new OWLQueryBuilder()
				.minObjectives(inputs.numberOfObjectives)
				.maxObjectives(inputs.numberOfObjectives)
				.heavyProcessing(inputs.heavyProcessing)
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
	public <T extends Solution<?>> AlgorithmListResults<T, List<T>> getAlgorithmResults(AlgorithmInputs inputs, Experiment<T, List<T>> experiment) {
    	// Run all the algorithms in parallel
		Collection<AlgorithmResults<List<T>>> results = experiment.algorithms.parallelStream()
				.peek(Algorithm::run)
				.map(algorithm -> new AlgorithmResults<>(algorithm, algorithm.getResult()))
				.collect(Collectors.toList());

		return new AlgorithmListResults<>(inputs, experiment.problem, results);
	}

}
