package com.example.drawernavigationtabs;
 
import com.example.drawernavigationtabs.GamesFragment;
import com.example.drawernavigationtabs.TopRatedFragment;

import java.util.Locale;

import com.example.drawernavigationtabs.R;
 
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventsTabbedFragment extends Fragment {
 
    public static final String TAG = EventsTabbedFragment.class.getSimpleName();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    public static EventsTabbedFragment newInstance() {
        return new EventsTabbedFragment();
    }
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onDetach() {
    	// TODO Auto-generated method stub
    	super.onDetach();
    	Drawable back = getResources().getDrawable( R.drawable.actionbar_homepage_background_top );
		getActivity().getActionBar().setBackgroundDrawable(back);
		 getActivity().getActionBar().setTitle("Hub");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabbed, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());
        Drawable back = getResources().getDrawable( R.drawable.actionbar_eventspage_background_top );
		getActivity().getActionBar().setBackgroundDrawable(back); 
		 getActivity().getActionBar().setTitle("Events");
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        PagerTitleStrip PagerTitle_strip = (PagerTitleStrip) v.findViewById(R.id.pager_title_strip);
		PagerTitle_strip.setVisibility(View.GONE);
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                // do transformation here
            	final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setRotationY(position * -30);
                }
        });
        return v;
    }
     
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
 
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
 
        @Override
        public Fragment getItem(int position) {
        	
        	switch (position) {
    		case 0:
    			// Top Rated fragment activity    			
    			return new Eventsfeed();
    		case 1:
    			// Games fragment activity
    			return new MainPagefeed();
    		case 2:
    			// Movies fragment activity
    			return new MainPagefeed();
    		case 3:
    			// Movies fragment activity
    			return new GamesFragment();
    		case 4:
    			// Movies fragment activity
    			return new TopRatedFragment();
    		case 5:
    			// Movies fragment activity
    			return new GamesFragment();
    		}

    		return null;
//            Fragment fragment = new TabbedContentFragment();
//            Bundle args = new Bundle();
//            args.putInt(TabbedContentFragment.ARG_SECTION_NUMBER, position + 1);
//            fragment.setArguments(args);
//            return fragment;
        }
 
        @Override
        public int getCount() {
            return 1;
        }
 
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
            case 0:
                return getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return getString(R.string.title_section3).toUpperCase(l);
            case 3:
                return getString(R.string.title_section4).toUpperCase(l);
            case 4:
                return getString(R.string.title_section5).toUpperCase(l);    
            }
            return null;
        }
    }
 
    public static class TabbedContentFragment extends Fragment {
 
        public static final String ARG_SECTION_NUMBER = "section_number";
 
        public TabbedContentFragment() {
        } 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed_content,
                    container, false);
            TextView dummyTextView = (TextView) rootView
                    .findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(
                    ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
 
}