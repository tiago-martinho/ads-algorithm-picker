package pt.ads.server.dto;

import java.util.List;

import lombok.Data;

@Data
public class AlgorithmInputs {

	public int numberOfObjectives;
	public Boolean heavyProcessing;
	public VariableType type;
	public List<Variable> variables;
	public AlgorithmOptions options;

}
