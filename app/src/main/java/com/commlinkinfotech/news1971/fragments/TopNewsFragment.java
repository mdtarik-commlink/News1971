package com.commlinkinfotech.news1971.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commlinkinfotech.news1971.R;
import com.commlinkinfotech.news1971.adapters.TopNewsAdapter;
import com.commlinkinfotech.news1971.asyncTask.BackgroundTask;
import com.commlinkinfotech.news1971.asyncTask.BackgroundTaskListener;
import com.commlinkinfotech.news1971.interfaces.FragmentUtility;
import com.commlinkinfotech.news1971.interfaces.OnFragmentInteractionListener;
import com.commlinkinfotech.news1971.interfaces.ProgressLoaderListener;
import com.commlinkinfotech.news1971.model.NewsItem;
import com.commlinkinfotech.news1971.utils.Constants;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopNewsFragment extends Fragment implements FragmentUtility, BackgroundTaskListener {

    private static final String ARG_TITLE = "title";

    BackgroundTask backgroundTask = null;

    private String mTitle;
    RecyclerView top_news;
    private OnFragmentInteractionListener mListener;
    TopNewsAdapter topNewsAdapter;

    AVLoadingIndicatorView aVLoadingIndicatorView;


    public TopNewsFragment() {
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
    public static TopNewsFragment newInstance(String title) {
        TopNewsFragment fragment = new TopNewsFragment();
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
        return inflater.inflate(R.layout.fragment_top_news, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();

        aVLoadingIndicatorView = (AVLoadingIndicatorView) getView().findViewById(R.id.aVLoadingIndicatorView);

        top_news = (RecyclerView) getView().findViewById(R.id.top_news);
        top_news.setHasFixedSize(false);
        top_news.setNestedScrollingEnabled(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;
                } else
                    return 1;
            }
        });
        top_news.setLayoutManager(gridLayoutManager);
        if (this.topNewsAdapter != null) {
            top_news.setAdapter(topNewsAdapter);
            aVLoadingIndicatorView.setVisibility(View.GONE);
        } else {
            backgroundTask = new BackgroundTask(getActivity(), this, Constants.ASYNCTASK_IDENTIFIERS.GET_TOP_NEWS);
            backgroundTask.execute();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            if (getArguments() != null) {
                mTitle = getArguments().getString(ARG_TITLE);
            }
            //mListener.onFragmentInteraction(TopNewsFragment.class.getSimpleName(), mTitle);
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

    @Override
    public void onPreExecuteAsynctask(int identifier) {

        if (identifier == Constants.ASYNCTASK_IDENTIFIERS.GET_TOP_NEWS) {
            if (getActivity() instanceof ProgressLoaderListener) {
                ((ProgressLoaderListener) getActivity()).showProgressLoader();
            }
        }

    }

    @Override
    public void onPostExecuteAsynctask(int identifier, Object data) {

        if (identifier == Constants.ASYNCTASK_IDENTIFIERS.GET_TOP_NEWS) {
            if (getActivity() instanceof ProgressLoaderListener) {
                ((ProgressLoaderListener) getActivity()).hideProgressLoader();
            }
            aVLoadingIndicatorView.setVisibility(View.GONE);
            if (data != null && data instanceof ArrayList<?>) {
                topNewsAdapter = new TopNewsAdapter(getActivity(), (ArrayList<NewsItem>) data);
                top_news.setAdapter(topNewsAdapter);
            }
        }

    }
}
