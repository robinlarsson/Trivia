package se.larssonweb.trivia;

import se.larssonweb.trivia.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

/**
 * 
 * @author Robin Larsson
 * @date Feb 4, 2013
 *
 */
public class TriviaActivity extends Activity {
	
	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivia_activity);
	}
	
	/**
	 * Runs when user clicks in new button of first page
	 * 
	 * @param view
	 */
	public void onClickPlay(View view) {
    	Intent i = new Intent("se.larssonweb.triviaPlayActivity");
    	
    	Bundle extras = new Bundle();
    	extras.putBoolean("continue", false);
    	//---attach the Bundle object to the Intent object---
    	i.putExtras(extras);  
    	
    	startActivity(i);
    }
	
	/**
	 * Runs when user clicks in continue button of first page
	 * 
	 * @param view
	 */
	public void onClickContinue(View view) {
    	Intent i = new Intent("se.larssonweb.triviaPlayActivity");
    	
    	Bundle extras = new Bundle();
    	extras.putBoolean("continue", true);
    	//---attach the Bundle object to the Intent object---
    	i.putExtras(extras);    
    	
    	startActivity(i);
    }

	/**
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trivia_activity, menu);
		return true;
	}

}
