package se.larssonweb.trivia;

import se.larssonweb.trivia.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// extend from portrait to use the same onstart logic code
public class PlayLandscapeFragment extends PlayPortraitFragment {
	
	//public Question question = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, 
    						 ViewGroup container, Bundle savedInstanceState) {
        
    	PlayActivity play = ((PlayActivity) getActivity());
    	// set the current Question object
    	this.question = play.getQuestion(play.getQuestionNbr());
    	//---Inflate the layout for this fragment---    	
        return inflater.inflate(
            R.layout.play, container, false);
    }
    
}
