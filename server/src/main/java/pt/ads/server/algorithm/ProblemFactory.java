package pt.ads.server.algorithm;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.uma.jmetal.solution.Solution;
import pt.ads.server.algorithm.problem.BinaryProblem;
import pt.ads.server.algorithm.problem.DoubleProblem;
import pt.ads.server.algorithm.problem.IntegerProblem;
import pt.ads.server.algorithm.problem.Problem;
import pt.ads.server.model.Objective;
import pt.ads.server.model.Variable;
import pt.ads.server.model.VariableType;
import pt.ads.server.exceptions.AlgorithmExecutionException;

@Slf4j
public class ProblemFactory {

	@Nullable
	@SuppressWarnings({ "unchecked", "ConstantConditions" })
	public static <S extends Solution<?>> Problem<S> getProblem(@NonNull VariableType type, @NonNull Collection<Variable> variables, @NonNull Collection<Objective> objectives)
			throws AlgorithmExecutionException {

		log.debug("Creating problem for type: " + type);

		switch (type) {
		case DOUBLE:
			return (Problem<S>) new DoubleProblem(variables, objectives);
		case INTEGER:
			return (Problem<S>) new IntegerProblem(variables, objectives);
		case BOOLEAN:
			return (Problem<S>) new BinaryProblem(variables, objectives);
		default:
			throw new AlgorithmExecutionException("No problem type found for type " + type);
		}
	}

}
