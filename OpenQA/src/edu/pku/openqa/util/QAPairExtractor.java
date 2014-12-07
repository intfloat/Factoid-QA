package edu.pku.openqa.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class QAPairExtractor {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	private static Log LOG = LogFactory.getLog(QAPairExtractor.class);
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner cin = new Scanner(new File("questions.txt"));
		FileWriter writer = new FileWriter("pair_v3.txt");
		int cnt = 0;
		while (cin.hasNextLine()) {
			String line = cin.nextLine().trim();
//			LOG.info(line);
			if (!line.contains("?")) continue;
			int pos = line.lastIndexOf("?");
			String question = line.substring(0, pos + 1).trim();
			int i = 0;
			for (i = 0; i < question.length(); ++i) {
				char c = question.charAt(i);
				if (c >= '0' && c <= '9') continue;
				if (c == ' ' || c == '.' || c == '、' 
						|| c == ':' || c == '：'
						|| c == '，' || c == '，' 
						|| c == '、' ) continue;
				break;
			}
			question = question.substring(i).trim();
			String answer = line.substring(pos + 1).trim();
			if (question.length() <= 5 || answer.length() <= 1) continue;
//			System.out.println(question + " vs " + answer);
			++cnt;
			writer.write(question + " vs " + answer + "\n");
//			LOG.info(question + " vs " + answer);
		}
		writer.flush();
		System.out.println("found " + cnt + " <question, answer> pairs.");
		return;
	}

}
