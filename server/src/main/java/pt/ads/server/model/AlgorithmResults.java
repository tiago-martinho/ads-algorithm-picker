package pt.ads.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.uma.jmetal.algorithm.Algorithm;

@Data
public class AlgorithmResults<S> {

	@JsonIgnoreProperties({ "population", "problem", "result" })
	public final Algorithm<S> algorithm;

	public final S solutions;

}
