package pt.ads.server.services;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlgorithmServiceImpl implements AlgorithmService {

    @Value("classpath:PMOEA.owl")
    private Resource owlFile;


    @Override
    public Object getAlgorithm() throws OWLOntologyCreationException, IOException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(owlFile.getInputStream());
        OWLClass clazz = manager.getOWLDataFactory().getOWLThing();

        log.debug("Ontology Loaded...");
        log.debug("Document IRI: " + owlFile);
        log.debug("Ontology    : " + ontology.getOntologyID());
        log.debug("Format      : " + manager.getOntologyFormat(ontology));
        log.debug("Thing Class : " + clazz);

        return null;
    }

    @Override
    public Object getAlgorithmResults(Object algorithm) {
        return null;
    }
}
