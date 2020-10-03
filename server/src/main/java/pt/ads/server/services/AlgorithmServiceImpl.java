package pt.ads.server.services;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {

    private static final String ONTOLOGY_PATH = "src/main/resources/PMOEA.owl";

    @Override
    public Object getAlgorithm() throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File(ONTOLOGY_PATH));
        System.out.println(ontology);
        return null;
    }

    @Override
    public Object getAlgorithmResults(Object algorithm) {
        return null;
    }
}
