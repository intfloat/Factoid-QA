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
public class TermEmergeScorer implements EvidenceScorer {

	
	public float weight;
	
	@Override
	public void score(Question question) {

		List<String> Terms = new ArrayList();
			for (int j = 0; j < question.title.size(); j++) {

				if (question.tagged_title.get(j).tag().equals("PU")||question.tagged_title.get(j).tag().equals("DT")||question.tagged_title.get(j).tag().equals("PN")||question.tagged_title.get(j).tag().equals("AD"))
					continue;
				if (question.title.get(j).length() == 1)
					continue;
				Terms.add(question.title.get(j));
			}
		for (Answer answer : question.answers) {
			float score = 0;
			for (String term : Terms) {
				for (String evidence_term : answer.evidence.evidence_content)
					if (term.equals(evidence_term))
						score += 1.0/answer.evidence.evidence_content.size();
			}
			answer.evidence.score += weight*score;
			System.out.println("Evidence Term Emerge score:" + answer.answer_content
   					+ " " + score);
		}

	}

}
