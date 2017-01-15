package com.commlinkinfotech.news1971.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commlinkinfotech.news1971.R;
import com.commlinkinfotech.news1971.adapters.ViewPagerAdapter;
import com.commlinkinfotech.news1971.interfaces.FragmentUtility;
import com.commlinkinfotech.news1971.interfaces.OnFragmentInteractionListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements FragmentUtility {

    private static final String ARG_TITLE = "title";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;

    private String mTitle;


    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Title of Fragment.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String title) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        viewPager = (ViewPager) getView().findViewById(R.id.viewpager);
        tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        this.setUpViewPagerAndTabLayout();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            if (getArguments() != null) {
                mTitle = getArguments().getString(ARG_TITLE);
            }
            mListener.onFragmentInteraction(HomeFragment.class.getSimpleName(), mTitle);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public String getTitle() {
        return getArguments().getString(ARG_TITLE, "");
    }

    private void setUpViewPagerAndTabLayout() {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        // add fragments to adapter here
        adapter.addFragment(TopNewsFragment.newInstance("শীর্ষ  সংবাদ"));
        adapter.addFragment(AboutFragment.newInstance("সর্বশেষ"));
        adapter.addFragment(AboutFragment.newInstance("জনপ্রিয়"));
        adapter.addFragment(AboutFragment.newInstance("খোলামত"));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
