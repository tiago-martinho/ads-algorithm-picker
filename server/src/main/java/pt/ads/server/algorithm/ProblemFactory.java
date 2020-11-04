package pt.ads.server.algorithm;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
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
	public static <T> Problem<T> getProblem(@NonNull VariableType type, @NonNull Collection<Variable> variables, @NonNull Collection<String> objectives)
			throws AlgorithmExecutionException {

		log.debug("Creating problem for type: " + type);

		switch (type) {
		case DOUBLE:
			return (Problem<T>) new DoubleProblem(variables, objectives);
		case INTEGER:
			return (Problem<T>) new IntegerProblem(variables, objectives);
		case BOOLEAN:
			return (Problem<T>) new BinaryProblem(variables, objectives);
		default:
			throw new AlgorithmExecutionException("No problem type found for type " + type);
		}
	}

}
