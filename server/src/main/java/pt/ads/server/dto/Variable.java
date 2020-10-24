package pt.ads.server.dto;

import lombok.Data;

@Data
public class Variable {

	public String name;
	public Double lowerLimit;
	public Double upperLimit;

}
