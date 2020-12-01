package pt.ads.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Objective {

	public String name;
	public ObjectiveGoal goal;

}
