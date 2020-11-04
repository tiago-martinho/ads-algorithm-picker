package pt.ads.server.algorithm.problems;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import pt.ads.server.dto.Variable;

public class IntegerProblem extends AbstractIntegerProblem {

	protected List<Integer> lowerLimit;
	protected List<Integer> upperLimit;

	public IntegerProblem(Collection<Variable> variables, Collection<String> objectives) {
		setName("Integer Problem");
		setNumberOfVariables(variables.size());
		setNumberOfObjectives(objectives.size());
		setLowerLimit(variables.stream().map(Variable::getLowerLimit).map(Double::intValue).collect(Collectors.toList()));
		setUpperLimit(variables.stream().map(Variable::getUpperLimit).map(Double::intValue).collect(Collectors.toList()));
	}

	@Override
	public void evaluate(IntegerSolution solution) {
		// TODO
	}

	@Override
	protected void setLowerLimit(List<Integer> lowerLimit) {
		this.lowerLimit = lowerLimit;
		super.setLowerLimit(lowerLimit);
	}

	@Override
	protected void setUpperLimit(List<Integer> upperLimit) {
		this.upperLimit = upperLimit;
		super.setUpperLimit(upperLimit);
	}

	@Override
	public String toString() {
		return "IntegerProblem(" +
			   "numberOfVariables=" + getNumberOfVariables() + ',' +
			   "numberOfObjectives=" + getNumberOfObjectives() + ',' +
			   "lowerLimit=" + lowerLimit + ',' +
			   "upperLimit=" + upperLimit +
			   ")";
	}

}
