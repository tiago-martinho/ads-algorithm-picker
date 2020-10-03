package pt.ads.server.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OwlServiceImpl implements OwlService {

	@Value("classpath:${owl.file}")
	private Resource owlResource;


	@Override
	public OWLOntology loadOntology() throws IOException, OWLOntologyCreationException {
		log.debug("Loading OWL ontology: " + owlResource.getFilename());

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		return manager.loadOntologyFromOntologyDocument(owlResource.getInputStream());
	}

	@Override
	public OWLReasoner loadReasoner(OWLOntology ontology) {
		log.debug("Loading OWL ontology reasoner");

		// Configure the HermiT reasoner
		OWLReasonerFactory reasonerFactory = new ReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createReasoner(ontology, new SimpleConfiguration(new SimpleProgressMonitor()));
		reasoner.precomputeInferences(InferenceType.values());

		return reasoner;
	}

	@Override
	public void executeQuery(String query, OWLReasoner reasoner) {
		log.debug("OWL: executing query: " + query);

		ManchesterOWLSyntaxParser parser = OWLManager.createManchesterParser();
		OWLClassExpression classExpression = parser.parseClassExpression(query);

		boolean direct = true;

		// Pretty print names: new SimpleShortFormProvider().getShortForm(entity)
		Collection<OWLClass> superClasses = reasoner.getSuperClasses(classExpression, direct).entities().collect(Collectors.toList());
		Collection<OWLClass> equivalentClasses = reasoner.getEquivalentClasses(classExpression).entities().collect(Collectors.toList());
		Collection<OWLClass> subClasses = reasoner.getSubClasses(classExpression, direct).entities().collect(Collectors.toList());
		Collection<OWLNamedIndividual> instances = reasoner.getInstances(classExpression, direct).entities().collect(Collectors.toList());

		// TODO: extract the algorithm instances from the reasoner.
	}


	private static class SimpleProgressMonitor implements ReasonerProgressMonitor {

		private String currentTaskName = "(undefined)";

		@Override
		public void reasonerTaskStarted(@NotNull String taskName) {
			this.currentTaskName = taskName;
			log.debug("OWL: " + currentTaskName + " [started]");
		}

		@Override
		public void reasonerTaskStopped() {
			log.debug("OWL: " + currentTaskName + " [finished]");
		}

	}

}
