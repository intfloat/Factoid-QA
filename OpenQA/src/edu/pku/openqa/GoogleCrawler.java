package edu.pku.openqa;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author Liang Wang
 * 
 * crawl and parse data from <a href = 'http://www.google.com'>google.com</a>
 *
 */
public class GoogleCrawler implements Crawler {
	
	private static Log LOG = LogFactory.getLog(GoogleCrawler.class);

	@Override
	public ArrayList<String> getSearchResult(String query) {
		// TODO Auto-generated method stub
		ArrayList<String> result = new ArrayList<String>();
		HttpClient httpClient = new HttpClient();		
		try {
			query = query.trim();
			query = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			LOG.error("fail to encode into UTF-8 format");
			return null;
		}
		String url = "http://ajax.googleapis.com/ajax/services/search/web?start=0&v=1.0&rsz=large&q=" + query;
        GetMethod getMethod = new GetMethod(url);
        
        try {
	        LOG.info("status code: " + httpClient.executeMethod(getMethod));
	        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
	                new DefaultHttpMethodRetryHandler());
	
	        int statusCode = httpClient.executeMethod(getMethod);
	        LOG.info("status code: " + statusCode);
	        if (statusCode != HttpStatus.SC_OK) {
	            LOG.error("Method failed: " + getMethod.getStatusLine());
	        }
	        byte[] responseBody = getMethod.getResponseBody();
	        String response = new String(responseBody, "UTF-8");
	        LOG.info("response data: " + response);
	        
	        JSONObject json = new JSONObject(response);	
	        JSONArray results = json.getJSONObject("responseData").getJSONArray("results");
	
	        LOG.debug(" Results:");
	        for (int i = 0; i < results.length(); i++) {            
	            JSONObject obj = results.getJSONObject(i);
	            String title = obj.getString("titleNoFormatting");
	            LOG.debug(title);           
	            String content = obj.get("content").toString();
	            content = content.replaceAll("<b>", "");
	            content = content.replaceAll("</b>", "");
            	content = content.replaceAll("\\.\\.\\.", "");
            	LOG.debug(content);            
            	result.add(title + ":" + content);
        	}
        } catch (Exception e) {
        	LOG.warn("failed to get search result for: " + query);
        }        
        
		return result;
	} // end method getSearchResult

} // end class GoogleCrawler
