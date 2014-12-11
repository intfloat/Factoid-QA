package edu.pku.QRanking.summaryscore;

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
public class CombinationSummaryScorer implements SummaryScorer {

	private final List<SummaryScorer> scorers = new ArrayList<SummaryScorer>();
	float weight;

	public CombinationSummaryScorer() {
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
		for (SummaryScorer scorer : scorers) {
			scorer.score(question);
		}
	}

}
