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
public class BigramEmergeScorer implements EvidenceScorer {

	public float weight;
	
	@Override
	public void score(Question question) {
		List<String> terms = new ArrayList();
		for (int j = 0; j < question.title.size()-1; j++) {
			if (question.tagged_title.get(j).tag().equals("PU")
					|| question.tagged_title.get(j).tag().equals("DT")
					|| question.tagged_title.get(j).tag().equals("PN")
					|| question.tagged_title.get(j).tag().equals("AD"))
				continue;
			if (question.tagged_title.get(j + 1).tag().equals("PU")
					|| question.tagged_title.get(j + 1).tag().equals("DT")
					|| question.tagged_title.get(j + 1).tag().equals("PN")
					|| question.tagged_title.get(j + 1).tag().equals("AD"))
				continue;
			if (question.title.get(j).length() == 1
					|| question.title.get(j + 1).length() == 1)
				continue;
			terms.add(question.title.get(j) + question.title.get(j + 1));
		}

		for (Answer answer : question.answers) {
			float score = 0;
			List<String> evidence_bigram = new ArrayList();
			for (int j = 0; j <answer.summary.summary_content.size()-1;j++) {
				evidence_bigram.add(answer.summary.summary_content.get(j)+answer.summary.summary_content.get(j+1));
			}
			for(String term: terms)
			{
				for(String bigram:evidence_bigram)
				{
					if(bigram.equals(term))
						score += 2.0/evidence_bigram.size();
				}
			}
			
			answer.summary.score += weight*score;
			System.out.println("Evidence Bigram Emerge score:" + answer.answer_content
   					+ " " + score);
			
		}
	}
}
