package pt.ads.server.algorithm.problem;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import pt.ads.server.model.Objective;
import pt.ads.server.model.ObjectiveGoal;
import pt.ads.server.model.Variable;

/**
 * Represents a binary problem - variables that can only have the values 0 or 1.
 */
public class BinaryProblem extends AbstractBinaryProblem implements Problem<BinarySolution> {

	protected final Variable[] variables;
	protected List<ObjectiveGoal> objectiveGoals;

	public BinaryProblem(Collection<Variable> variables, Collection<Objective> objectives) {
		setName("Boolean Problem");
		setNumberOfVariables(variables.size());
		setNumberOfObjectives(objectives.size());
		this.variables = variables.toArray(new Variable[0]);
		this.objectiveGoals = objectives.stream().map(Objective::getGoal).collect(Collectors.toList());
	}

	@Override
	protected int getBitsPerVariable(int index) {
		return variables[index].upperLimit.intValue();
	}

	@Override
	public void evaluate(BinarySolution solution) {
		// Evaluator taken from OneZeroMax
		// TODO: ask the client tho choose the evaluate function

		int counterOnes;
		int counterZeroes;

		counterOnes = 0;
		counterZeroes = 0;

		BitSet bitset = solution.getVariableValue(0) ;

		for (int i = 0; i < bitset.length(); i++) {
			if (bitset.get(i)) {
				counterOnes++;
			} else {
				counterZeroes++;
			}
		}

		// OneZeroMax is a maximization problem: multiply by -1 to minimize
		solution.setObjective(0, -1.0 * counterOnes);
		solution.setObjective(1, -1.0 * counterZeroes);
	}

	public ObjectiveGoal getObjectiveGoal(int i) {
		return objectiveGoals.get(i);
	}

	@Override
	public String toString() {
		return "BinaryProblem(" +
			   "numberOfVariables=" + getNumberOfVariables() + ',' +
			   "numberOfObjectives=" + getNumberOfObjectives() + ',' +
			   "variables=" + Arrays.toString(variables) + ',' +
			   "objectiveGoals=" + objectiveGoals +
			   ")";
	}

}
