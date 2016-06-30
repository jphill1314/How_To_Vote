package com.example.jphil.how_to_vote;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jphil on 6/28/2016.
 */
public class CandidateFragment extends android.app.Fragment {

    public CandidateFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_candidate, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        CandidateActivity ca = (CandidateActivity) getActivity();
        ca.setToolbar(toolbar);

        String candidate = "";
        Bundle bundle = this.getArguments();
        if(bundle != null){
            candidate = bundle.getString(CandidateActivity.CANDIDATE);
        }

        populateView(candidate, rootView);


        return rootView;
    }

    private void populateView(String candidate, View view){
        TextView issues = (TextView) view.findViewById(R.id.stances);
        TextView background = (TextView) view.findViewById(R.id.background);
        TextView imageLicense = (TextView) view.findViewById(R.id.image_license);
        ImageView backdrop = (ImageView) view.findViewById(R.id.backdrop);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);


        collapsingToolbar.setTitle(candidate);

        String bg = "";
        String is = "";
        String il = "";
        int image = 0;

        switch (candidate){
            case "Hillary Clinton":
                bg = getString(R.string.clinton_background);
                is = getString(R.string.clinton_beliefs);
                il = getString(R.string.clinton_image_cc);
                image = R.drawable.hillary;
                break;
            case "Donald Trump":
                bg = getString(R.string.trump_background);
                is = getString(R.string.trump_beliefs);
                il = getString(R.string.trump_image_cc);
                image = R.drawable.trump;
                break;
            case "Jill Stein":
                bg = getString(R.string.stein_background);
                is = getString(R.string.stein_beliefs);
                il = getString(R.string.stein_image_cc);
                image = R.drawable.stein;
                break;
            case "Gary Johnson":
                bg = getString(R.string.johnson_background);
                is = getString(R.string.johnson_beliefs);
                il = getString(R.string.johnson_image_cc);
                image = R.drawable.johnson;
                break;
        }


        background.setText(bg);
        issues.setText(is);
        imageLicense.setText(il);
        backdrop.setImageResource(image);
    }
}