package pt.ads.server.services;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * Service for the business logic related to the OWL ontologies - load and execute queries against a given ontology.
 */
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
	public @NonNull SQWRLQueryEngine loadQueryEngine(@NonNull OWLOntology ontology) {
		log.debug("Loading SQWRL query engine");

		// Create SQWRL query engine using the SWRLAPI
		return SWRLAPIFactory.createSQWRLQueryEngine(ontology);
	}

	@Override
	public @NonNull SQWRLResult executeQuery(String query, @NonNull SQWRLQueryEngine queryEngine) throws SQWRLException, SWRLParseException {
		log.debug("OWL: executing query: " + query);
		return queryEngine.runSQWRLQuery("findAlgorithm", query);
	}

}
