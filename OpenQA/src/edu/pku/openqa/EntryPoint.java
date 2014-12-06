package edu.pku.openqa;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.pku.openqa.util.XMLUtils;


/**
 * 
 * @author Liang Wang
 *
 */
public class EntryPoint {
	
//	private static final String input = "D:/Documents/GitHub/pkuqa/interface/stage2.xml";
	private static final String input = "D:/Documents/GitHub/pkuqa/data/stage2_big.xml";
	private static final String output = "D:/Documents/GitHub/pkuqa/data/out";
	private static QuestionSet qs = new QuestionSet();
	private static BaiduCrawler baidu = new BaiduCrawler();
	private static GoogleCrawler google = new GoogleCrawler();
	private static Log LOG = LogFactory.getLog(EntryPoint.class);	

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		XMLUtils.readXML(new File(input), qs);
		ArrayList<Question> arr = qs.getQs();		
		LOG.info("arr length: " + arr.size());
		int begin = 0;
		int end = arr.size();
		for (int i = begin; i <= end; ++i) {			
			if (i > begin && i % 50 == 0) {
				LOG.info("Crawled " + i + " pages.... sleep for 5 seconds...");
				Thread.sleep(5000);
				QuestionSet cur = new QuestionSet();
				for (int j = i - 50; j < i; ++j) {
					cur.addQuestion(qs.getQs().get(j));
				}
				LOG.info("qs length: " + cur.getQs().size());
				XMLUtils.writeXML(new File(output + "_baidu_" + (i / 50) + ".xml"), cur);
//				break;
			}
			if (i == end) break;
			Question q = arr.get(i);
			q.setSummary(baidu.getSearchResult(q.getQuestion()));
//			q.setSummary(google.getSearchResult(q.getQuestion()));
		}
//		LOG.info("qs length: " + qs.getQs().size());
//		XMLUtils.writeXML(new File(output), qs);
		return;
	} // end method main

} // end class EntryPoint
