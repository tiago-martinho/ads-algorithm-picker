package pt.ads.server.controllers;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.uma.jmetal.solution.Solution;
import pt.ads.server.dto.AlgorithmInputs;
import pt.ads.server.dto.AlgorithmListResults;
import pt.ads.server.dto.Experiment;
import pt.ads.server.services.AlgorithmService;

@Slf4j
@Controller
@CrossOrigin
@RequiredArgsConstructor
public class AlgorithmController {

	private final AlgorithmService algorithmService;


	/**
	 * Find and execute an algorithm based on the user inputs.
	 *
	 * @param inputs the user inputs
	 * @return the name of the algorithm and its run results for the given user parameters
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public <T extends Solution<?>> ResponseEntity<AlgorithmListResults<T, List<T>>> getResults(@RequestBody AlgorithmInputs inputs) {
		log.debug("INPUTS: " + inputs);

		Experiment<T, List<T>> experiment = algorithmService.getAlgorithms(inputs);
		AlgorithmListResults<T, List<T>> results = algorithmService.executeAlgorithms(inputs, experiment);

		return ResponseEntity.ok(results);
	}

}
