package edu.pku.QRanking;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;

/**
 * Answer Extraction 
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */
public class AnswerSelector {

	
	public void select(Question question)
	{
		switch(question.type)
		{
		case PERSON_NAME:
		{
			for(Evidence evidence: question.evidences)
			{
				for(TaggedWord word:evidence.tagged_evidence)
				{
					if(word.tag().equals("NR"))
					{
						Answer new_one = new Answer();
						new_one.answer_content = word.word();
						new_one.evidence = evidence;
						question.answers.add(new_one);
					}
				}
			}
			break;
		}
		case LOCATION_NAME:
		{
			for(Evidence evidence: question.evidences)
			{
				for(TaggedWord word:evidence.tagged_evidence)
				{
					if(word.tag().equals("NR"))
					{
						Answer new_one = new Answer();
						new_one.answer_content = word.word();
						new_one.evidence = evidence;
						question.answers.add(new_one);
					}
				}
			}
			break;
		}
		case NUMBER:
		{
			for(Evidence evidence: question.evidences)
			{
				for(TaggedWord word:evidence.tagged_evidence)
				{
					if(word.tag().equals("NT")||word.tag().equals("CD"))
					{
						Answer new_one = new Answer();
						new_one.answer_content = word.word();
						new_one.evidence = evidence;
						question.answers.add(new_one);
					}
				}
			}
			break;
		}
		case OTHER:
		{
			for(Evidence evidence: question.evidences)
			{
				for(TaggedWord word:evidence.tagged_evidence)
				{
					if(word.tag().equals("NR")||word.tag().equals("VV")||word.tag().equals("NN"))
					{
						Answer new_one = new Answer();
						new_one.answer_content = word.word();
						new_one.evidence = evidence;
						question.answers.add(new_one);
					}
				}
			}
			break;
		}
		}
	}
	
	
	
	
}
