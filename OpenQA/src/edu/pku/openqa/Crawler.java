package edu.pku.openqa;

import java.util.ArrayList;

/**
 * 
 * @author Liang Wang
 * 
 * crawl search engine webpage and extract contents
 *
 */
public interface Crawler {
	
	/**
	 * 
	 * @param query
	 * @return a list of text snippets returned by search engine
	 */
	public ArrayList<String> getSearchResult(String query);

}
