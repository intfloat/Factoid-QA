package edu.pku.QRanking.evidencescore;

import java.util.ArrayList;
import java.util.List;

import edu.pku.QRanking.Question;

/**
 * Answer Extraction
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */
public class CombinationEvidenceScorer implements EvidenceScorer {

	private final List<EvidenceScorer> scorers = new ArrayList<>();
	float weight;

	public CombinationEvidenceScorer() {
		BigramEmergeScorer bes = new BigramEmergeScorer();
		SkipBigramEmergeScorer sbes = new SkipBigramEmergeScorer();
		TermEmergeScorer tes = new TermEmergeScorer();
		bes.weight = (float) 0.1;
		sbes.weight =(float) 0.1;
		tes.weight = (float) 0.1;
		scorers.add(bes);
		scorers.add(tes);
		scorers.add(sbes);
	}

	@Override
	public void score(Question question) {
		// TODO Auto-generated method stub
		for (EvidenceScorer scorer : scorers) {
			scorer.score(question);
		}
	}

}
