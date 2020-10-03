package pt.ads.server.services;

import lombok.extern.slf4j.Slf4j;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class AlgorithmServiceImpl implements AlgorithmService {

    private final OwlService owlService;
    private final OWLReasoner owlReasoner;


    public AlgorithmServiceImpl(OwlService owlService) throws IOException, OWLOntologyCreationException {
        this.owlService = owlService;

        OWLOntology ontology = owlService.loadOntology();
        this.owlReasoner = owlService.loadReasoner(ontology);
    }

    @Override
	public Object getAlgorithm() {
        String classExpression = "canSolve some (isManyObjectiveProblem value true) and hasImplementationLanguage value Java";
        owlService.executeQuery(classExpression, owlReasoner);
		return null;
	}

	@Override
	public Object getAlgorithmResults(Object algorithm) {
		return null;
	}

}
