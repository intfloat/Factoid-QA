package edu.pku.QRanking;

import java.io.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.pku.QRanking.answerscore.CombinationAnswerScorer;
import edu.pku.QRanking.evidencescore.CombinationEvidenceScorer;
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
	CombinationEvidenceScorer evidenceScorer = new CombinationEvidenceScorer();
	CombinationAnswerScorer answerScorer = new CombinationAnswerScorer();

	public void extractAnswer(String inputFileName, String outputFileName)
			throws Exception {
		NLPTools tool = new NLPTools();

		File input = new File(inputFileName);
		Document doc = Jsoup.parse(input, "UTF-8", "");
		Elements elements = doc.select("QuestionSet > question");
		List<String> newone;
		for (Element element : elements) {
			Question question = new Question();
			
			question.id = element.attr("id");
			// get question
			Elements subElements = element.select("q");
			String title = subElements.get(0).text();
			newone = tool.segment(title);
			question.title = newone;
			question.tagged_title = tool.postag(newone);

			// get question type
			subElements = element.select("category");
			String type = subElements.get(0).text();
			
			if(type.equals("Person"))
				question.type = QuestionType.PERSON_NAME;
			else if(type.equals("Location"))
				question.type = QuestionType.LOCATION_NAME;
			else if(type.equals("Number"))
				question.type = QuestionType.NUMBER;
			else if(type.equals("Other"))
				question.type = QuestionType.OTHER;
			else
				question.type = QuestionType.NULL;
			/*
			switch (type) {
			case "Person":
				question.type = QuestionType.PERSON_NAME;
				break;
			case "Location":
				question.type = QuestionType.LOCATION_NAME;
				break;
			case "Number":
				question.type = QuestionType.NUMBER;
				break;
			case "Other":
				question.type = QuestionType.OTHER;
				break;
			default:
				question.type = QuestionType.NULL;
			}
			 */
			// get evidence
			subElements = element.select("summary");
			float i = 1;
			for (Element subelement : subElements) {
				Evidence new_evidence = new Evidence();
				String evidence = subelement.text();
				newone = tool.segment(evidence);
				new_evidence.evidence_content = newone;
				new_evidence.tagged_evidence = tool.postag(newone);
				new_evidence.score = 0;
				i = i + 1;
				question.evidences.add(new_evidence);
			}

			questions.add(question);
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
			if (question.synthesized_answers.size() == 0)
			{
				writer.write(question.id + "\t" + " "
						+"\n");
			}
			else
			{
			writer.write(question.id + "\t" + question.synthesized_answers.get(0).answer
					+"\n");

			for (SynthesizedAnswer answer : question.synthesized_answers) {
				System.out.println(question.id + "\t" + answer.answer + " " + answer.score);
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
