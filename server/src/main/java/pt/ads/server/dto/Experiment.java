package pt.ads.server.dto;

import lombok.Data;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

@Data
public class Experiment<R extends Solution<?>, S> {

	public final Problem<R> problem;
	public final Algorithm<S> algorithm;

}
