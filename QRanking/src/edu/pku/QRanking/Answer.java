package edu.pku.QRanking;

import edu.stanford.nlp.ling.TaggedWord;

/**
 * Answer Extraction 
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */
public class Answer {
private float score;
private Summary summary;
private String answer_content;
private TaggedWord tagged_answer;
public float getScore() {
	return score;
}
public void setScore(float score) {
	this.score = score;
}
public Summary getSummary() {
	return summary;
}
public void setSummary(Summary summary) {
	this.summary = summary;
}
public String getAnswer_content() {
	return answer_content;
}
public void setAnswer_content(String answer_content) {
	this.answer_content = answer_content;
}
public TaggedWord getTagged_answer() {
	return tagged_answer;
}
public void setTagged_answer(TaggedWord tagged_answer) {
	this.tagged_answer = tagged_answer;
}





}
