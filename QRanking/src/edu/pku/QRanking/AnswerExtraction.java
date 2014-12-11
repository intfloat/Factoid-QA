package edu.pku.QRanking;

import java.io.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
			newone = NLPTools.segment(title);
			question.title = newone;
			question.tagged_title = NLPTools.postag(newone);

			// get question type
			subElements = element.select("category");
			String type = subElements.get(0).text();
			
			if(type.equals("Person"))
				question.category = QuestionCategory.PERSON_NAME;
			else if(type.equals("Location"))
				question.category = QuestionCategory.LOCATION_NAME;
			else if(type.equals("Number"))
				question.category = QuestionCategory.NUMBER;
			else if(type.equals("Other"))
				question.category = QuestionCategory.OTHER;
			else
				question.category = QuestionCategory.NULL;
			/*
			switch (type) {
			case "Person":
				question.category = QuestionCategory.PERSON_NAME;
				break;
			case "Location":
				question.category = QuestionCategory.LOCATION_NAME;
				break;
			case "Number":
				question.category = QuestionCategory.NUMBER;
				break;
			case "Other":
				question.category = QuestionCategory.OTHER;
				break;
			default:
				question.category = QuestionCategory.NULL;
			}
			 */
			// get summary
			subElements = element.select("summary");
			if(subElements.size() == 1)
			{
				question.unique_answer = true;
				Answer right_one = new Answer();
				right_one.setAnswer_content(subElements.get(0).text());
				right_one.setScore(100);
				question.answers.add(right_one);
				
			}
			else
			for (Element subelement : subElements) {
				question.unique_answer = false;
				Summary new_summary = new Summary();
				String summary = subelement.text();
				newone = NLPTools.segment(summary);
				new_summary.setSummary_content(newone);
				new_summary.setTagged_summary(NLPTools.postag(newone));
				new_summary.setScore(0);
				question.summarys.add(new_summary);
			}

			questions.add(question);
		}
		// get candidate answers
		for (Question question : questions) {
			if(question.unique_answer == false)
			selector.select(question);
		}

		// score summarys
		for (Question question : questions) {
			if(question.unique_answer == false)
			summaryScorer.score(question);
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
		if(args.length == 0)
			ae.extractAnswer("stage3.xml", "stage4.xml");
		else if(args.length == 2)
			ae.extractAnswer(args[0], args[1]);

	}

}
