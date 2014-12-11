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
		switch(question.getCategory())
		{
		case PERSON_NAME:
		{
			for(Summary evidence: question.getSummarys())
			{
				for(TaggedWord word:evidence.getTagged_summary())
				{
					if(word.tag().equals("NR") && word.word().length() != 1)
					{
						Answer new_one = new Answer();
						new_one.setAnswer_content(word.word());
						new_one.setSummary(evidence);
						question.getAnswers().add(new_one);
					}
				}
			}
			break;
		}
		case LOCATION_NAME:
		{
			for(Summary evidence: question.getSummarys())
			{
				for(TaggedWord word:evidence.getTagged_summary())
				{
					if(word.tag().equals("NR") && word.word().length() != 1)
					{
						Answer new_one = new Answer();
						new_one.setAnswer_content(word.word());
						new_one.setSummary(evidence);
						question.getAnswers().add(new_one);
					}
				}
			}
			break;
		}
		case NUMBER:
		{
			for(Summary evidence: question.getSummarys())
			{
				for(TaggedWord word:evidence.getTagged_summary())
				{
					if(word.tag().equals("NT")||word.tag().equals("CD") && word.word().length() != 1)
					{
						Answer new_one = new Answer();
						new_one.setAnswer_content(word.word());
						new_one.setSummary(evidence);
						question.getAnswers().add(new_one);
					}
				}
			}
			break;
		}
		case OTHER:
		{
			for(Summary evidence: question.getSummarys())
			{
				for(TaggedWord word:evidence.getTagged_summary())
				{
					if(word.tag().equals("NR")||word.tag().equals("VV")||word.tag().equals("NN") && word.word().length() != 1)
					{
						Answer new_one = new Answer();
						new_one.setAnswer_content(word.word());
						new_one.setSummary(evidence);
						question.getAnswers().add(new_one);
					}
				}
			}
			break;
		}
		}
	}
	
	
	
	
}
