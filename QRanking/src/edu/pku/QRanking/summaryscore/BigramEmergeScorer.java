package edu.pku.QRanking.summaryscore;

import java.util.ArrayList;
import java.util.List;

import edu.pku.QRanking.Answer;
import edu.pku.QRanking.Question;

/**
 * Answer Extraction
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */
public class BigramEmergeScorer implements SummaryScorer {

	public float weight;
	
	@Override
	public void score(Question question) {
		List<String> terms = new ArrayList();
		for (int j = 0; j < question.getTitle().size()-1; j++) {
			if (question.getTagged_title().get(j).tag().equals("PU")
					|| question.getTagged_title().get(j).tag().equals("DT")
					|| question.getTagged_title().get(j).tag().equals("PN")
					|| question.getTagged_title().get(j).tag().equals("AD"))
				continue;
			if (question.getTagged_title().get(j + 1).tag().equals("PU")
					|| question.getTagged_title().get(j + 1).tag().equals("DT")
					|| question.getTagged_title().get(j + 1).tag().equals("PN")
					|| question.getTagged_title().get(j + 1).tag().equals("AD"))
				continue;
			if (question.getTitle().get(j).length() == 1
					|| question.getTitle().get(j + 1).length() == 1)
				continue;
			terms.add(question.getTitle().get(j) + question.getTitle().get(j + 1));
		}

		for (Answer answer : question.getAnswers()) {
			float score = 0;
			List<String> evidence_bigram = new ArrayList();
			for (int j = 0; j <answer.getSummary().getSummary_content().size()-1;j++) {
				evidence_bigram.add(answer.getSummary().getSummary_content().get(j)+answer.getSummary().getSummary_content().get(j+1));
			}
			for(String term: terms)
			{
				for(String bigram:evidence_bigram)
				{
					if(bigram.equals(term))
						score += 2.0/evidence_bigram.size();
				}
			}
			
			answer.getSummary().setScore(answer.getSummary().getScore() + weight*score);
			System.out.println("Evidence Bigram Emerge score:" + answer.getAnswer_content()
   					+ " " + score);
			
		}
	}
}
