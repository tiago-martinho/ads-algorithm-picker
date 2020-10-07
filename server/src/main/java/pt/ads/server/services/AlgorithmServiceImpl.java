package pt.ads.server.services;

import lombok.extern.slf4j.Slf4j;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.stereotype.Service;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.io.IOException;

@Service
@Slf4j
public class AlgorithmServiceImpl implements AlgorithmService {

    private final OwlService owlService;

	private final OWLOntology owlOntology;
	private final SQWRLQueryEngine owlQueryEngine;


    public AlgorithmServiceImpl(OwlService owlService) throws IOException, OWLOntologyCreationException {
        this.owlService = owlService;

        this.owlOntology = owlService.loadOntology();
        this.owlQueryEngine = owlService.loadQueryEngine(this.owlOntology);
    }

    @Override
	public Object getAlgorithm() throws SQWRLException, SWRLParseException {
        String classExpression = "canSolve some (isManyObjectiveProblem value true) ^ hasImplementationLanguage(Java)";
        owlService.executeQuery(classExpression, owlQueryEngine);
		return null;
	}

	@Override
	public Object getAlgorithmResults(Object algorithm) {
		return null;
	}

}
