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
public class TermEmergeScorer implements SummaryScorer {

	
	public float weight;
	
	@Override
	public void score(Question question) {

		List<String> Terms = new ArrayList();
			for (int j = 0; j < question.getTitle().size(); j++) {

				if (question.getTagged_title().get(j).tag().equals("PU")||question.getTagged_title().get(j).tag().equals("DT")||question.getTagged_title().get(j).tag().equals("PN")||question.getTagged_title().get(j).tag().equals("AD"))
					continue;
				if (question.getTitle().get(j).length() == 1)
					continue;
				Terms.add(question.getTitle().get(j));
			}
		for (Answer answer : question.getAnswers()) {
			float score = 0;
			for (String term : Terms) {
				for (String evidence_term : answer.getSummary().getSummary_content())
					if (term.equals(evidence_term))
						score += 1.0/answer.getSummary().getSummary_content().size();
			}
			answer.getSummary().setScore(answer.getSummary().getScore() + weight*score);
	//		System.out.println("Evidence Term Emerge score:" + answer.getAnswer_content()
   	//				+ " " + score);
		}

	}

}
