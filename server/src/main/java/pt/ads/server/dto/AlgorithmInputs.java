package pt.ads.server.dto;

import java.util.List;

import lombok.Data;

@Data
public class AlgorithmInputs {

	public int numberOfObjectives;
	public VariableType type;
	public List<Variable> variables;
	public boolean heavyProcessing;
	public AlgorithmOptions options;

}
