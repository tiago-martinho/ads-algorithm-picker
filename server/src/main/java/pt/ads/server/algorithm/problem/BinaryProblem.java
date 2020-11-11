package pt.ads.server.algorithm.problem;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import pt.ads.server.dto.Objective;
import pt.ads.server.dto.ObjectiveGoal;
import pt.ads.server.dto.Variable;

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
		// TODO: ask the client tho choose the evaluate function
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
