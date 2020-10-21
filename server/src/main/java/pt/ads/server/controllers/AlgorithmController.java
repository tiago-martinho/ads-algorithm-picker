package pt.ads.server.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uma.jmetal.solution.DoubleSolution;
import pt.ads.server.dto.AlgorithmInputs;
import pt.ads.server.dto.AlgorithmResults;
import pt.ads.server.dto.Experiment;
import pt.ads.server.services.AlgorithmService;

import java.util.List;

@Slf4j
@Controller
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
	public ResponseEntity<AlgorithmResults<DoubleSolution, List<DoubleSolution>>> getResults(@RequestBody AlgorithmInputs inputs) throws Exception {
		log.debug("INPUTS: " + inputs);

		Experiment<DoubleSolution, List<DoubleSolution>> experiment = algorithmService.getAlgorithm(inputs);
		AlgorithmResults<DoubleSolution, List<DoubleSolution>> results = algorithmService.getAlgorithmResults(inputs, experiment);

		return ResponseEntity.ok(results);
	}

}
