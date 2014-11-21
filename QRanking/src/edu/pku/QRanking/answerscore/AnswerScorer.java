package edu.pku.QRanking.answerscore;

import edu.pku.QRanking.Question;

/**
 * Answer Extraction 
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */

public interface AnswerScorer {
	
	
    public void score(Question question);
	
}
