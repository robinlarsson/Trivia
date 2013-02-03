package com.umu.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PlayPortraitFragment extends Fragment {
	
	public Question question = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, 
    						 ViewGroup container, Bundle savedInstanceState) {
    	
    	Play play = ((Play) getActivity());
    	// set the current Question object
    	this.question = play.getQuestion(play.getQuestionNbr());
        //---Inflate the layout for this fragment---
        return inflater.inflate(
            R.layout.play_portrait, container, false);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        TextView question = (TextView)
            getActivity().findViewById(R.id.question);
        // get the text for the question
        question.setText(this.question.getQuestion());
        
        //---Button view---
        Button btnAnswer1 = (Button) 
            getActivity().findViewById(R.id.btnAnswer1);
        // get the text for the first answer
        btnAnswer1.setText(this.question.getAnswer(0).getAnswer());
        
        //---Button view---
        Button btnAnswer2 = (Button) 
            getActivity().findViewById(R.id.btnAnswer2);
        btnAnswer2.setText(this.question.getAnswer(1).getAnswer());
        
        //---Button view---
        Button btnAnswer3 = (Button) 
            getActivity().findViewById(R.id.btnAnswer3);
        btnAnswer3.setText(this.question.getAnswer(2).getAnswer());           
    }

}