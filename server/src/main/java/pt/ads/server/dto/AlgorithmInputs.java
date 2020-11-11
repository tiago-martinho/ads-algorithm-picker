package pt.ads.server.dto;

import java.util.List;

import lombok.Data;

@Data
public class AlgorithmInputs {

	public String description;
	public VariableType type;
	public List<Variable> variables;
	public List<Objective> objectives;
	public AlgorithmOptions options;

}
