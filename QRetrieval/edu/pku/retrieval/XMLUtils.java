package edu.pku.retrieval;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author Liang Wang
 *
 */
public final class XMLUtils {    

    private static DocumentBuilderFactory dbf;
    private static DocumentBuilder db;
    private static Document doc;
    private static Element root;    
    
    private static final String QUESTION = "question";
    private static final String QUESTION_SET = "QuestionSet";
    private static final String ID = "id";
    private static final String CATEGORY = "category";
    private static final String Q = "q";
    private static final String SUMMARY = "summary";
    private static final String ENCODING = "utf-8";

    public static boolean readXML(File file, QuestionSet qs) {
        if (!file.exists()) {
            System.err.println("can not find: " + file.toString());
            return false;
        }
        readXMLAux(file);
        NodeList nodeList = root.getElementsByTagName(QUESTION);
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Element element = (Element) nodeList.item(i);
            Question question = new Question();
            question.setID(Integer.parseInt(element.getAttribute(ID)));
            Element q = (Element) element.getElementsByTagName(Q).item(0);
            question.setQuestion(q.getTextContent());
            Element category = (Element) element.getElementsByTagName(CATEGORY).item(0);
            question.setCategory(category.getTextContent());
            Element query = (Element) element.getElementsByTagName("query").item(0);
            System.out.println(i);
            if (query != null)
            	question.getQuery().add(query.getTextContent().trim());
            if (question.getQuery() == null 
            		|| question.getQuery().size() == 0
            		|| question.getQuery().get(0).length() == 0) {
//            	System.err.println("Invalid query for " + i + "th question");
            }
            
//          add summary information if it has
            NodeList sm = element.getElementsByTagName(SUMMARY);
            for (int j = 0; j < sm.getLength(); ++j)
            	question.getSummary().add(sm.item(j).getTextContent().trim());
            
//          insert this question into question set
            qs.addQuestion(question);
//            LOG.info(question);
        }
        return true;
    }
    
    public static boolean writeXML(File file, QuestionSet qs) {
        dbf = DocumentBuilderFactory.newInstance();
        db = null;
        doc = null;
        try {
            db=dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block            
            e.printStackTrace();
        }
        doc = db.newDocument();
        root = doc.createElement(QUESTION_SET);
        doc.appendChild(root);
        
        for (Question q : qs.getQs()) {
            Element element = doc.createElement(QUESTION);
            element.setAttribute(ID, String.valueOf(q.getID()));
            Element qt = doc.createElement(Q);
            qt.setTextContent(q.getQuestion());
            element.appendChild(qt);
            Element category = doc.createElement(CATEGORY);
            category.setTextContent(q.getCategory());
            element.appendChild(category);
            for (String sm : q.getSummary()) {
                Element summary = doc.createElement(SUMMARY);
                summary.setTextContent(sm);
                element.appendChild(summary);
            }
            root.appendChild(element);
//            LOG.info("write: " + q);
        }
        
        //output the xml content into certain file
        FileOutputStream outStream=null;
        try {
            outStream=new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block                
            e.printStackTrace();
        }
            
        OutputStreamWriter outstreamWriter=new OutputStreamWriter(outStream);
//        call writer
        callWriteXmlFile(doc, outstreamWriter, ENCODING);
        try {
            outstreamWriter.close();
        } catch (IOException e) {                    
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            outStream.close();
        } catch (IOException e) {                    
            // TODO Auto-generated catch block
            e.printStackTrace();
        }            
                
        return true;
    }
    
    private static void readXMLAux(File file){
        dbf = DocumentBuilderFactory.newInstance();
        db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        }
        doc = null;
        try {
            doc = db.parse(file);
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        }
        root = doc.getDocumentElement();        
    }  // End method readXML()    
    
    /**
     * 
     * @param Document doc
     * @param Writer w
     * @param encoding
     */
    private static void callWriteXmlFile(Document doc, Writer w, String encoding) {
          try {
              Source source = new DOMSource(doc);
              Result result = new StreamResult(w);
              Transformer xformer = TransformerFactory.newInstance()
                      .newTransformer();
              xformer.setOutputProperty(OutputKeys.ENCODING, encoding);
              xformer.setOutputProperty(OutputKeys.INDENT, "yes");            
              xformer.transform(source, result);
          } 
          catch (TransformerConfigurationException e) {
              e.printStackTrace();
          }
          catch (TransformerException e) {
              e.printStackTrace();
          }
    }  // end method callWriteXmlFile
    

} // end method XMLUtils
