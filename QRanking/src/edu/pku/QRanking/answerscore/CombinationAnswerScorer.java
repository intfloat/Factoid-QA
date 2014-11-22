package edu.pku.QRanking.answerscore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import edu.pku.QRanking.Answer;
import edu.pku.QRanking.Question;
import edu.pku.QRanking.QuestionType;
import edu.pku.QRanking.SynthesizedAnswer;

/**
 * Answer Extraction
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */
public class CombinationAnswerScorer implements AnswerScorer {

	private final List<AnswerScorer> scorers = new ArrayList<>();
	float weight;

	public void addCandidateAnswerScore(AnswerScorer scorer) {
		scorers.add(scorer);
	}

	public CombinationAnswerScorer() {
		TfScorer tfs = new TfScorer();
		TermDistanceScorer tds = new TermDistanceScorer();
		TermMiniDistanceScorer tmds = new TermMiniDistanceScorer();
		TermAlignmentScorer tas= new TermAlignmentScorer();
		LooseTermAlignmentScorer ltas= new LooseTermAlignmentScorer();
		tfs.weight = 1;
		tds.weight = 5;
		tmds.weight = 1;
		tas.weight = 12;
		ltas.weight = 8;
		addCandidateAnswerScore(tfs);
		addCandidateAnswerScore(tds);
		addCandidateAnswerScore(tmds);
		addCandidateAnswerScore(tas);
		addCandidateAnswerScore(ltas);
	}

	@Override
	public void score(Question question) {
		List<Answer> cached = new ArrayList<>();
		List<SynthesizedAnswer> for_merge = new ArrayList<>();
		List<SynthesizedAnswer> synthesizedanswer = new ArrayList<>();
		Map<String, Float> score_map= new HashMap<>();
		
		for (Answer answer : question.answers) {
			if (question.title.contains(answer.answer_content)) {
				cached.add(answer);
				continue;
			}
		}
		
		//delete those answers in question titles
		for(Answer answer : cached)
		{
			question.answers.remove(answer);
		}
		
		
		for (AnswerScorer scorer : scorers) {
			scorer.score(question);
		}
		
		
		//synthesize answers final_score = sum(answer_score in all evidences)+sum(evidence_score which contains this answer)
		for (Answer answer : question.answers) {
			if (score_map.get(answer.answer_content) == null) {
				score_map.put(answer.answer_content,answer.score+answer.evidence.score);
			}
			else
			{
				float score = score_map.get(answer.answer_content);
				score += answer.score+answer.evidence.score;
				score_map.put(answer.answer_content,score);
			}
		}
		
        //move synthesized answers from map to list
		for (Map.Entry<String, Float> entry : score_map.entrySet()) {
            String answer = entry.getKey();
            float score = entry.getValue();
            SynthesizedAnswer new_synthesizedanswer = new SynthesizedAnswer();
            new_synthesizedanswer.answer = answer;
            new_synthesizedanswer.score = score;
            synthesizedanswer.add(new_synthesizedanswer);
		}
		
		//just for test ,for person name problems 厄尔诺·鲁比克 include 鲁毕克 so maybe we should merge them
		if(question.type == QuestionType.PERSON_NAME)
		{

			for(SynthesizedAnswer sa:synthesizedanswer)
			{
				for(SynthesizedAnswer sa2:synthesizedanswer)
				{
					if(sa.answer.contains(sa2.answer) && !sa.answer.equals(sa2.answer))
					{
						System.out.println("cached remove:"+sa.answer+" "+sa2.answer);
						sa.score += sa2.score;
						for_merge.add(sa2);
					}
				}
			}
			
			for(SynthesizedAnswer sa:for_merge)
			{
				System.out.println("remove:"+sa.answer);
				synthesizedanswer.remove(sa);
			}
			
		}
		
		// sort answers
		Collections.sort(synthesizedanswer);
		Collections.reverse(synthesizedanswer);
		
		question.synthesized_answers = synthesizedanswer;
	}
}
