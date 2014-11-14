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
	
	private static final String input = "D:/Documents/GitHub/pkuqa/interface/stage2.xml";
	private static final String output = "out.xml";
	private static QuestionSet qs = new QuestionSet();
	private static BaiduCrawler baidu = new BaiduCrawler();
	private static GoogleCrawler google = new GoogleCrawler();
	private static Log LOG = LogFactory.getLog(EntryPoint.class);	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XMLUtils.readXML(new File(input), qs);
		ArrayList<Question> arr = qs.getQs();
		LOG.info("arr length: " + qs.getQs().size());
		for (Question q : arr) {
			q.setSummary(baidu.getSearchResult(q.getQuestion()));
		}
		LOG.info("qs length: " + qs.getQs().size());
		XMLUtils.writeXML(new File(output), qs);
		return;
	} // end method main

} // end class EntryPoint
