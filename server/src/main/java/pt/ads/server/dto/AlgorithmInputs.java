package pt.ads.server.dto;

import java.util.List;

import lombok.Data;

@Data
public class AlgorithmInputs {

	public VariableType type;
	public List<Variable> variables;
	public List<String> objectives;
	public AlgorithmOptions options;

}
