package pt.ads.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

@Data
public class AlgorithmResults<R extends Solution<?>, S> {

	public final Problem<R> problem;

	@JsonIgnoreProperties({ "population", "problem", "result" })
	public final Algorithm<S> algorithm;

	public final S solutions;

}
