package edu.pku.openqa;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * 
 * @author Liang Wang
 *
 */
public class GoogleCrawlerTest {

	@Test
	public void SearchResultNotEmpty() {
		GoogleCrawler crawler = new GoogleCrawler();
		String query = "中国的首都是哪座城市？";
		ArrayList<String> result = crawler.getSearchResult(query);
		assertTrue(result != null);
		for (String snippet : result) {
			System.out.println(snippet);
			assertTrue(snippet.length() > 0);
		}
	} // end testcase SearchResultNotEmpty

} // end class GoogleCrawlerTest
