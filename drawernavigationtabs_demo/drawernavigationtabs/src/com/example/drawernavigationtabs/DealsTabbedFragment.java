package com.example.drawernavigationtabs;
 
import com.example.drawernavigationtabs.GamesFragment;
import com.example.drawernavigationtabs.TopRatedFragment;

import java.util.Locale;

import com.example.drawernavigationtabs.R;
 
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.ActionBarDrawerToggle;

public class DealsTabbedFragment extends Fragment {
 
    public static final String TAG = DealsTabbedFragment.class.getSimpleName();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private TextView search;
    public DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	public ActionBarDrawerToggle mDrawerToggle;
	public String mTitle="Deals";
    public static DealsTabbedFragment newInstance() {
        return new DealsTabbedFragment();
    }
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setHasOptionsMenu(true);
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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.deals_menu, menu);
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabbed, container, false);
        
        
        LinearLayout searchLayout = (LinearLayout) v.findViewById(R.id.searchLayout);
		searchLayout.setVisibility(View.VISIBLE);
		search=(TextView) v.findViewById(R.id.search);
		search.setVisibility(View.VISIBLE);
		Drawable back = getResources().getDrawable( R.drawable.actionbar_dealspage_background_top );
		getActivity().getActionBar().setBackgroundDrawable(back);
		 getActivity().getActionBar().setTitle("Dining");
		PagerTitleStrip PagerTitle_strip = (PagerTitleStrip) v.findViewById(R.id.pager_title_strip);
		PagerTitle_strip.setVisibility(View.GONE);
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				System.out.println("roopgundeep click: news More:");
				getActivity().getSupportFragmentManager()
	            .beginTransaction()
	            .replace(R.id.content_frame, FoodList.newInstance(), FoodList.TAG).commit();
			}
		});
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());
         
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
       
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
    			return new Dealsfeed();
    		case 1:
    			// Games fragment activity
    			return new MainPagefeed();
    		case 2:
    			// Movies fragment activity
    			return new NewsfeedHome();
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
   
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }*/
    /*
     * When using the ActionBarDrawerToggle, you must call it during onPostCreate()
     * and onConfigurationChanged()
     */
    
}