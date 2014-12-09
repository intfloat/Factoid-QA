package edu.pku.QRanking.summaryscore;

import edu.pku.QRanking.Question;

/**
 * Answer Extraction 
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */
public interface EvidenceScorer {

    public void score(Question question);
}
