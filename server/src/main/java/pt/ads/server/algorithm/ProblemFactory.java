package pt.ads.server.algorithm;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.uma.jmetal.problem.Problem;
import pt.ads.server.algorithm.problems.BinaryProblem;
import pt.ads.server.algorithm.problems.DoubleProblem;
import pt.ads.server.algorithm.problems.IntegerProblem;
import pt.ads.server.dto.Variable;
import pt.ads.server.dto.VariableType;
import pt.ads.server.exceptions.AlgorithmExecutionException;

@Slf4j
public class ProblemFactory {

	@Nullable
	@SuppressWarnings({ "unchecked", "ConstantConditions" })
	public static <T> Problem<T> getProblem(@NotNull VariableType type, @NotNull Collection<Variable> variables, int numberOfObjectives)
			throws AlgorithmExecutionException {

		log.debug("Creating problem for type: " + type);

		switch (type) {
		case DOUBLE:
			return (Problem<T>) new DoubleProblem(variables, numberOfObjectives);
		case INTEGER:
			return (Problem<T>) new IntegerProblem(variables, numberOfObjectives);
		case BOOLEAN:
			return (Problem<T>) new BinaryProblem(variables, numberOfObjectives);
		default:
			throw new AlgorithmExecutionException("No problem type found for type " + type);
		}
	}

}
