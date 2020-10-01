package pt.ads.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pt.ads.server.services.AlgorithmService;

@Controller
@RequestMapping("/algorithms")
public class AlgorithmController {

	private final AlgorithmService algorithmService;

	public AlgorithmController(AlgorithmService algorithmService) {
		this.algorithmService = algorithmService;
	}


	@GetMapping("/find")
	@ResponseBody
	public String find() {
		return algorithmService.findBestAlgorithm();
	}

	@GetMapping("/execute/{algorithm}")
	@ResponseBody
	public String execute(@PathVariable(value="algorithm") String algorithmName) {
		return algorithmService.executeAlgorithm(algorithmName);
	}

}
