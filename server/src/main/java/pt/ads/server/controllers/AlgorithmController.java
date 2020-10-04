package pt.ads.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pt.ads.server.services.AlgorithmService;

@Controller
@RequiredArgsConstructor
public class AlgorithmController {

	private final AlgorithmService algorithmService;

	/**
	 * TODO Define a model for the the response and possibly a custom model as a parameter (in that case, we should not use a GetMapping)
	 * @return the name of the algorithm and its run results for the given user parameters
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getResults() throws Exception {
		algorithmService.getAlgorithm();
		return ResponseEntity.ok().build();
	}

}
