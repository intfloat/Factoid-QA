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
			for(String word:answer.getSummary().getSummary_content())
			{
				if(word.equals(answer.getAnswer_content()))
				{
					count++;
				}
			}
			answer.setScore(answer.getScore() + count*weight);
			System.out.println("tf score:"+answer.getAnswer_content()+" "+count*weight);
		}
	}

}
