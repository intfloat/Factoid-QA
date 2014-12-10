

import java.util.ArrayList;

/**
 * 
 * @author Liang Wang
 *
 */
public class QuestionSet {
	
	private ArrayList<Question> qs;
	
	/**
	 * constructor
	 */
	public QuestionSet() {
		this.qs = new ArrayList<Question>();
	}
	
	/**
	 * 
	 * @param q
	 * @return
	 */
	public boolean addQuestion(Question q) {
		if (null == q) return false;
		this.qs.add(q);
		return true;
	}

	/**
	 * @return the qs
	 */
	public ArrayList<Question> getQs() {
		return qs;
	}

	/**
	 * @param qs the qs to set
	 */
	public void setQs(ArrayList<Question> qs) {
		this.qs = qs;
	}

} // end class QuestionSet
