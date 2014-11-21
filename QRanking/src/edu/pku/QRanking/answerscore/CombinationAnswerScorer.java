package edu.pku.QRanking.answerscore;

import java.util.ArrayList;
import java.util.List;

import edu.pku.QRanking.Question;

/**
 * Answer Extraction 
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */
public class CombinationAnswerScorer implements AnswerScorer{
	
	private final List<AnswerScorer> scorers = new ArrayList<>();
	float weight;
	
    public void addCandidateAnswerScore(AnswerScorer scorer) {
    	scorers.add(scorer);
    }
    
    @Override
    public void score(Question question)
    {
    	for (AnswerScorer scorer:scorers)
    	{
    		scorer.score(question);
    	}
    }

	
}
