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

	public QuestionCategory category;
	public String id;
	public List<String> title;
	public ArrayList<TaggedWord> tagged_title = new ArrayList<TaggedWord>();
	
	public List<Summary> summarys = new ArrayList<Summary>();
	
	//these answers are belongs
	public List<Answer> answers = new ArrayList<Answer>();
	
	public List<SynthesizedAnswer> synthesized_answers = new ArrayList<SynthesizedAnswer>();
	
}
