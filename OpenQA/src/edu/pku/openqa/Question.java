package edu.pku.openqa;

import java.util.ArrayList;

/**
 * 
 * @author Liang Wang
 * 
 * represent a single question
 *
 */
public class Question {
	
	private int ID;
	private String question;
	private String category;
	private ArrayList<String> query;
	private ArrayList<String> summary;	
	
	/**
	 * empty constructor
	 */
	public Question() {
		this.ID = -1;
		this.query = this.summary = null;
		this.question = this.category = null;
	}
	
	/**
	 * nontrivial constructor
	 * 
	 * @param ID
	 * @param question
	 * @param category
	 * @param query
	 * @param summary
	 */
	public Question(int ID, String question, String category, 
			        ArrayList<String> query, 
			        ArrayList<String> summary) {
		this.ID = ID;
		this.query = query;
		this.question = question;
		this.category = category;
		this.summary = summary;
	}
	
	/**
	 * @return the summary
	 */
	public ArrayList<String> getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(ArrayList<String> summary) {
		this.summary = summary;
	}
	/**
	 * @return the query
	 */
	public ArrayList<String> getQuery() {
		return query;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(ArrayList<String> query) {
		this.query = query;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}
	
	@Override
	public String toString() {
		return "ID: " + this.ID + " category: " + this.category
				+ " question: " + this.question;
	}

} // end class Question
