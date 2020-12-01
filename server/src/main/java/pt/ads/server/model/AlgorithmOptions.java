package pt.ads.server.model;

import lombok.Data;

@Data
public class AlgorithmOptions {

	public Boolean heavyProcessing;

	public Integer populationSize;
	public Integer iterations;

	public Double crossoverProbability;
	public Double crossoverDistributionIndex;

	public Double mutationProbability;
	public Double mutationDistributionIndex;

}
