import java.io.File;
import java.io.IOException;  
import java.io.StringReader;  
  
import org.apache.lucene.analysis.Analyzer;  
import org.apache.lucene.analysis.TokenStream;  
import org.apache.lucene.analysis.standard.StandardAnalyzer;  
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;  
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;  
import org.apache.lucene.document.Document;  
import org.apache.lucene.index.IndexReader;  
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;  
import org.apache.lucene.queryParser.QueryParser;  
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;  
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;  
import org.apache.lucene.search.BooleanClause; 
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.ScoreDoc;  
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;  
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;  
import org.apache.lucene.search.highlight.QueryScorer;  
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.store.Directory;  
import org.apache.lucene.store.FSDirectory;  
import org.apache.lucene.util.Version;  
import org.wltea.analyzer.lucene.IKAnalyzer; 

public class aa {  
  
    private static IndexSearcher isearcher = null;  
    public static void search(String key) throws IOException, ParseException, InvalidTokenOffsetsException{  
    	 String index = "D:\\index";         //搜索的索引路径  
    	// Directory directory=FSDirectory.open(new File(index));
         IndexReader ireader = IndexReader.open(FSDirectory.open(new File(index)));  
         // Now search the index:  
       // read-only=true  
     
         IndexSearcher isearcher  = new IndexSearcher(ireader);  
         ScoreDoc[] hits = null;    
     
         Query query = null;  
        // Term t = new Term("body", "奥运会");
         //Term t1 = new Term("body", "是");
         //Query query1 = new PrefixQuery(t);
        // Query query2 = new PrefixQuery(t1);
         // create a boolean query
        // BooleanQuery query = new BooleanQuery();
         //query.add(query1, BooleanClause.Occur.MUST);
       // query.add(query2, BooleanClause.Occur.MUST);

       // TopDocs tops=isearcher.search(query,10);
       // ScoreDoc[] hits = tops.scoreDocs;
         Analyzer analyzer = new IKAnalyzer();
  
        QueryParser parser = new QueryParser(Version.LUCENE_36,"body", analyzer);  
         query = parser.parse(key);  
        // String [] stringQuery={"奥斯卡","最佳"}; 
        // String[] fields={"body","body"};
        // Occur[] occ={Occur.SHOULD,Occur.MUST}; 
        // query=MultiFieldQueryParser.parse(Version.LUCENE_36, stringQuery,fields,occ,analyzer);
       hits = isearcher.search(query, null, 20).scoreDocs;  
          
        Highlighter hl = new Highlighter(new QueryScorer(query));  
         hl.setTextFragmenter(new SimpleFragmenter(300)); 
        System.out.println(query.toString());  
        
        	
            System.out.println("找到:" + hits.length + " 个结果!");    
         
        // Iterate through the results:  
        for (int i = 0; i < hits.length; i++) { 
          Document hitDoc = isearcher.doc(hits[i].doc);  
          TokenStream ts = analyzer.tokenStream("body", new StringReader(hitDoc.getValues("body")[0]));  
          String frament = hl.getBestFragment(ts, hitDoc.getValues("body")[0]);  
          System.out.println(hits[i].score+frament);  
         
//        System.out.println(hitDoc.getValues("id")[0] + "\t" + hitDoc.getValues("context")[0] + "\t" + hits[i].score);  
//        Explanation explan = isearcher.explain(query, hits[i].doc);  
//        System.out.println(explan);  
        }  
    }  
      
    public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {  
        search("甲午标志" );  
       isearcher.close();  
    }  
      
}  