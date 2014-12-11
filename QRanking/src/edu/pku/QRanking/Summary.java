package edu.pku.QRanking;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.TaggedWord;

/**
 * Answer Extraction 
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */
public class Summary {
	private float score;
	
	private List<String> summary_content;
	private List<TaggedWord> tagged_summary;
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public List<String> getSummary_content() {
		return summary_content;
	}
	public void setSummary_content(List<String> summary_content) {
		this.summary_content = summary_content;
	}
	public List<TaggedWord> getTagged_summary() {
		return tagged_summary;
	}
	public void setTagged_summary(List<TaggedWord> tagged_summary) {
		this.tagged_summary = tagged_summary;
	}
	
	
}
