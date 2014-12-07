package edu.pku.openqa;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pku.openqa.util.XMLUtils;

/**
 * 
 * @author intfloat@pku.edu.cn
 *
 */
public class ExactMatcher {

	private static final String QA_PAIRS = "D:/Documents/GitHub/pkuqa/data/pair_v4.txt";
	private static final String BAIDU_DATA = "D:/Documents/GitHub/pkuqa/data/baidu_crawler_data_v4.xml";
	private static final String BAIDU_DATA_V2 = "D:/Documents/GitHub/pkuqa/data/baidu_crawler_data_v5.xml";
	
	private static int minimum(int a, int b, int c) {                            
        return Math.min(Math.min(a, b), c);                                      
    }    
	
	/**
	 * I am too lazy to implement edit distance, 
	 * so I just copy code from 
	 * <a href = 'http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java'>
	 * wiki </a>
	 * 
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	private static int editDistance(String str1, String str2) {
		int[][] distance = new int[str1.length() + 1][str2.length() + 1];        
		 
        for (int i = 0; i <= str1.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= str2.length(); j++)                                 
            distance[0][j] = j;                                                  
 
        for (int i = 1; i <= str1.length(); i++)                                 
            for (int j = 1; j <= str2.length(); j++)                             
                distance[i][j] = minimum(                                        
                        distance[i - 1][j] + 1,                                  
                        distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));
 
        return distance[str1.length()][str2.length()];
	}
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		QuestionSet qs = new QuestionSet();
		XMLUtils.readXML(new File(BAIDU_DATA), qs);
		Scanner reader = new Scanner(new File(QA_PAIRS));
		ArrayList<QAPair> arr = new ArrayList<QAPair>();
		
//		load <Question, Answer> pairs
		while (reader.hasNextLine()) {
			String line = reader.nextLine().trim();
			int pos = line.lastIndexOf("vs");
			String q = line.substring(0, pos).trim();
			String ans = line.substring(pos + 2).trim();
			arr.add(new QAPair(q, ans));
		}
		
		System.out.println("Found " + arr.size() + " QA Pairs.");
		
		ArrayList<Question> questionArr = qs.getQs();
		int matchCnt = 0;
//		update search engine based summary
		for (int i = 0; i < questionArr.size(); ++i) {
			Question q = questionArr.get(i);
			if (i % 100 == 0) {
				System.out.println("Progress: " + i + " questions...");
			}
			q.setQuestion(q.getQuestion().trim());
			for (int j = 0; j < arr.size(); ++j) {
				QAPair pair = arr.get(j);
				int dis = editDistance(q.getQuestion(), pair.question);
				if ((dis < 3 && pair.question.length() < 20) 
						|| (dis < 8 && pair.question.length() >= 20)) {
					System.out.println("Got " + matchCnt + " match: " + q.getQuestion() + " vs " + pair.question);
					++matchCnt;
					q.getSummary().clear();
					q.getSummary().add(pair.answer);
					arr.remove(j);
					break;
				}
			}
		} // end outer for loop
		
		XMLUtils.writeXML(new File(BAIDU_DATA_V2), qs);
	} // end method main

} // end class ExactMatcher
