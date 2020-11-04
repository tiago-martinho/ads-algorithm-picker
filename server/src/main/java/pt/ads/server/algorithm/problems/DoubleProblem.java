package pt.ads.server.algorithm.problems;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import pt.ads.server.dto.Variable;

public class DoubleProblem extends AbstractDoubleProblem {

	protected List<Double> lowerLimit;
	protected List<Double> upperLimit;

	public DoubleProblem(Collection<Variable> variables, Collection<String> objectives) {
		setName("Double Problem");
		setNumberOfVariables(variables.size());
		setNumberOfObjectives(objectives.size());
		setLowerLimit(variables.stream().map(Variable::getLowerLimit).collect(Collectors.toList()));
		setUpperLimit(variables.stream().map(Variable::getUpperLimit).collect(Collectors.toList()));
	}

	@Override
	public void evaluate(DoubleSolution solution) {
		// Evaluator taken from Kursawe

		double aux, xi, xj;
		double[] fx = new double[getNumberOfObjectives()];
		double[] x = new double[getNumberOfVariables()];
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			x[i] = solution.getVariableValue(i) ;
		}

		fx[0] = 0.0;
		for (int var = 0; var < solution.getNumberOfVariables() - 1; var++) {
			xi = x[var] * x[var];
			xj = x[var + 1] * x[var + 1];
			aux = (-0.2) * Math.sqrt(xi + xj);
			fx[0] += (-10.0) * Math.exp(aux);
		}

		if (fx.length > 1) {
			fx[1] = 0.0;

			for (int var = 0; var < solution.getNumberOfVariables(); var++) {
				fx[1] += Math.pow(Math.abs(x[var]), 0.8) + 5.0 * Math.sin(Math.pow(x[var], 3.0));
			}
		}

		for (int i = 0; i < fx.length; i++) {
			solution.setObjective(i, fx[i]);
		}
	}

	@Override
	protected void setLowerLimit(List<Double> lowerLimit) {
		this.lowerLimit = lowerLimit;
		super.setLowerLimit(lowerLimit);
	}

	@Override
	protected void setUpperLimit(List<Double> upperLimit) {
		this.upperLimit = upperLimit;
		super.setUpperLimit(upperLimit);
	}

	@Override
	public String toString() {
		return "DoubleProblem(" +
			   "numberOfVariables=" + getNumberOfVariables() + ',' +
			   "numberOfObjectives=" + getNumberOfObjectives() + ',' +
			   "lowerLimit=" + lowerLimit + ',' +
			   "upperLimit=" + upperLimit +
			   ")";
	}

}
