package pt.ads.server.services;

public interface AlgorithmService {

    /**
     * TODO Define the number of parameters to be passed (or maybe define a model that includes all necessary variables)
     * TODO Change the type to be returned (inspect OWL API first)
     * @return
     */
    Object getAlgorithm() throws Exception;

    /**
     * TODO Change the type to be returned
     * TODO Change the type of the parameter (inspect JMetal first)
     * @return
     */
    Object getAlgorithmResults(Object algorithm);

}
