package pt.ads.server.controllers;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.uma.jmetal.solution.Solution;
import pt.ads.server.dto.*;
import pt.ads.server.exceptions.AlgorithmInputsException;
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

		validateInputs(inputs);

		Experiment<T, List<T>> experiment = algorithmService.getAlgorithms(inputs);
		AlgorithmListResults<T, List<T>> results = algorithmService.executeAlgorithms(inputs, experiment);

		return ResponseEntity.ok(results);
	}

	private void validateInputs(AlgorithmInputs inputs) {
		if (inputs.type == VariableType.INTEGER || inputs.type == VariableType.DOUBLE) {
			for (Variable variable : inputs.variables) {
				Optional<String> name = Optional.ofNullable(variable.name).map(n -> "the variable '" + n + '\'');

				if (variable.lowerLimit == null) {
					throw new AlgorithmInputsException("You need to specify the lower limit for " + name.orElse("all the variables") + "!");
				} else if (variable.upperLimit == null) {
					throw new AlgorithmInputsException("You need to specify the upper limit for " + name.orElse("all the variables") + "!");
				} else if (variable.lowerLimit > variable.upperLimit) {
					throw new AlgorithmInputsException("Your lower limit is higher than the high limit on " + name.orElse("one of the variables") + "!");
				}
			}
		}
	}

}
