package pt.ads.server.services;

import java.util.List;

import org.uma.jmetal.solution.Solution;
import pt.ads.server.model.AlgorithmInputs;
import pt.ads.server.model.AlgorithmListResults;
import pt.ads.server.model.Experiment;
import pt.ads.server.exceptions.AlgorithmException;

public interface AlgorithmService {

    /**
     * Find and instantiate the best algorithm based on the user input.
	 *
	 * @param inputs the user inputs
     * @return the best algorithm for the problem
     */
    <T extends Solution<?>> Experiment<T, List<T>> getAlgorithms(AlgorithmInputs inputs) throws AlgorithmException;

    /**
     * Executes the algorithm.
	 *
	 * @param inputs the user inputs
	 * @param experiment the algorithm to execute
	 * @return the results from the algorithm
     */
    <T extends Solution<?>> AlgorithmListResults<T, List<T>> executeAlgorithms(AlgorithmInputs inputs, Experiment<T, List<T>> experiment);

}
