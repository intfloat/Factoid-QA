package edu.pku.retrieval;

import java.io.File;
import java.io.IOException;  
import java.io.StringReader;  
  
import org.apache.lucene.analysis.Analyzer;  
import org.apache.lucene.analysis.TokenStream;  
import org.apache.lucene.document.Document;  
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;  
import org.apache.lucene.search.Query;  
import org.apache.lucene.search.ScoreDoc;  
import org.apache.lucene.search.highlight.Highlighter;  
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;  
import org.apache.lucene.search.highlight.QueryScorer;  
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.store.FSDirectory;  
import org.apache.lucene.util.Version;  
import org.wltea.analyzer.lucene.IKAnalyzer; 

/**
 * 
 * @author Lei Zhao, intfloat@pku.edu.cn
 *
 */
public class HighLighter {  
  
	private static IndexSearcher isearcher = null;

	/**
	 * 
	 * @param input
	 * @param output
	 * @param index
	 * @throws IOException
	 * @throws ParseException
	 * @throws InvalidTokenOffsetsException
	 */
	public static void search(File input, File output, String index) throws IOException, ParseException,
			InvalidTokenOffsetsException {
		QuestionSet qs = new QuestionSet();
		XMLUtils.readXML(input, qs);
		@SuppressWarnings("deprecation")
		IndexReader ireader = IndexReader.open(FSDirectory
				.open(new File(index)));
		
		IndexSearcher isearcher = new IndexSearcher(ireader);
		ScoreDoc[] hits = null;

		Query query = null;
		Analyzer analyzer = new IKAnalyzer();

		@SuppressWarnings("deprecation")
		QueryParser parser = new QueryParser(Version.LUCENE_36, "body",
				analyzer);		
		
		for (int cnt = 0; cnt < qs.getQs().size(); ++cnt) {
			if (cnt > 0 && cnt % 50 == 0) {
				System.out.println("Progress: " + cnt + " questions...");
//				break;
			}			
			Question question = qs.getQs().get(cnt);			
			String key = question.getQuery().get(0);
			query = parser.parse(key);
			hits = isearcher.search(query, null, 10).scoreDocs;
	
			Highlighter hl = new Highlighter(new QueryScorer(query));
			hl.setTextFragmenter(new SimpleFragmenter(100));
//			System.out.println(query.toString());
	
//			just for debug purpose
			System.out.println("找到:" + hits.length + " 个结果!");
	
			// Iterate through the results:
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				TokenStream ts = analyzer.tokenStream("body", new StringReader(
						hitDoc.getValues("body")[0]));
				String fragment = hl.getBestFragment(ts, hitDoc.getValues("body")[0]);
				fragment = fragment.replace("<B>", "");
				fragment = fragment.replace("</B>", "");
				question.getSummary().add(fragment);
				
//				just for debug
				System.out.println(hits[i].score + fragment);
	        } // end inner for loop
		} // end external for loop		

		XMLUtils.writeXML(output, qs);
		System.out.println("Done.");
		return;
		
    } // end method search
    
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 * @throws InvalidTokenOffsetsException
	 */
    public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
    	search(new File("D:/Documents/GitHub/pkuqa/data/stage2_big.xml"), 
    			new File("D:/Documents/GitHub/pkuqa/data/stage3_aux.xml"),
    			"D:/Documents/GitHub/index");
    	return;
    } // end method main
      
} // end class HighLighter