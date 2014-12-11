package edu.pku.QRanking;

import java.io.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.pku.QRanking.answerscore.CombinationAnswerScorer;
import edu.pku.QRanking.summaryscore.CombinationSummaryScorer;
import edu.pku.QRanking.util.NLPTools;

/**
 * Answer Extraction
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */

public class AnswerExtraction {
	List<Question> questions = new ArrayList<Question>();
	AnswerSelector selector = new AnswerSelector();
	CombinationSummaryScorer summaryScorer = new CombinationSummaryScorer();
	CombinationAnswerScorer answerScorer = new CombinationAnswerScorer();

	public void extractAnswer(String inputFileName, String outputFileName)
			throws Exception {
		NLPTools.initalize();
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		dbf = DocumentBuilderFactory.newInstance();
		Element root;
		Document doc;
		try {
			db = dbf.newDocumentBuilder();
			doc = null;
			doc = db.parse(inputFileName);
			root = doc.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("question");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Question question = new Question();

				question.setId(((Element) nodeList.item(i)).getAttributes()
						.getNamedItem("id").getNodeValue());
				question.setTitle(NLPTools.segment(((Element) nodeList.item(i))
						.getElementsByTagName("q").item(0).getTextContent()));
				question.setTagged_title(NLPTools.postag(question.getTitle()));

				String category = ((Element) nodeList.item(i))
						.getElementsByTagName("category").item(0)
						.getTextContent();

				
				if (category.contains("Person"))
					question.setCategory(QuestionCategory.PERSON_NAME);
				else if (category.contains("Location"))
					question.setCategory(QuestionCategory.LOCATION_NAME);
				else if (category.contains("Number"))
					question.setCategory(QuestionCategory.NUMBER);
				else if (category.contains("Other"))
					question.setCategory(QuestionCategory.OTHER);
				else
					question.setCategory(QuestionCategory.NULL);

				if (((Element) nodeList.item(i))
						.getElementsByTagName("summary").getLength() == 1) {
					question.setUnique_answer(true);
					Answer right_one = new Answer();
					right_one.setAnswer_content(((Element) nodeList.item(i))
							.getElementsByTagName("summary").item(0)
							.getTextContent());
					right_one.setScore(100);
					question.getAnswers().add(right_one);

				} else {
					for (int j = 0; j < ((Element) nodeList.item(i))
							.getElementsByTagName("summary").getLength(); j++) {
						question.setUnique_answer(false);

						Summary new_summary = new Summary();

						new_summary.setSummary_content(NLPTools
								.segment(((Element) nodeList.item(i))
										.getElementsByTagName("summary")
										.item(j).getTextContent()));
						new_summary.setTagged_summary(NLPTools
								.postag(new_summary.getSummary_content()));
						new_summary.setScore(0);
						question.getSummarys().add(new_summary);
					}
				}
				questions.add(question);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get candidate answers
		for (Question question : questions) {
			if (question.isUnique_answer() == false)
				selector.select(question);
		}

		// score summarys
		for (Question question : questions) {
			if (question.isUnique_answer() == false) {
				summaryScorer.score(question);
			}
		}

		// score answers
		for (Question question : questions) {
			answerScorer.score(question);
		}

		// show answers
		File output = new File(outputFileName);
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		for (Question question : questions) {
			if (question.getSynthesized_answers().size() == 0) {
				writer.write(question.getId() + "\t" + " " + "\n");
			} else {
				writer.write(question.getId() + "\t"
						+ question.getSynthesized_answers().get(0).answer
						+ "\n");

				for (SynthesizedAnswer answer : question
						.getSynthesized_answers()) {
					System.out.println(question.getId() + "\t" + answer.answer
							+ " " + answer.score);
				}
			}
		}
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		AnswerExtraction ae = new AnswerExtraction();
		if (args.length == 0)
			ae.extractAnswer("stage3.xml", "stage4.xml");
		else if (args.length == 2)
			ae.extractAnswer(args[0], args[1]);

	}

}
