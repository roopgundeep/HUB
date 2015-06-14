package com.example.drawernavigationtabs;

import com.example.drawernavigationtabs.*;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class Demo1 extends Fragment {
	
	private int mShortAnimationDuration;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.demo1, container, false);
		mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
		return rootView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	/*@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
	}*/
	/*@Override
	public void onPause() {
		Drawable d = getResources().getDrawable(R.drawable.image);
		

		// TODO Auto-generated method stub
		super.onPause();
	}*/
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);

		if (this.isVisible()) {

			if (getActivity().findViewById(R.id.image2).getVisibility() == View.VISIBLE) {
				Animation out = AnimationUtils.loadAnimation(getActivity(),
						android.R.anim.fade_out);
				/*Animation out_circle = AnimationUtils.loadAnimation(getActivity(),
						android.R.anim.slide_out_right);*/
				getActivity().findViewById(R.id.image2).startAnimation(out);
				getActivity().findViewById(R.id.image2).setVisibility(
						View.INVISIBLE);
				//getActivity().findViewById(R.id.circle2).startAnimation(out_circle);
				getActivity().findViewById(R.id.circle2).setBackgroundResource(R.drawable.grey);
				getActivity().findViewById(R.id.circle2).setAlpha((float) 0.5);
			}
			Animation in = AnimationUtils.loadAnimation(getActivity(),
					android.R.anim.fade_in);
			
			getActivity().findViewById(R.id.image1).startAnimation(in);
			getActivity().findViewById(R.id.image1).setVisibility(View.VISIBLE);
			//getActivity().findViewById(R.id.circle1).startAnimation(in_circle);
			getActivity().findViewById(R.id.circle1).setBackgroundResource(R.drawable.black);
			getActivity().findViewById(R.id.circle1).setAlpha(1);
			// Drawable d = getResources().getDrawable(R.drawable.art);
			// getActivity().findViewById(R.id.demoPagerLayout).setBackground(d);
		}
	}
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}
	private void crossfade(View view,  final View view2) {

	    // Set the content view to 0% opacity but visible, so that it is visible
	    // (but fully transparent) during the animation.
	    view.setAlpha(0f);
	    view.setVisibility(View.VISIBLE);

	    // Animate the content view to 100% opacity, and clear any animation
	    // listener set on the view.
	    view.animate()
	            .alpha(1f)
	            .setDuration(mShortAnimationDuration)
	            .setListener(null);

	    // Animate the loading view to 0% opacity. After the animation ends,
	    // set its visibility to GONE as an optimization step (it won't
	    // participate in layout passes, etc.)
	    view2.animate()
	            .alpha(0f)
	            .setDuration(mShortAnimationDuration)
	            .setListener(new AnimatorListenerAdapter() {
	                @Override
	                public void onAnimationEnd(Animator animation) {
	                	 view2.setVisibility(View.GONE);
	                }
	            });
	}
}
