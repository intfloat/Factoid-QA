package edu.pku.QRanking.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class NLPTools {

	private static final String basedir = System.getProperty("SegDemo", "data");
	public NLPTools() throws Exception
	{
		initalize();
	}
	


	static private Properties props;
	static private CRFClassifier<CoreLabel> segmenter;
	static private MaxentTagger tagger;
	// static private LexicalizedParser lp;
	static private AbstractSequenceClassifier<CoreLabel> classifier;

	static void initalize() throws Exception {
		System.setOut(new PrintStream(System.out, true, "utf-8"));
		props = new Properties();
		props.setProperty("sighanCorporaDict", basedir);
		// props.setProperty("NormalizationTable", "data/norm.simp.utf8");
		// props.setProperty("normTableEncoding", "UTF-8");
		// below is needed because CTBSegDocumentIteratorFactory accesses it
		props.setProperty("serDictionary", basedir + "/dict-chris6.ser.gz");
		props.setProperty("inputEncoding", "UTF-8");
		props.setProperty("sighanPostProcessing", "true");
		segmenter = new CRFClassifier<CoreLabel>(props);
		segmenter.loadClassifierNoExceptions(basedir + "/ctb.gz", props);
		tagger = new MaxentTagger("./models/chinese-distsim.tagger");

	}

	static String ner(List<String> segmented) {
		String sentence = "";
		for (String word : segmented) {
			sentence += word + " ";
		}
		String result = classifier.classifyToString(sentence);
		System.out.print(result);
		return result;
	}

	/*
	 * static Tree parse(List<String> segmented) {
	 * 
	 * String[] sent = new String[segmented.size()]; int i = 0; for (String word
	 * : segmented) { sent[i++] = word; } List<CoreLabel> rawWords =
	 * Sentence.toCoreLabelList(sent); Tree parse = lp.apply(rawWords);
	 * parse.pennPrint(); System.out.println(); return parse; }
	 */
	public static ArrayList<TaggedWord> postag(List<String> segmented) {
		/*
		String sentence = "";
		for (String word : segmented) {
			sentence += word + " ";
		}
		// System.out.println(sentence+"\n");
		String tSentence = tagger.tagString(sentence);
		*/
	//	System.out.println(tSentence + "\n");


		List<HasWord> sentence2 = new ArrayList<HasWord>();
		for (String word : segmented) {
			sentence2.add(new Word(word));
		}
		ArrayList<TaggedWord> tSentence2 = tagger.tagSentence(sentence2);
		System.out.println(Sentence.listToString(tSentence2, false));
		return tSentence2;
	}

	public static List<String> segment(String sentence) {
		List<String> segmented = segmenter.segmentString(sentence);
	//	System.out.println(segmented);
		return segmented;
	}
}
