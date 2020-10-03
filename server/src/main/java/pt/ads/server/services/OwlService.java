package pt.ads.server.services;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.io.IOException;

public interface OwlService {

    /**
     * Loads the ontology found on the <i>owl.file</i> config value.
     *
     * @return the ontology
     * @throws IOException the file was not loaded
     * @throws OWLOntologyCreationException the OWL file is in an invalid format.
     */
    OWLOntology loadOntology() throws IOException, OWLOntologyCreationException;

    /**
     * Loads a reasoner from the OWL ontology.
     *
     * @param ontology the ontology
     * @return the reasoner
     */
    OWLReasoner loadReasoner(OWLOntology ontology);

    /**
     * Executes a DL query againsta given ontology reasoner.
     *
     * @param query the query
     * @param reasoner the ontology reasoner
     */
    void executeQuery(String query, OWLReasoner reasoner);

}
