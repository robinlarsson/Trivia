package se.larssonweb.trivia;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *  Used example from 
 *  http://www.mysamplecode.com/2011/11/android-parse-xml-file-example-using.html
 * 
 * @author Robin Larsson
 * @date Feb 4, 2013
 *
 */
public class QuestionXMLHandler extends DefaultHandler {
		 
    public Boolean currentElement = false;
    public String currentValue = "";
    public Answer answer;    
    public Question question;
    private int question_nbr = 0;
    private ArrayList<Question> questionList = new ArrayList<Question>();
 
    /**
     *  Called when tag starts 
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
 
        this.currentElement = true;
        this.currentValue = "";
        
        if (localName.equals("question")) {
        	
        	// add new Question object when starting tag is found
            this.question = new Question();
        }
        else if (localName.equals("answer")) {
	       	
        	// add new Answer object when starting tag is found
	       	this.answer = new Answer();
	       	
	       	// only set correct if there is a attribute for it
	       	if(attributes.getValue("correct") != null) {
	       		
	       		this.answer.setIsCorrect(Boolean.parseBoolean(attributes.getValue("correct")));
	       	}        	
        }
    }
 
    /**
     *  Called when tag closing 
     */
    @Override
    public void endElement(String uri, String localName, String qName)
    						throws SAXException {
 
    	this.currentElement = false;
 
        /** set value */
        if (localName.equalsIgnoreCase("item")) {
        	
        	this.question.setName(this.currentValue);
        	// set question number in this class for current question
        	this.setQuestionNbr((this.getQuestionNbr() + 1));
        	// set question number in question for current question
        	this.question.setQuestionNbr(this.getQuestionNbr());
        }        
        else if (localName.equalsIgnoreCase("answer")) {
        	
        	this.answer.setName(this.currentValue);
        	this.answer.setQuestion(this.question);
        	this.question.addToAnswerList(this.answer);
        }        
        else if (localName.equalsIgnoreCase("question")) {
        	
        	// found ending question tag and adding question object to the list
            this.questionList.add(this.question);
        }
    }
 
    /**
     *  Called to get tag characters 
     */
    @Override
    public void characters(char[] ch, int start, int length)
    						throws SAXException {
 
        if (this.currentElement) {
        	
        	this.currentValue = this.currentValue +  new String(ch, start, length);
        }
 
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<Question> getQuestionsList() {
        return questionList;
    }

	/**
	 * @return the question_nbr
	 */
	public int getQuestionNbr() {
		return question_nbr;
	}

	/**
	 * @param question_nbr the question_nbr to set
	 */
	public void setQuestionNbr(int question_nbr) {
		this.question_nbr = question_nbr;
	}
    
}