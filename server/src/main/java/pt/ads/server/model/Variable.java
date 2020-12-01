package pt.ads.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Variable {

	public String name;
	public Double lowerLimit;
	public Double upperLimit;

}
