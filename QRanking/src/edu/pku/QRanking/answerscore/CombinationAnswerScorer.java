package edu.pku.QRanking.answerscore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import edu.pku.QRanking.Answer;
import edu.pku.QRanking.Question;
import edu.pku.QRanking.QuestionCategory;
import edu.pku.QRanking.SynthesizedAnswer;

/**
 * Answer Extraction
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */
public class CombinationAnswerScorer implements AnswerScorer {

	private final List<AnswerScorer> scorers = new ArrayList<AnswerScorer>();
	float weight;

	public void addCandidateAnswerScore(AnswerScorer scorer) {
		scorers.add(scorer);
	}

	public CombinationAnswerScorer() {
		TfScorer tfs = new TfScorer();
		TermDistanceScorer tds = new TermDistanceScorer();
		TermMiniDistanceScorer tmds = new TermMiniDistanceScorer();
		TermAlignmentScorer tas = new TermAlignmentScorer();
		LooseTermAlignmentScorer ltas = new LooseTermAlignmentScorer();
		tfs.weight = (float) 0.6;
		tds.weight = 7;
		tmds.weight = 7;
		tas.weight = 12;
		ltas.weight = 10;
		addCandidateAnswerScore(tfs);
		addCandidateAnswerScore(tds);
		addCandidateAnswerScore(tmds);
		addCandidateAnswerScore(tas);
		addCandidateAnswerScore(ltas);
	}

	@Override
	public void score(Question question) {
		List<Answer> cached = new ArrayList<Answer>();
		List<SynthesizedAnswer> for_merge = new ArrayList<SynthesizedAnswer>();
		List<SynthesizedAnswer> synthesizedanswer = new ArrayList<SynthesizedAnswer>();
		Map<String, Float> score_map = new HashMap<String, Float>();
		for (Answer answer : question.answers) {
			if (answer.getAnswer_content().contains("百度")
					|| answer.getAnswer_content().contains("知道")
					|| answer.getAnswer_content().contains("文库")
					|| answer.getAnswer_content().contains("答案")
					|| answer.getAnswer_content().contains("原理")
					|| answer.getAnswer_content().contains("题库")) {
				cached.add(answer);
				continue;
			}

			for (String word : question.title) {
				if (word.equals("俄罗斯人") || answer.getAnswer_content().equals("俄罗斯"))
					System.out.println("word:" + word + "    answer:"
							+ answer.getAnswer_content());

				if (word.contains(answer.getAnswer_content())) {
					cached.add(answer);
					break;
				}
			}
		}

		// delete those answers in question titles
		for (Answer answer : cached) {
			question.answers.remove(answer);
		}

		if (question.unique_answer == false)
			for (AnswerScorer scorer : scorers) {
				scorer.score(question);
			}

		// synthesize answers final_score = sum(answer_score in all
		// evidences)+sum(evidence_score which contains this answer)
		for (Answer answer : question.answers) {
			if (score_map.get(answer.getAnswer_content()) == null) {
				if (question.unique_answer == false)
					score_map.put(answer.getAnswer_content(), answer.getScore()
							+ answer.getSummary().getScore());
				else
					score_map.put(answer.getAnswer_content(), answer.getScore());
			} else {
				float score = score_map.get(answer.getAnswer_content());
				score += answer.getScore() + answer.getSummary().getScore();
				score_map.put(answer.getAnswer_content(), score);
			}
		}

		// move synthesized answers from map to list
		for (Map.Entry<String, Float> entry : score_map.entrySet()) {
			String answer = entry.getKey();
			float score = entry.getValue();
			SynthesizedAnswer new_synthesizedanswer = new SynthesizedAnswer();
			new_synthesizedanswer.answer = answer;
			new_synthesizedanswer.score = score;
			synthesizedanswer.add(new_synthesizedanswer);
		}

		// just for test ,for person name problems 厄尔诺·鲁比克 include 鲁毕克 so maybe
		// we should merge them
		if (question.category == QuestionCategory.PERSON_NAME) {

			for (SynthesizedAnswer sa : synthesizedanswer) {
				for (SynthesizedAnswer sa2 : synthesizedanswer) {
					if (sa.answer.contains(sa2.answer)
							&& !sa.answer.equals(sa2.answer)) {
						System.out.println("cached remove:" + sa.answer + " "
								+ sa2.answer);
						sa.score += sa2.score;
						for_merge.add(sa2);
					}
				}
			}

			for (SynthesizedAnswer sa : for_merge) {
				System.out.println("remove:" + sa.answer);
				synthesizedanswer.remove(sa);
			}

		}

		// sort answers
		Collections.sort(synthesizedanswer);
		Collections.reverse(synthesizedanswer);
		question.synthesized_answers = synthesizedanswer;
	}
}
