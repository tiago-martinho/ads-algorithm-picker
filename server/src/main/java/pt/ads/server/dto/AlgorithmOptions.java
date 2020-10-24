package pt.ads.server.dto;

import lombok.Data;

@Data
public class AlgorithmOptions {

	public Integer populationSize;
	public Integer iterations;

	public Double crossoverProbability;
	public Double crossoverDistributionIndex;

	public Double mutationProbability;
	public Double mutationDistributionIndex;

}
