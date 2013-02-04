package se.larssonweb.trivia;

import java.util.ArrayList;

public class Question {
	
	private String question = null;
	private int question_nbr = 0;
	private ArrayList<Answer> answerList = new ArrayList<Answer>();

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getQuestionNbr() {
		return question_nbr;
	}

	public void setQuestionNbr(int question_nbr) {
		this.question_nbr = question_nbr;
	}

	public ArrayList<Answer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(ArrayList<Answer> answerList) {
		this.answerList = answerList;
	}
	
	public void addToAnswerList(Answer answer) {
		this.answerList.add(answer);
	}

	public Answer getAnswer(int number) {
		return this.getAnswerList().get(number);
	}

}