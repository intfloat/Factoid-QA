package edu.pku.QRanking.evidencescore;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pku.QRanking.Answer;
import edu.pku.QRanking.Question;

/**
 * Answer Extraction
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */
public class SkipBigramEmergeScorer implements EvidenceScorer {

	public float weight;

	@Override
	public void score(Question question) {
		List<String> terms = new ArrayList();
		for (int j = 0; j < question.title.size() - 2; j++) {
			if (question.tagged_title.get(j).tag().equals("PU")
					|| question.tagged_title.get(j).tag().equals("DT")
					|| question.tagged_title.get(j).tag().equals("PN")
					|| question.tagged_title.get(j).tag().equals("AD"))
				continue;
			if (question.tagged_title.get(j + 2).tag().equals("PU")
					|| question.tagged_title.get(j + 2).tag().equals("DT")
					|| question.tagged_title.get(j + 2).tag().equals("PN")
					|| question.tagged_title.get(j + 2).tag().equals("AD"))
				continue;
			if (question.title.get(j).length() == 1
					|| question.title.get(j + 2).length() == 1)
				continue;
			terms.add(question.title.get(j) + ".{0,3}"
					+ question.title.get(j + 2));
		}
		for (Answer answer : question.answers) {
			float score = 0;
			for (String term : terms) {
				Pattern p = Pattern.compile(term);
				String evidence_content_string = "";
				for (String word : answer.evidence.evidence_content) {
					evidence_content_string += word;
				}
				Matcher matcher = p.matcher(evidence_content_string);
				while (matcher.find()) {
					String text = matcher.group();
					score += 2.0/answer.evidence.evidence_content.size();
				}
			}
			answer.evidence.score += weight*score;
			System.out.println("Evidence Skip Bigram Emerge score:" + answer.answer_content
   					+ " " + score);
		}

	}

}
