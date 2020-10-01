package pt.ads.server.controllers;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pt.ads.server.services.AlgorithmService;
import pt.ads.server.services.AlgorithmServiceImpl;

import java.net.http.HttpResponse;

@Controller
@RequestMapping("/algorithms")
public class AlgorithmController {

	private final AlgorithmService algorithmService;

	public AlgorithmController(AlgorithmService algorithmService) {
		this.algorithmService = algorithmService;
	}

	@GetMapping("/execute/{algorithm}")
	@ResponseBody
	public ResponseEntity<String> execute() {
		return ResponseEntity.ok().build();
	}

}
