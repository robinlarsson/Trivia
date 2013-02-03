package com.umu.assignment1;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// used example from http://www.mysamplecode.com/2011/11/android-parse-xml-file-example-using.html
public class QuestionXMLHandler extends DefaultHandler {
		 
    public Boolean currentElement = false;
    public String currentValue = "";
    public Answer answer;    
    public Question question;
    private ArrayList<Question> questionList = new ArrayList<Question>();
 
    // Called when tag starts 
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
	       		
	       		this.answer.setCorrect(Integer.parseInt(attributes.getValue("correct")));
	       	}        	
        }
    }
 
    // Called when tag closing 
    @Override
    public void endElement(String uri, String localName, String qName)
    						throws SAXException {
 
    	this.currentElement = false;
 
        /** set value */
        if (localName.equalsIgnoreCase("item")) {
        	
        	this.question.setQuestion(this.currentValue);
        }
        else if (localName.equalsIgnoreCase("question_nbr")) {
        	
        	this.question.setQuestionNbr(Integer.parseInt(this.currentValue));
        }        
        else if (localName.equalsIgnoreCase("answer")) {
        	
        	this.answer.setAnswer(this.currentValue);
        	this.answer.setQuestion(this.question);
        	this.question.addToAnswerList(this.answer);
        }        
        else if (localName.equalsIgnoreCase("question")) {
        	
        	// found ending question tag and adding question object to the list
            this.questionList.add(this.question);
        }
    }
 
    // Called to get tag characters 
    @Override
    public void characters(char[] ch, int start, int length)
    						throws SAXException {
 
        if (this.currentElement) {
        	
        	this.currentValue = this.currentValue +  new String(ch, start, length);
        }
 
    }
    
    public ArrayList<Question> getQuestionsList() {
        return questionList;
    }
    
}