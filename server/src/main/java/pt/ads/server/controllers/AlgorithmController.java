package pt.ads.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uma.jmetal.solution.DoubleSolution;
import pt.ads.server.dto.AlgorithmResults;
import pt.ads.server.dto.Experiment;
import pt.ads.server.services.AlgorithmService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AlgorithmController {

	private final AlgorithmService algorithmService;


	/**
	 * TODO Possibly define a custom model as a parameter (in that case, we should not use a GetMapping)
	 * @return the name of the algorithm and its run results for the given user parameters
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<AlgorithmResults<DoubleSolution, List<DoubleSolution>>> getResults() throws Exception {
		Experiment<DoubleSolution, List<DoubleSolution>> experiment = algorithmService.getAlgorithm();
		AlgorithmResults<DoubleSolution, List<DoubleSolution>> results = algorithmService.getAlgorithmResults(experiment);

		return ResponseEntity.ok(results);
	}

}
