package com.example.drawernavigationtabs;
 
import com.example.drawernavigationtabs.GamesFragment;
import com.example.drawernavigationtabs.TopRatedFragment;

import java.util.ArrayList;
import java.util.Locale;

import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.database.SqlHandler;
 
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsTabbedFragment extends Fragment {
 
    public static final String TAG = NewsTabbedFragment.class.getSimpleName();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    ArrayList<String> interestDB = new ArrayList<String>(100);
    Integer number_tabs=2;
    public static NewsTabbedFragment newInstance() {
        return new NewsTabbedFragment();
    }
     	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SqlHandler sqlHandler = new SqlHandler(getActivity());
		Cursor cursor = sqlHandler.selectQuery("SELECT * FROM INTERESTS");
		if (cursor != null && cursor.getCount() > 0) {
			number_tabs += cursor.getCount();
			if (cursor.moveToFirst()) {
				do {
					System.out.println("roop Interest DB: "
							+ cursor.getString(cursor.getColumnIndex("name")));
					interestDB.add(cursor.getString(cursor
							.getColumnIndex("name")));
				} while (cursor.moveToNext());
			}

		}
		for (int i = 0; i < interestDB.size(); i++) {
			System.out.println("Interests= "+interestDB.get(i));
		}
	}
    @Override
    public void onDetach() {
    	// TODO Auto-generated method stub
    	super.onDetach();
    	Drawable back = getResources().getDrawable( R.drawable.actionbar_homepage_background_top );
		getActivity().getActionBar().setBackgroundDrawable(back);
		 getActivity().getActionBar().setTitle("Brthe");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabbed, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());
         
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Drawable back = getResources().getDrawable( R.drawable.actionbar_newspage_background_top );
		getActivity().getActionBar().setBackgroundDrawable(back);
		 getActivity().getActionBar().setTitle("Feeds");
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
        	String tabName;
        	switch (position) {
    		case 0:    			
    			return new NewsfeedHome();
    		case 1:    			
    			return new NewsfeedGeneral();	
    		case 2:
    			tabName= interestDB.get(position-2);    			
    			if(tabName.equals("Entertainment"))
    				return new NewsfeedEntertainment();
    			else if(tabName.equals("Science"))
    				return new NewsfeedScience();
    			else if(tabName.equals("Technology"))
    				return new NewsfeedTechnology();
    			else if(tabName.equals("Sports"))
    				return new NewsfeedSports();
    			else if(tabName.equals("Business"))
    				return new NewsfeedBusiness();
    			else if(tabName.equals("Auto"))
    				return new NewsfeedAuto();
    			else if(tabName.equals("Arts and Photography"))
    				return new NewsfeedArtsAndPhoto();
    			else if(tabName.equals("Lifestyle"))
        			return new NewsfeedLifestyle();	
    		case 3:
    			tabName= interestDB.get(position-2);    			
    			if(tabName.equals("Entertainment"))
    				return new NewsfeedEntertainment();
    			else if(tabName.equals("Science"))
    				return new NewsfeedScience();
    			else if(tabName.equals("Technology"))
    				return new NewsfeedTechnology();
    			else if(tabName.equals("Sports"))
    				return new NewsfeedSports();
    			else if(tabName.equals("Business"))
    				return new NewsfeedBusiness();
    			else if(tabName.equals("Auto"))
    				return new NewsfeedAuto();
    			else if(tabName.equals("Arts and Photography"))
    				return new NewsfeedArtsAndPhoto();
    			else if(tabName.equals("Lifestyle"))
        			return new NewsfeedLifestyle();
    		case 4:
    			tabName= interestDB.get(position-2);    			
    			if(tabName.equals("Entertainment"))
    				return new NewsfeedEntertainment();
    			else if(tabName.equals("Science"))
    				return new NewsfeedScience();
    			else if(tabName.equals("Technology"))
    				return new NewsfeedTechnology();
    			else if(tabName.equals("Sports"))
    				return new NewsfeedSports();
    			else if(tabName.equals("Business"))
    				return new NewsfeedBusiness();
    			else if(tabName.equals("Auto"))
    				return new NewsfeedAuto();
    			else if(tabName.equals("Arts and Photography"))
    				return new NewsfeedArtsAndPhoto();
    			else if(tabName.equals("Lifestyle"))
        			return new NewsfeedLifestyle();
    		case 5:
    			tabName= interestDB.get(position-2);    			
    			if(tabName.equals("Entertainment"))
    				return new NewsfeedEntertainment();
    			else if(tabName.equals("Science"))
    				return new NewsfeedScience();
    			else if(tabName.equals("Technology"))
    				return new NewsfeedTechnology();
    			else if(tabName.equals("Sports"))
    				return new NewsfeedSports();
    			else if(tabName.equals("Business"))
    				return new NewsfeedBusiness();
    			else if(tabName.equals("Auto"))
    				return new NewsfeedAuto();
    			else if(tabName.equals("Arts and Photography"))
    				return new NewsfeedArtsAndPhoto();
    			else if(tabName.equals("Lifestyle"))
        			return new NewsfeedLifestyle();
    		case 6:
    			tabName= interestDB.get(position-2);    			
    			if(tabName.equals("Entertainment"))
    				return new NewsfeedEntertainment();
    			else if(tabName.equals("Science"))
    				return new NewsfeedScience();
    			else if(tabName.equals("Technology"))
    				return new NewsfeedTechnology();
    			else if(tabName.equals("Sports"))
    				return new NewsfeedSports();
    			else if(tabName.equals("Business"))
    				return new NewsfeedBusiness();
    			else if(tabName.equals("Auto"))
    				return new NewsfeedAuto();
    			else if(tabName.equals("Arts and Photography"))
    				return new NewsfeedArtsAndPhoto();
    			else if(tabName.equals("Lifestyle"))
        			return new NewsfeedLifestyle();
    		case 7:
    			tabName= interestDB.get(position-2);    			
    			if(tabName.equals("Entertainment"))
    				return new NewsfeedEntertainment();
    			else if(tabName.equals("Science"))
    				return new NewsfeedScience();
    			else if(tabName.equals("Technology"))
    				return new NewsfeedTechnology();
    			else if(tabName.equals("Sports"))
    				return new NewsfeedSports();
    			else if(tabName.equals("Business"))
    				return new NewsfeedBusiness();
    			else if(tabName.equals("Auto"))
    				return new NewsfeedAuto();
    			else if(tabName.equals("Arts and Photography"))
    				return new NewsfeedArtsAndPhoto();
    			else if(tabName.equals("Lifestyle"))
        			return new NewsfeedLifestyle();
    		case 8:
    			tabName= interestDB.get(position-2);    			
    			if(tabName.equals("Entertainment"))
    				return new NewsfeedEntertainment();
    			else if(tabName.equals("Science"))
    				return new NewsfeedScience();
    			else if(tabName.equals("Technology"))
    				return new NewsfeedTechnology();
    			else if(tabName.equals("Sports"))
    				return new NewsfeedSports();
    			else if(tabName.equals("Business"))
    				return new NewsfeedBusiness();
    			else if(tabName.equals("Auto"))
    				return new NewsfeedAuto();
    			else if(tabName.equals("Arts and Photography"))
    				return new NewsfeedArtsAndPhoto();
    			else if(tabName.equals("Lifestyle"))
        			return new NewsfeedLifestyle();
    		case 9:
    			tabName= interestDB.get(position-2);    			
    			if(tabName.equals("Entertainment"))
    				return new NewsfeedEntertainment();
    			else if(tabName.equals("Science"))
    				return new NewsfeedScience();
    			else if(tabName.equals("Technology"))
    				return new NewsfeedTechnology();
    			else if(tabName.equals("Sports"))
    				return new NewsfeedSports();
    			else if(tabName.equals("Business"))
    				return new NewsfeedBusiness();
    			else if(tabName.equals("Auto"))
    				return new NewsfeedAuto();
    			else if(tabName.equals("Arts and Photography"))
    				return new NewsfeedArtsAndPhoto();
    			else if(tabName.equals("Lifestyle"))
        			return new NewsfeedArtsAndPhoto();	
    		case 10:
    			tabName= interestDB.get(position-2);    			
    			if(tabName.equals("Entertainment"))
    				return new NewsfeedEntertainment();
    			else if(tabName.equals("Science"))
    				return new NewsfeedScience();
    			else if(tabName.equals("Technology"))
    				return new NewsfeedTechnology();
    			else if(tabName.equals("Sports"))
    				return new NewsfeedSports();
    			else if(tabName.equals("Business"))
    				return new NewsfeedBusiness();
    			else if(tabName.equals("Auto"))
    				return new NewsfeedAuto();
    			else if(tabName.equals("Arts and Photography"))
    				return new NewsfeedArtsAndPhoto();
    			else if(tabName.equals("Lifestyle"))
        			return new NewsfeedLifestyle();		
    		}
    		return null;

        }
 
        @Override
        public int getCount() {
            return number_tabs;
        }
 
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            String tabName;
            switch (position) {
            case 0:
                return getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return getString(R.string.title_section2).toUpperCase(l);    
            case 2:
            	tabName = interestDB.get(position-2);
            	return tabName.toUpperCase(l);
                //return getString(R.string.title_section2).toUpperCase(l);
            case 3:
            	tabName = interestDB.get(position-2);
            	return tabName.toUpperCase(l);
            case 4:
            	tabName = interestDB.get(position-2);
            	return tabName.toUpperCase(l);
            case 5:
            	tabName = interestDB.get(position-2);
            	return tabName.toUpperCase(l);
            case 6:
            	tabName = interestDB.get(position-2);
            	return tabName.toUpperCase(l);	
            case 7:
            	tabName = interestDB.get(position-2);
            	return tabName.toUpperCase(l);
            case 8:
            	tabName = interestDB.get(position-2);
            	return tabName.toUpperCase(l);
            case 9:
            	tabName = interestDB.get(position-2);
            	return tabName.toUpperCase(l);
            case 10:
            	tabName = interestDB.get(position-2);
            	return tabName.toUpperCase(l);
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