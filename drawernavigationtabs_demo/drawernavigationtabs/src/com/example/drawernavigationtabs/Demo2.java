package com.example.drawernavigationtabs;

import com.example.drawernavigationtabs.*;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

@SuppressLint("NewApi")
public class Demo2 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.demo2, container, false);
		
		return rootView;
	}
	/*@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Drawable d = getResources().getDrawable(R.drawable.food);
		getActivity().findViewById(R.id.demoPagerLayout).setBackground(d);
		super.onStart();
	}*/
/*	@Override
	public void onPause() {
		Drawable d = getResources().getDrawable(R.drawable.business);
		getActivity().findViewById(R.id.image2).setVisibility(View.GONE);
		getActivity().findViewById(R.id.image1).setVisibility(View.VISIBLE);
		// TODO Auto-generated method stub
		super.onPause();
	}*/
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		
		if (this.isVisible()) {
			
			if(getActivity().findViewById(R.id.image1).getVisibility()==View.VISIBLE){
				Animation out = AnimationUtils.loadAnimation(getActivity(),
						android.R.anim.fade_out);
				Animation out_circle = AnimationUtils.loadAnimation(getActivity(),
						android.R.anim.slide_out_right);
				getActivity().findViewById(R.id.image1).startAnimation(out);
				getActivity().findViewById(R.id.image1).setVisibility(View.INVISIBLE);
				//getActivity().findViewById(R.id.circle1).startAnimation(out_circle);
				getActivity().findViewById(R.id.circle1).setBackgroundResource(R.drawable.grey);
				getActivity().findViewById(R.id.circle1).setAlpha((float) 0.5);
			}
			if(getActivity().findViewById(R.id.image3).getVisibility()==View.VISIBLE){
				Animation out = AnimationUtils.loadAnimation(getActivity(),
						android.R.anim.fade_out);
				getActivity().findViewById(R.id.image3).startAnimation(out);
				getActivity().findViewById(R.id.image3).setVisibility(View.INVISIBLE);
				getActivity().findViewById(R.id.circle3).setBackgroundResource(R.drawable.grey);
				getActivity().findViewById(R.id.circle3).setAlpha((float) 0.5);
			}
            
			Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
			getActivity().findViewById(R.id.image2).startAnimation(in);
			getActivity().findViewById(R.id.image2).setVisibility(View.VISIBLE);
			Animation in_circle = AnimationUtils.loadAnimation(getActivity(),
					android.R.anim.slide_in_left);
			//getActivity().findViewById(R.id.circle2).startAnimation(in_circle);
			getActivity().findViewById(R.id.circle2).setBackgroundResource(R.drawable.black);
			getActivity().findViewById(R.id.circle2).setAlpha(1);			
			//Drawable d = getResources().getDrawable(R.drawable.food);
			//getActivity().findViewById(R.id.demoPagerLayout).setBackground(d);
			
	    }
	}
}
