package com.umu.assignment1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Play extends Activity {
	
	private int right_answers = 0;
	private int wrong_answers = 0;
	private int question_nbr = 0;
	
	private ArrayList<Question> questionList = new ArrayList<Question>();
	private SharedPreferences mPrefs;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		// get the preferences from previous games
		this.getPref();
		// parse the XML file that is container for the questions
		this.parseXML();
		// start the poll with same question number
		this.next(0);
	}
	
    protected void onPause() {
        super.onPause();
        // save preferences when activity goes in background or is recreated
        this.setPref();
    }
    
    // get the preferences from previous games
    private void getPref() {
    	
    	// get preferences for assignment1
    	this.mPrefs = getSharedPreferences("assignment1", 0);
    	this.setRightAnswers(mPrefs.getInt("right_answers", this.getRightAnswers()));
    	this.setWrongAnswers(mPrefs.getInt("wrong_answers", this.getWrongAnswers()));
    	this.setQuestionNbr(mPrefs.getInt("question_nbr", this.getQuestionNbr()));
    }
    
    private void setPref() {    	
    	
    	// start editor for preferences and set latest values
    	SharedPreferences.Editor ed = this.mPrefs.edit();
        ed.putInt("right_answers", this.getRightAnswers());
        ed.putInt("wrong_answers", this.getWrongAnswers());
        ed.putInt("question_nbr", this.getQuestionNbr());
        ed.commit();
    }
	
    // when start button on result screen is pressed
	public void onClickRestart(View view) {
		
		this.restartPlay();
	}
	
	// reset all the values
	public void resetValues() {
		
		this.setRightAnswers(0);
		this.setWrongAnswers(0);
		this.setQuestionNbr(0);
	}
	
	// start from first question
	private void restartPlay() {	
		
		this.resetValues();
		this.startQuestion();
	}
	
	// start fragments for result screen
	private void startResult() {		
		
		// results only needs one type of fragment to display all content
		// so use the same for both landscape and portrait
		ResultFragment landscape_fragment = new ResultFragment();
		ResultFragment portrait_fragment = new ResultFragment();
		this.callFragments(landscape_fragment, portrait_fragment);
	}

	// start fragments for question screen
	private void startQuestion() {
		
		// landscape has smaller but wider buttons than portrait
		PlayLandscapeFragment landscape_fragment = new PlayLandscapeFragment();
		PlayPortraitFragment portrait_fragment = new PlayPortraitFragment();
		this.callFragments(landscape_fragment, portrait_fragment);
	}
	
	// common code for fragments
	private void callFragments(Fragment landscape_fragment, Fragment portrait_fragment) {
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = 
				fragmentManager.beginTransaction();  
		//---get the current display info---
		WindowManager wm = getWindowManager(); 
		Display d = wm.getDefaultDisplay();
		
		// the displays method to get size is deprecated, so use Point instead
		Point size = new Point();
		d.getSize(size);
		int width = size.x;
		int height = size.y;
		
		// width is greater then height which means landscape mode
		if (width > height)
		{
			//---landscape mode---			            
			// android.R.id.content refers to the content 
			// view of the activity        
			fragmentTransaction.replace(
					android.R.id.content, landscape_fragment);
		}
		else
		{
			//---portrait mode---			
			fragmentTransaction.replace(
					android.R.id.content, portrait_fragment);
		}
		
		fragmentTransaction.commit();
	}
	
	public Question getQuestion(int number) {
		return this.questionList.get(number);
	}
	
	public ArrayList<Answer> getAnswers(int number) {
		return this.questionList.get(number).getAnswerList();
	}
	
	public Answer getAnswer(int number, int answer) {
		return this.questionList.get(number).getAnswerList().get(answer);
	}
	
	// read from the XML file
	public String fetchQuestions() throws IOException {
				
		Resources res = getResources();
		InputStream is = res.openRawResource(R.raw.questions);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	    int i;
		try {
			i = is.read();
		    while (i != -1) {
		       byteArrayOutputStream.write(i);
		       i = is.read();
		    }
		    is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		String content = byteArrayOutputStream.toString();		     
		return content;
	}	
	
	// check the field correct for a question
	private int getCorrect(int number) {
		
		int correct = this.getQuestion(this.getQuestionNbr()).getAnswer(number).getCorrect();
		return correct;
	}
	
	private void increseRightAnswer() {
		
		this.setRightAnswers((this.getRightAnswers() + 1));
	}
	
	private void increseWrongAnswer() {
		
		this.setWrongAnswers((this.getWrongAnswers() + 1));
	}
	
	// check if answer is correct or not and add score
	private void addPoints(int number) {
		
		int correct = this.getCorrect(number);
		if(correct == 1) {
			this.increseRightAnswer();
		}
		else {
			this.increseWrongAnswer();
		}
	}
	
	private void next(int increase) {
		
		this.setQuestionNbr((this.getQuestionNbr() + increase));
		
		// if question number is less then total numbers then display next question
		if(this.getQuestionNbr() < (this.questionList.size()))
		{					
			this.startQuestion();
		}
		// else display results
		else
		{
			this.startResult();
		}
	}
	
	// click on answer 1
	public void onClick1(View view) {
		
		// check if answer is correct or not and add score
		this.addPoints(0);
		this.next(1);
	}
	
	// click on answer 2
	public void onClick2(View view) {
		
		this.addPoints(1);
		this.next(1);
	}
	
	// click on answer 3
	public void onClick3(View view) {
		
		this.addPoints(2);
		this.next(1);
	}
	
	private void parseXML() {
        
        try {
 
            /** Handling XML */
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
 
            QuestionXMLHandler myXMLHandler = new QuestionXMLHandler();
            xr.setContentHandler(myXMLHandler);
            InputSource inStream = new InputSource();
            
            String question_str = "";
			try {
				// fetch questions from XML file
				question_str = this.fetchQuestions();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
            inStream.setCharacterStream(new StringReader(question_str));
            // extract values from the XML tags
            xr.parse(inStream);
            // set list to this activitys list
            this.setQuestionList(myXMLHandler.getQuestionsList());
        }
        catch (Exception e) {
            Log.w("AndroidParseXMLActivity",e );
        }        
    }	

	public int getQuestionNbr() {
		return question_nbr;
	}

	public void setQuestionNbr(int question_nbr) {
		this.question_nbr = question_nbr;
	}

	public ArrayList<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(ArrayList<Question> questionList) {
		this.questionList = questionList;
	}

	public int getRightAnswers() {
		return right_answers;
	}

	public void setRightAnswers(int right_answers) {
		this.right_answers = right_answers;
	}

	public int getWrongAnswers() {
		return wrong_answers;
	}

	public void setWrongAnswers(int wrong_answers) {
		this.wrong_answers = wrong_answers;
	}
}
