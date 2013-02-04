package se.larssonweb.trivia;

/**
 * 
 * @author Robin Larsson
 * @date Feb 4, 2013
 *
 */
public class Answer {
	
	private String name = "";
	private boolean is_correct = false;
	private Question question = new Question();	

	/**
	 * 
	 * @return the answers name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param answers name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return 
	 */
	public boolean getIsCorrect() {
		return is_correct;
	}

	/**
	 * 
	 * @param correct
	 */
	public void setIsCorrect(boolean is_correct) {
		this.is_correct = is_correct;
	}

	/**
	 * 
	 * @return question that the answer belongs to
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * 
	 * @param question to set answer
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

}