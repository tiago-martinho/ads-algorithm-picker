package pt.ads.server.services;

import java.io.IOException;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

public interface OwlService {

    /**
     * Loads the ontology found on the <i>owl.file</i> config value.
     *
     * @return the ontology
     * @throws IOException the file was not loaded
     * @throws OWLOntologyCreationException the OWL file is in an invalid format.
     */
    @NonNull OWLOntology loadOntology() throws IOException, OWLOntologyCreationException;

    /**
     * Loads a reasoner from the OWL ontology.
     *
     * @param ontology the ontology
     * @return the reasoner
     */
    @NonNull SQWRLQueryEngine loadQueryEngine(@NonNull OWLOntology ontology);

    /**
     * Executes a DL query againsta given ontology reasoner.
     *  @param query the query
     * @param queryEngine the query engine
	 * @return
	 */
	@NonNull SQWRLResult executeQuery(String query, @NonNull SQWRLQueryEngine queryEngine) throws SQWRLException, SWRLParseException;

}
