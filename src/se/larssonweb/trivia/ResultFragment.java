package se.larssonweb.trivia;

import se.larssonweb.trivia.R;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultFragment extends Fragment {
	
    @Override
    public View onCreateView(LayoutInflater inflater, 
    						 ViewGroup container, Bundle savedInstanceState) {
    	
		ActionBar ab = getActivity().getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
        //---Inflate the layout for this fragment---
        return inflater.inflate(
            R.layout.result, container, false);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        PlayActivity play = ((PlayActivity) getActivity());
        
        TextView ra = (TextView)
            getActivity().findViewById(R.id.right_answers);
        ra.setText(Integer.toString(play.getRightAnswers()));
        
        TextView wa = (TextView)
                getActivity().findViewById(R.id.wrong_answers);
        wa.setText(Integer.toString(play.getWrongAnswers()));
    }

}