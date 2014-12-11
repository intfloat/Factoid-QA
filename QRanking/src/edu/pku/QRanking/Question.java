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
public class Question {

	private QuestionCategory category;
	private String id;
	private List<String> title;
	private ArrayList<TaggedWord> tagged_title = new ArrayList<TaggedWord>();
	private boolean unique_answer;
	
	private List<Summary> summarys = new ArrayList<Summary>();
	
	//these answers are belongs
	private List<Answer> answers = new ArrayList<Answer>();
	
	private List<SynthesizedAnswer> synthesized_answers = new ArrayList<SynthesizedAnswer>();

	public QuestionCategory getCategory() {
		return category;
	}

	public void setCategory(QuestionCategory category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getTitle() {
		return title;
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}

	public ArrayList<TaggedWord> getTagged_title() {
		return tagged_title;
	}

	public void setTagged_title(ArrayList<TaggedWord> tagged_title) {
		this.tagged_title = tagged_title;
	}

	public boolean isUnique_answer() {
		return unique_answer;
	}

	public void setUnique_answer(boolean unique_answer) {
		this.unique_answer = unique_answer;
	}

	public List<Summary> getSummarys() {
		return summarys;
	}

	public void setSummarys(List<Summary> summarys) {
		this.summarys = summarys;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public List<SynthesizedAnswer> getSynthesized_answers() {
		return synthesized_answers;
	}

	public void setSynthesized_answers(List<SynthesizedAnswer> synthesized_answers) {
		this.synthesized_answers = synthesized_answers;
	}
	
	
	
	
}
