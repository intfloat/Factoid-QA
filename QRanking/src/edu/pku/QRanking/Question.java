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

	public QuestionType type;
	List<String> title;
	ArrayList<TaggedWord> tagged_title = new ArrayList<>();
	
	List<Evidence> evidences = new ArrayList<>();
	List<Answer> answers = new ArrayList<>();
	
}
