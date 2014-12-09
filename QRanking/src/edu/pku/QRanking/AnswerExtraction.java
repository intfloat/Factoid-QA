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
	CombinationSummaryScorer evidenceScorer = new CombinationSummaryScorer();
	CombinationAnswerScorer answerScorer = new CombinationAnswerScorer();

	public void extractAnswer(String inputFileName, String outputFileName)
			throws Exception {
		NLPTools tool = new NLPTools();

		File input = new File(inputFileName);

		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		dbf = DocumentBuilderFactory.newInstance();
		Element root;
		Document doc;

		try {
			db = dbf.newDocumentBuilder();
			doc = null;
			doc = db.parse("stage3.xml");
			root = doc.getDocumentElement();

			NodeList nodeList = root.getElementsByTagName("question");
			for (int i = 0; i < nodeList.getLength(); i++) {

				Question question = new Question();

				question.id = ((Element) nodeList.item(i)).getAttributes()
						.getNamedItem("id").getNodeValue();
				question.title = NLPTools.segment(((Element) nodeList.item(i))
						.getElementsByTagName("q").item(0).getTextContent());
				question.tagged_title = NLPTools.postag(question.title);

				String category = ((Element) nodeList.item(i))
						.getElementsByTagName("category").item(0)
						.getTextContent();

				if (category.equals("Person"))
					question.category = QuestionCategory.PERSON_NAME;
				else if (category.equals("Location"))
					question.category = QuestionCategory.LOCATION_NAME;
				else if (category.equals("Number"))
					question.category = QuestionCategory.NUMBER;
				else if (category.equals("Other"))
					question.category = QuestionCategory.OTHER;
				else
					question.category = QuestionCategory.NULL;

				for (int j = 0; j < ((Element) nodeList.item(i))
						.getElementsByTagName("summary").getLength(); j++) {

					Summary new_summary = new Summary();

					new_summary.evidence_content = NLPTools
							.segment(((Element) nodeList.item(i))
									.getElementsByTagName("summary").item(j)
									.getTextContent());
					new_summary.tagged_evidence = NLPTools
							.postag(new_summary.evidence_content);
					new_summary.score = 0;
					question.summarys.add(new_summary);
				}
				questions.add(question);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get candidate answers
		for (Question question : questions) {
			selector.select(question);
		}

		// score evidences
		for (Question question : questions) {
			evidenceScorer.score(question);
		}

		// score answers
		for (Question question : questions) {
			answerScorer.score(question);
		}

		// show answers
		File output = new File(outputFileName);
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		for (Question question : questions) {
			if (question.synthesized_answers.size() == 0) {
				writer.write(question.id + "\t" + " " + "\n");
			} else {
				writer.write(question.id + "\t"
						+ question.synthesized_answers.get(0).answer + "\n");

				for (SynthesizedAnswer answer : question.synthesized_answers) {
					System.out.println(question.id + "\t" + answer.answer + " "
							+ answer.score);
				}
			}
		}
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		AnswerExtraction ae = new AnswerExtraction();
		ae.extractAnswer("stage3.xml", "stage4.xml");

	}

}
