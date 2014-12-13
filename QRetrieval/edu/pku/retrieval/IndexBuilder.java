package edu.pku.retrieval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

//import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 
 * @author Lei Zhao
 *
 */
public class IndexBuilder {
	
    public static void main(String[] args) throws Exception {        
        File fileDir = new File("D:/Documents/GitHub/wiki");

        File indexDir = new File("D:/Documents/GitHub/index");
        Directory dir = FSDirectory.open(indexDir);
        IKAnalyzer luceneAnalyzer = new IKAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36, luceneAnalyzer);
//        IndexWriterConfig iwc = new IndexWriterConfig(Version.LATEST, luceneAnalyzer);
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(dir, iwc);
        File[] textFiles = fileDir.listFiles();        
        long startTime = new Date().getTime();
        System.out.println(textFiles.length);
        //增加document到索引去
        for (int i = 0; i < textFiles.length; i++) {
        	File[] docs = textFiles[i].listFiles();
        	for (File file : docs) {
            	if (file.isFile()) {            	
	                System.out.println("File " + file.getCanonicalPath()
	                        + " is being indexed....");
	                String temp = FileReaderAll(file.getCanonicalPath(),
	                        "UTF-8");
//	                System.out.println(temp);
	                Document document = new Document();
	                @SuppressWarnings("deprecation")
					Field FieldPath = new Field("path", file.getPath(),
	                        Field.Store.YES, Field.Index.NO);
	                @SuppressWarnings("deprecation")
					Field FieldBody = new Field("body", temp, Field.Store.YES,
	                        Field.Index.ANALYZED,
	                        Field.TermVector.WITH_POSITIONS_OFFSETS);
	                document.add(FieldPath);
	                document.add(FieldBody);
	                indexWriter.addDocument(document);
            	}
        	}
        }
        indexWriter.close();

        //测试一下索引的时间
        long endTime = new Date().getTime();
        System.out.println("It took " + (endTime - startTime) + " ms " + fileDir.getPath());
    }

    public static String FileReaderAll(String FileName, String charset)
            throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FileName), charset));
        String line = new String();
        String temp = new String();

        while ((line = reader.readLine()) != null) {
            temp += line;
        }
        reader.close();
        return temp;
    }
}
