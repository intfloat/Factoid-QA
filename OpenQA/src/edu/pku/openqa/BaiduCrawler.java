package edu.pku.openqa;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author Liang Wang
 * 
 * crawl and parse html data from <a href = 'http://www.baidu.com'>baidu.com</a>
 *
 */
public class BaiduCrawler implements Crawler {
    
    private static Log LOG = LogFactory.getLog(BaiduCrawler.class);
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
    private static final String ENCODING = "gzip,deflate,sdch";
    private static final String LANGUAGE = "zh-CN,zh;q=0.8,en;q=0.6,pl;q=0.4,zh-TW;q=0.2,ru;q=0.2";
    private static final String CONNECTION = "keep-alive";
    private static final String HOST = "www.baidu.com";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.101 Safari/537.36";
    
    @Override
    public ArrayList<String> getSearchResult(String query) {
        // TODO Auto-generated method stub
        ArrayList<String> result = new ArrayList<String>();
        String url = "http://www.baidu.com/s?wd=" + query;
        LOG.info("start to crawl webpage for " + query + "...");
        try {
            Document document = Jsoup.connect(url)
                    .header("Accept", ACCEPT)
                    .header("Accept-Encoding", ENCODING)
                    .header("Accept-Language", LANGUAGE)
                    .header("Connection", CONNECTION)
                    .header("User-Agent", USER_AGENT)
                    .header("Host", HOST).get();
            String resultCssQuery = "html > body > div > div > div > div > div.result";
            Elements elements = document.select(resultCssQuery);
            for (Element element : elements) {
                Elements subElements = element.select("h3 > a");
                if(subElements.size() != 1){
                    LOG.warn("can not find title");
                    continue;
                }
                String title =subElements.get(0).text();
                if (title == null || "".equals(title.trim())) {
                    LOG.warn("title is empty");
                    continue;
                }
                subElements = element.select("div.c-abstract");
                if(subElements.size() != 1){
                    LOG.warn("can not find abstract");
                    continue;
                }
                String snippet =subElements.get(0).text();
                if (snippet == null || "".equals(snippet.trim())) {
                    LOG.warn("abstract is empty");
                    continue;
                }
                result.add(title + "¡£" + snippet);       
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOG.error("failed to get response for " + query);
        }
        
        return result;
    } // end method getSearchResult

} // end class BaiduCrawler
