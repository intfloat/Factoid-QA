package edu.pku.QRanking.evidencescore;

import java.util.ArrayList;
import java.util.List;

import edu.pku.QRanking.Question;

public class CombinationEvidenceScorer implements EvidenceScorer{

	private final List<EvidenceScorer> scorers = new ArrayList<>();
	float weight;
	
	@Override
	public void score(Question question) {
		// TODO Auto-generated method stub
    	for (EvidenceScorer scorer:scorers)
    	{
    		scorer.score(question);
    	}
	}


    
    
}
