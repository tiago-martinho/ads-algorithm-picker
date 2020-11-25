package pt.ads.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.solution.IntegerSolution;
import pt.ads.server.algorithm.AlgorithmFactory;
import pt.ads.server.algorithm.ProblemFactory;
import pt.ads.server.algorithm.problem.Problem;
import pt.ads.server.dto.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlgorithmTests {

	@Test
	void testReflection() {
		Problem<IntegerSolution> problem = getProblem();
		AlgorithmOptions options = getOptions();

		// GWASFGA -> GWASFGA
		// MOCELL  -> MOCellBuilder
		// NSGAII  -> NSGAIIBuilder
		Collection<String> names = Arrays.asList("NSGAII", "MOCELL", "GWASFGA");

		for (String name : names) {
			Algorithm<List<IntegerSolution>> algorithm = AlgorithmFactory.getAlgorithmReflection(name, options, problem);
			assertNotNull(algorithm);
//			algorithm.run();
		}
	}

	@NotNull
	private Problem<IntegerSolution> getProblem() {
		Collection<Variable> variables = new ArrayList<>(0);
		variables.add(new Variable("Grain quality index", -10.0, 10.0));
		variables.add(new Variable("Grain cost", 50.0, 100.0));

		Collection<Objective> objectives = new ArrayList<>(0);
		objectives.add(new Objective("Quality", ObjectiveGoal.MAXIMIZE));
		objectives.add(new Objective("Cost",    ObjectiveGoal.MINIMIZE));

		Problem<IntegerSolution> problem = ProblemFactory.getProblem(VariableType.INTEGER, variables, objectives);
		assertNotNull(problem);

		return problem;
	}

	@NotNull
	private AlgorithmOptions getOptions() {
		AlgorithmOptions options = new AlgorithmOptions();
		options.populationSize = 100;
		options.iterations = 10;
		options.crossoverProbability       = 0.1;
		options.crossoverDistributionIndex = 10.0;
		options.mutationProbability        = 0.1;
		options.mutationDistributionIndex  = 10.0;
		return options;
	}

}
