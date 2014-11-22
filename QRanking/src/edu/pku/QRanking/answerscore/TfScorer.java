package edu.pku.QRanking.answerscore;

import edu.pku.QRanking.Answer;
import edu.pku.QRanking.Question;

import java.util.HashMap;
import java.util.Map;


/**
 * Answer Extraction 
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */

public class TfScorer implements AnswerScorer{

	float weight;
	//add term_frequency*weight to score
	@Override
	public void score(Question question) {
		for(Answer answer:question.answers)
		{
			//answer word counts
			int count = 0;
			for(String word:answer.evidence.evidence_content)
			{
				if(word.equals(answer.answer_content))
				{
					count++;
				}
			}
			answer.score += count*weight;
			System.out.println("tf score:"+answer.answer_content+" "+count*weight);
		}
	}

}
