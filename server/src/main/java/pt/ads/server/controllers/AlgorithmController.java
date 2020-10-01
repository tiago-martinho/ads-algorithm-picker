package pt.ads.server.controllers;

import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pt.ads.server.services.AlgorithmService;
import pt.ads.server.services.AlgorithmServiceImpl;

import java.net.http.HttpResponse;

@Controller
public class AlgorithmController {

	private final AlgorithmService algorithmService;

	public AlgorithmController(AlgorithmService algorithmService) {
		this.algorithmService = algorithmService;
	}

	/**
	 * TODO Define a model for the the response and possibly a custom model as a parameter (in that case, we should not use a GetMapping)
	 * @return the name of the algorithm and its run results for the given user parameters
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> getResults() {
		return ResponseEntity.ok().build();
	}

}
