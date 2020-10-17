package pt.ads.server.services;

import lombok.extern.slf4j.Slf4j;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.Kursawe;
import org.uma.jmetal.solution.DoubleSolution;
import pt.ads.server.dto.AlgorithmResults;
import pt.ads.server.dto.Experiment;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class AlgorithmServiceImpl implements AlgorithmService {


	@Value("${algorithm.population_size}")
	private int populationSize;

	@Value("${algorithm.max_evaluations}")
	private int maxEvaluations;


    private final OwlService owlService;

	private final OWLOntology owlOntology;
	private final SQWRLQueryEngine owlQueryEngine;


    public AlgorithmServiceImpl(OwlService owlService) throws IOException, OWLOntologyCreationException {
        this.owlService = owlService;

        this.owlOntology = owlService.loadOntology();
        this.owlQueryEngine = owlService.loadQueryEngine(this.owlOntology);
    }

    @Override
	public Experiment<DoubleSolution, List<DoubleSolution>> getAlgorithm() throws SQWRLException, SWRLParseException {
        String classExpression = "canSolve some (isManyObjectiveProblem value true) ^ hasImplementationLanguage(Java)";
        owlService.executeQuery(classExpression, owlQueryEngine); // TODO: find the algorithm


		Problem<DoubleSolution> problem = new Kursawe(2);
		Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(
				problem,
				new SBXCrossover(1.0, 5),
				new PolynomialMutation(1.0 / problem.getNumberOfVariables(), 10.0),
				populationSize
		)
				.setMaxEvaluations(maxEvaluations)
				.build();

		return new Experiment<>(problem, algorithm);
	}

	@Override
	public AlgorithmResults<DoubleSolution, List<DoubleSolution>> getAlgorithmResults(Experiment<DoubleSolution, List<DoubleSolution>> experiment) {
		experiment.algorithm.run();
		return new AlgorithmResults<>(experiment.problem, experiment.algorithm, experiment.algorithm.getResult());
	}

}
