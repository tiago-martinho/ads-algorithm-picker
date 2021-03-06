package pt.ads.server.algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Loads the defaults to be used in the algorithms, in case the user didn't specify them in the UI.
 */
@Component
public class AlgorithmDefaults {

	/* Generics */
	@Value("${algorithm.population-size}")
	public int populationSize;
	@Value("${algorithm.iterations}")
	public int iterations;

	/* Crossover selector */
	@Value("${algorithm.selectors.crossover.probability}")
	public double crossoverProbability;
	@Value("${algorithm.selectors.crossover.distribution-index}")
	public double crossoverDistributionIndex;

	/* Mutation selector */
	@Value("${algorithm.selectors.mutation.probability}")
	public double mutationProbability;
	@Value("${algorithm.selectors.mutation.distribution-index}")
	public double mutationDistributionIndex;

}
