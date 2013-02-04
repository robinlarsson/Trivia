package se.larssonweb.trivia;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import se.larssonweb.trivia.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

/**
 * 
 * @author Robin Larsson
 * @date Feb 4, 2013
 *
 */
public class PlayActivity extends Activity {
	
	private int right_answers = 0;
	private int wrong_answers = 0;
	private int question_nbr = 0;
	
	private ArrayList<Question> questionList = new ArrayList<Question>();
	private SharedPreferences mPrefs;
	
	/**
	 * Sets app icon to home icon in action bar
	 * Get preferences if continue
	 * Calls XML parser for questions
	 * Calls display for next page
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
		// get preferences for trivia
    	this.mPrefs = getSharedPreferences("trivia", 0);
		
		//---get the Bundle object passed in---
		Bundle bundle = getIntent().getExtras();
		//Boolean continue_game = true;
		
		if(bundle != null) {
			Boolean continue_game = bundle.getBoolean("continue");
			// get the preferences from previous games
			if(continue_game) {
				this.getPref();
			}
		}		
		
		// parse the XML file that is container for the questions
		this.parseXML();
		// start the poll with same question number
		this.next(0);
	}
    
	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trivia_activity, menu);
		return true;
	}
    
	/**
	 * Calls function for menu logics
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return MenuChoice(item);
	}
	    
	/**
	 * Logics for the menu
	 * 
	 * @param item
	 * @return
	 */
    private boolean MenuChoice(MenuItem item) {
    	switch (item.getItemId()) {
    		case android.R.id.home:
    			Intent i = new Intent(this, TriviaActivity.class);
    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(i);
    			
    			return true;
    	}
		return false;
	}

	/**
	 * Get the preferences from previous game
	 */
    private void getPref() {
    	
    	this.setRightAnswers(mPrefs.getInt("right_answers", this.getRightAnswers()));
    	this.setWrongAnswers(mPrefs.getInt("wrong_answers", this.getWrongAnswers()));
    	this.setQuestionNbr(mPrefs.getInt("question_nbr", this.getQuestionNbr()));
    }
    
    /**
     * Set preferences for current score so we can start 
     * from where we was before quit current game
     */
    private void setPref() {    	
    	
    	// start editor for preferences and set latest values
    	SharedPreferences.Editor ed = this.mPrefs.edit();
        ed.putInt("right_answers", this.getRightAnswers());
        ed.putInt("wrong_answers", this.getWrongAnswers());
        ed.putInt("question_nbr", this.getQuestionNbr());
        ed.commit();
    }
    
    /**
     * 
     */
	/*@Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	
    	super.onRestoreInstanceState(savedInstanceState);
    	this.setRightAnswers(savedInstanceState.getInt("right_answers"));
    	this.setWrongAnswers(savedInstanceState.getInt("wrong_answers"));
    	this.setQuestionNbr(savedInstanceState.getInt("question_nbr"));
    }*/
    
	/**
	 * 
	 */
	/*@Override
    public void onSaveInstanceState(Bundle b) {
    	
    	b.putInt("right_answers", this.getRightAnswers());
    	b.putInt("wrong_answers", this.getWrongAnswers());
    	b.putInt("question_nbr", this.getQuestionNbr());
    	super.onSaveInstanceState(b);  	
    }*/
    
	/**
	 * 
	 */
    protected void onPause() {
    	// save preferences when activity goes in background or is recreated
        this.setPref();
    	super.onPause();
    }
    
    /**
     * 
     */
    protected void onDestroy() {
    	// save preferences when activity goes in background or is recreated
        this.setPref();
    	super.onDestroy();
    }
	
    /**
     * When start button on result screen is pressed
     * 
     * @param view
     */
	public void onClickRestart(View view) {
		
		this.restartPlay();
	}
	
	/**
	 * Reset all the values
	 */
	public void resetValues() {
		
		this.setRightAnswers(0);
		this.setWrongAnswers(0);
		this.setQuestionNbr(0);
	}
	
	/**
	 * Start from first question
	 */
	private void restartPlay() {	
		
		this.resetValues();
		this.startQuestion();
	}
	
	/**
	 * Start fragments for result screen
	 */
	private void startResult() {		
		
		// results only needs one type of fragment to display all content
		// so use the same for both landscape and portrait
		ResultFragment landscape_fragment = new ResultFragment();
		ResultFragment portrait_fragment = new ResultFragment();
		this.callFragments(landscape_fragment, portrait_fragment);
	}
	
	/**
	 * Start fragments for question screen
	 */
	private void startQuestion() {
		
		// landscape has smaller but wider buttons than portrait
		PlayLandscapeFragment landscape_fragment = new PlayLandscapeFragment();
		PlayPortraitFragment portrait_fragment = new PlayPortraitFragment();
		this.callFragments(landscape_fragment, portrait_fragment);
	}
	
	/**
	 * Common code for fragments
	 * 
	 * @param landscape_fragment
	 * @param portrait_fragment
	 */
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
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	public Question getQuestion(int number) {
		return this.questionList.get(number);
	}
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	public ArrayList<Answer> getAnswers(int number) {
		return this.questionList.get(number).getAnswerList();
	}
	
	/**
	 * 
	 * @param number
	 * @param answer
	 * @return
	 */
	public Answer getAnswer(int number, int answer) {
		return this.questionList.get(number).getAnswerList().get(answer);
	}
	
	/**
	 * Read from the XML file
	 * 
	 * @return
	 * @throws IOException
	 */
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
	
	/**
	 * Check the if answer is correct for a question
	 * 
	 * @param number
	 * @return
	 */
	private boolean getIsCorrect(int number) {
		
		boolean is_correct = this.getQuestion(this.getQuestionNbr()).getAnswer(number).getIsCorrect();
		return is_correct;
	}
	
	/**
	 * Increment number of correct answers for current score
	 */
	private void increseRightAnswer() {
		
		this.setRightAnswers((this.getRightAnswers() + 1));
	}
	
	/**
	 * Increment number of incorrect answers for current score
	 */
	private void increseWrongAnswer() {
		
		this.setWrongAnswers((this.getWrongAnswers() + 1));
	}
	
	
	/**
	 * Check if answer is correct or not and add score
	 * 
	 * @param number
	 */
	private void addPoints(int number) {
		
		boolean is_correct = this.getIsCorrect(number);
		if(is_correct == true) {
			this.increseRightAnswer();
		}
		else {
			this.increseWrongAnswer();
		}
	}
	
	/**
	 * Check if to display next question or show the results
	 * 
	 * @param increase
	 */
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
	
	/**
	 * Handler for click on answer 1 that checks 
	 * where to increment points and run next
	 * 
	 * @param view
	 */
	public void onClick1(View view) {
		
		// check if answer is correct or not and add score
		this.addPoints(0);
		this.next(1);
	}
	
	/**
	 * Handler for click on answer 2 that checks 
	 * where to increment points and run next
	 * 
	 * @param view
	 */
	public void onClick2(View view) {
		
		this.addPoints(1);
		this.next(1);
	}
	
	/**
	 * Handler for click on answer 3 that checks 
	 * where to increment points and run next
	 * 
	 * @param view
	 */
	public void onClick3(View view) {
		
		this.addPoints(2);
		this.next(1);
	}
	
	/**
	 * Parse the XML with questions and add to questionList
	 */
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

	/**
	 * 
	 * @return question_nbr for current question
	 */
	public int getQuestionNbr() {
		return question_nbr;
	}

	/**
	 * Set question_nbr for current question
	 * 
	 * @param question_nbr
	 */
	public void setQuestionNbr(int question_nbr) {
		this.question_nbr = question_nbr;
	}

	/**
	 * 
	 * @return questionList for current trivia
	 */
	public ArrayList<Question> getQuestionList() {
		return questionList;
	}

	/**
	 * Set questionList for current trivia
	 * 
	 * @param questionList
	 */
	public void setQuestionList(ArrayList<Question> questionList) {
		this.questionList = questionList;
	}

	/**
	 * 
	 * @return amount of right_answers for current score
	 */
	public int getRightAnswers() {
		return right_answers;
	}

	/**
	 * Set amount of right_answers for current score
	 * 
	 * @param right_answers 
	 */
	public void setRightAnswers(int right_answers) {
		this.right_answers = right_answers;
	}

	/**
	 * 
	 * @return amount of wrong_answers for current score
	 */
	public int getWrongAnswers() {
		return wrong_answers;
	}

	/**
	 * Set amount of wrong answers on current score
	 * 
	 * @param wrong_answers
	 */
	public void setWrongAnswers(int wrong_answers) {
		this.wrong_answers = wrong_answers;
	}
}
