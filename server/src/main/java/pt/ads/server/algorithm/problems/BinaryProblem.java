package pt.ads.server.algorithm.problems;

import java.util.Arrays;
import java.util.Collection;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import pt.ads.server.dto.Variable;

public class BinaryProblem extends AbstractBinaryProblem {

	private final Variable[] variables;

	public BinaryProblem(Collection<Variable> variables, Collection<String> objectives) {
		setName("Boolean Problem");
		setNumberOfVariables(variables.size());
		setNumberOfObjectives(objectives.size());

		this.variables = variables.toArray(new Variable[0]);
	}

	@Override
	protected int getBitsPerVariable(int index) {
		return variables[index].upperLimit.intValue();
	}

	@Override
	public void evaluate(BinarySolution solution) {
		// TODO
	}

	@Override
	public String toString() {
		return "BinaryProblem(" +
			   "numberOfVariables=" + getNumberOfVariables() + ',' +
			   "numberOfObjectives=" + getNumberOfObjectives() + ',' +
			   "variables=" + Arrays.toString(variables) +
			   ")";
	}

}
