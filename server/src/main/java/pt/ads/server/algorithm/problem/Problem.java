package pt.ads.server.algorithm.problem;

import org.uma.jmetal.solution.Solution;
import pt.ads.server.model.ObjectiveGoal;

/**
 * Represents an abstract problem.
 *
 * @param <S> the solution type
 */
public interface Problem<S extends Solution<?>> extends org.uma.jmetal.problem.Problem<S> {

	/**
	 * Specifies whether the objective should be minimized (find the lowest value possible) or maximized (the highest value possible).
	 *
	 * @param i the objective index
	 * @return the objective goal
	 */
	ObjectiveGoal getObjectiveGoal(int i);

}
