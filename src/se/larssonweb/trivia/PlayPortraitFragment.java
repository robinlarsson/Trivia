package se.larssonweb.trivia;

import java.util.Random;

import se.larssonweb.trivia.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author Robin Larsson
 * @date Feb 4, 2013
 *
 */
public class PlayPortraitFragment extends Fragment {
	
	public Question question = null;
	private int[] rand = {0, 1, 2};
	
	/**
	 * 
	 */
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
    
    /**
     * 
     */
    @Override
    public void onStart() {
        super.onStart();
        
        TextView question = (TextView)
            getActivity().findViewById(R.id.question);
        // get the text for the question
        question.setText(this.question.getName());
        
        shuffleArray(this.rand);
        
        //---Button view---
        Button btnAnswer1 = (Button) 
            getActivity().findViewById(R.id.btnAnswer1);
        // get the text for the first answer
        btnAnswer1.setText(this.question.getAnswer(this.rand[0]).getName());
        
        //---Button view---
        Button btnAnswer2 = (Button) 
            getActivity().findViewById(R.id.btnAnswer2);
        btnAnswer2.setText(this.question.getAnswer(this.rand[1]).getName());
        
        //---Button view---
        Button btnAnswer3 = (Button) 
            getActivity().findViewById(R.id.btnAnswer3);
        btnAnswer3.setText(this.question.getAnswer(this.rand[2]).getName());           
    }
    
    /**
     * Implementing Fisher–Yates shuffle
     * 
     * @param ar
     */
    static void shuffleArray(int[] ar)
    {
	  Random rnd = new Random();
	  for (int i = ar.length - 1; i >= 0; i--)
	  {
	    int index = rnd.nextInt(i + 1);
	    // Simple swap
	    int a = ar[index];
	    ar[index] = ar[i];
	    ar[i] = a;
	  }
    }

}