package pt.ads.server.model;

import java.util.Collection;
import java.util.List;

import lombok.Data;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

@Data
public class Experiment<R extends Solution<?>, S> {

	public final Problem<R> problem;
	public final List<Algorithm<S>> algorithms;
	public final Collection<String> possibleAlgorithms;

}
