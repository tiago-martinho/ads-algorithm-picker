package pt.ads.server.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum VariableType {
	INTEGER,
	DOUBLE,
	@JsonAlias("BINARY")
	BOOLEAN,
}
