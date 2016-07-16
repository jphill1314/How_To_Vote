package com.example.jphil.how_to_vote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class CompareResultsActivity extends AppCompatActivity {

    Toolbar tb;
    TextView tvSocial, tvEcon, tvForeign;

    public static final String INTENT_INFO = "candidate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_results);

        tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitle(getResources().getString(R.string.compare_activity));
        if(tb != null){
            setSupportActionBar(tb);

            if(getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        ((TextView) findViewById(R.id.social_issues_label)).setText("Social Policy");
        ((TextView) findViewById(R.id.foreign_issues_label)).setText("Foreign Policy");
        ((TextView) findViewById(R.id.economic_issues_label)).setText("Economic Policy");

        showResults();
    }

    private void showResults(){}
}
