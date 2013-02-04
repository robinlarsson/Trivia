package se.larssonweb.trivia;

import se.larssonweb.trivia.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class TriviaActivity extends Activity {
	
	/* Activity ids */
	//private static final int PLAY = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivia_activity);
	}
	
	public void onClickPlay(View view) {
    	Intent i = new Intent("se.larssonweb.triviaPlayActivity");
    	
    	Bundle extras = new Bundle();
    	extras.putBoolean("continue", false);
    	//---attach the Bundle object to the Intent object---
    	i.putExtras(extras);  
    	
    	startActivity(i);
    }
	
	public void onClickContinue(View view) {
    	Intent i = new Intent("se.larssonweb.triviaPlayActivity");
    	
    	Bundle extras = new Bundle();
    	extras.putBoolean("continue", true);
    	//---attach the Bundle object to the Intent object---
    	i.putExtras(extras);    
    	
    	startActivity(i);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trivia_activity, menu);
		return true;
	}

}
