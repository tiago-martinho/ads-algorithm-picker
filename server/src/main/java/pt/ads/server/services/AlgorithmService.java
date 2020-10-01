package pt.ads.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmService {

	private final Logger log = LoggerFactory.getLogger(AlgorithmService.class);


	public String findBestAlgorithm() {
		log.debug("Finding best algorithm for...");
		return "BEST ALGORITHM";
	}

	public String executeAlgorithm(String algorithmName) {
		log.debug("Executing algorithm: " + algorithmName);
		return "EXECUTED " + algorithmName;
	}

}
