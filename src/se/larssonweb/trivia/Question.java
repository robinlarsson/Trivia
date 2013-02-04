package se.larssonweb.trivia;

import java.util.ArrayList;

/**
 * 
 * @author Robin Larsson
 * @date Feb 4, 2013
 *
 */
public class Question {
	
	private String name = "";
	private int question_nbr = 0;
	private ArrayList<Answer> answerList = new ArrayList<Answer>();

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public int getQuestionNbr() {
		return question_nbr;
	}

	/**
	 * 
	 * @param question_nbr
	 */
	public void setQuestionNbr(int question_nbr) {
		this.question_nbr = question_nbr;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Answer> getAnswerList() {
		return answerList;
	}

	/**
	 * 
	 * @param answerList
	 */
	public void setAnswerList(ArrayList<Answer> answerList) {
		this.answerList = answerList;
	}
	
	/**
	 * 
	 * @param answer
	 */
	public void addToAnswerList(Answer answer) {
		this.answerList.add(answer);
	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	public Answer getAnswer(int number) {
		return this.getAnswerList().get(number);
	}

}