package pt.ads.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Variable {

	public String name;
	public Double lowerLimit;
	public Double upperLimit;

}
