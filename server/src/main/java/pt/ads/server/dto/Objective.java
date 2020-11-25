package pt.ads.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Objective {

	public String name;
	public ObjectiveGoal goal;

}
