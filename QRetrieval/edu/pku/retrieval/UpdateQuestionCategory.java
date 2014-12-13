package edu.pku.retrieval;

import java.io.File;




/**
 * Update category of question without having to rerun crawler or lucene
 * 
 * @author intfloat@pku.edu.cn
 *
 */
public class UpdateQuestionCategory {

	private static final String stage3 = "../data/baidu_crawler_data_v5.xml";
	private static final String stage2 = "../data/stage2.xml";
	private static final String output = "../data/baidu_crawler_data_v6.xml";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuestionSet oldQuestion = new QuestionSet();
		System.out.println("Start to load data...");
		XMLUtils.readXML(new File(stage3), oldQuestion);
		QuestionSet upQuestion = new QuestionSet();
		XMLUtils.readXML(new File(stage2), upQuestion);
		if (oldQuestion.getQs().size() != upQuestion.getQs().size()) {
			System.err.println("Number of questions should be the same.");
			return;
		}
		for (int i = 0; i < oldQuestion.getQs().size(); ++i) {
			oldQuestion.getQs().get(i).setCategory(upQuestion.getQs().get(i).getCategory());
		}
		XMLUtils.writeXML(new File(output), oldQuestion);
		System.out.println("Done.");
		return;
	}

} // end class UpdateQuestionCategory
