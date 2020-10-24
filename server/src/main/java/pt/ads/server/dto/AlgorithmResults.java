package pt.ads.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.uma.jmetal.algorithm.Algorithm;

@Data
public class AlgorithmResults<S> {

	@JsonIgnoreProperties({ "population", "problem", "result" })
	public final Algorithm<S> algorithm;

	public final S solutions;

}
