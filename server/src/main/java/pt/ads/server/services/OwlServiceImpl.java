package pt.ads.server.services;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.io.IOException;

@Service
@Slf4j
public class OwlServiceImpl implements OwlService {

	@Value("classpath:${owl.file}")
	private Resource owlResource;


	@Override
	public @NonNull OWLOntology loadOntology() throws IOException, OWLOntologyCreationException {
		log.debug("Loading OWL ontology: " + owlResource.getFilename());

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		return manager.loadOntologyFromOntologyDocument(owlResource.getInputStream());
	}

	@Override
	public @NonNull SQWRLQueryEngine loadQueryEngine(@NotNull OWLOntology ontology) {
		log.debug("Loading SQWRL query engine");

		// Create SQWRL query engine using the SWRLAPI
		return SWRLAPIFactory.createSQWRLQueryEngine(ontology);
	}

	@Override
	public void executeQuery(String query, @NotNull SQWRLQueryEngine queryEngine) throws SQWRLException, SWRLParseException {
		log.debug("OWL: executing query: " + query);

		SQWRLResult result = queryEngine.runSQWRLQuery("findAlgorithm", query);

		// Process the SQWRL result
		while (result.next()) {
			System.out.println("RESULT: " + result); // result.getLiteral("x").getInteger()
		}
	}

}
