package pt.ads.server.owl;

import pt.ads.server.exceptions.AlgorithmInputsException;

/**
 * Uses the builder design pattern to simplify the creation of the query to be executed by the OWL ontology.
 */
public class OWLQueryBuilder {

	private Integer minObjectives = null;
	private Integer maxObjectives = null;
	private Boolean heavyProcessing = null;

	public OWLQueryBuilder() {
	}

	public String build() throws AlgorithmInputsException {
		StringBuilder sb = new StringBuilder();

		sb.append("Algorithm(?alg) ^");

		if (minObjectives != null)
			sb.append(" minObjectivesAlgorithmIsAbleToDealWith(?alg,?min) ^ swrlb:lessThanOrEqual(?min,").append(minObjectives).append(')');

		if (maxObjectives != null)
			sb.append(" maxObjectivesAlgorithmIsAbleToDealWith(?alg,?max) ^ swrlb:greaterThanOrEqual(?max,").append(maxObjectives).append(')');

		if (heavyProcessing != null)
			sb.append(" dealsWithHeavyProcessingEvaluationFunctions(?alg,").append(heavyProcessing).append(')');

		sb.append(" -> sqwrl:select(?alg)");

		return sb.toString();
	}

	public OWLQueryBuilder minObjectives(Integer minObjectives) {
		this.minObjectives = minObjectives;
		return this;
	}

	public OWLQueryBuilder maxObjectives(Integer maxObjectives) {
		this.maxObjectives = maxObjectives;
		return this;
	}

	public OWLQueryBuilder heavyProcessing(Boolean heavyProcessing) {
		this.heavyProcessing = heavyProcessing;
		return this;
	}

}
