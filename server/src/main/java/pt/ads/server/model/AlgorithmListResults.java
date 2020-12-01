package pt.ads.server.model;

import lombok.Data;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

@Data
public class AlgorithmListResults<R extends Solution<?>, S> {

	public final AlgorithmInputs inputs;

	public final Problem<R> problem;

	public final AlgorithmResults<S> results;

}
