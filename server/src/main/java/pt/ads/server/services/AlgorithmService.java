package pt.ads.server.services;

import org.uma.jmetal.solution.DoubleSolution;
import pt.ads.server.dto.AlgorithmResults;
import pt.ads.server.dto.Experiment;

import java.util.List;

public interface AlgorithmService {

    /**
     * TODO Define the number of parameters to be passed (or maybe define a model that includes all necessary variables)
     * TODO Change the type to be returned (inspect OWL API first)
     * @return the best algorithm for the problem
     */
    Experiment<DoubleSolution, List<DoubleSolution>> getAlgorithm() throws Exception;

    /**
     * TODO Change the type to be returned
     * TODO Change the type of the parameter (inspect JMetal first)
     * @param experiment the algorithm to execute
     * @return the results from the algorithm
     */
    AlgorithmResults<DoubleSolution, List<DoubleSolution>> getAlgorithmResults(Experiment<DoubleSolution, List<DoubleSolution>> experiment);

}
