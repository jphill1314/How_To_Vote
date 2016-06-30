package com.example.jphil.how_to_vote;

import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CandidateActivity extends AppCompatActivity {

    Toolbar toolbar;
    public static final String CANDIDATE = "candidate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate);

        if(getSupportActionBar() != null) {
             getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        Bundle args = new Bundle();
        args.putString(CANDIDATE, getIntent().getStringExtra(CANDIDATE));
        CandidateFragment cf = new CandidateFragment();
        cf.setArguments(args);

        getFragmentManager().beginTransaction().replace(R.id.frame_view, cf).commit();
    }

    public void setToolbar(Toolbar tb){
        setSupportActionBar(tb);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
