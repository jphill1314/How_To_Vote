package com.example.jphil.how_to_vote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

public class QuizResultsActivity extends AppCompatActivity {

    private int[] csAns, ceAns, cfAns;
    private int[] tsAns, teAns, tfAns;
    private int[] ssAns, seAns, sfAns;
    private int[] jsAns, jeAns, jfAns;

    private int[] userSocial, userEcon, userForeign;

    public final static String SOCIAL_KEY = "social";
    public final static String ECON_KEY = "econ";
    public final static String FOREIGN_KEY = "foreign";

    public static final String SHARED_PREFS = "quiz";
    public static final String FIRST = "first";
    public static final String SECOND = "second";
    public static final String THIRD = "third";
    public static final String FOUR = "four";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("Results");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        userSocial = i.getIntArrayExtra(SOCIAL_KEY);
        userForeign = i.getIntArrayExtra(FOREIGN_KEY);
        userEcon = i.getIntArrayExtra(ECON_KEY);

        initAnswerArrays();
        calculateResults();

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
        QuizResultsAdapter adapter = new QuizResultsAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    private void initAnswerArrays(){
        csAns = getResources().getIntArray(R.array.clinton_social_answers);
        ceAns = getResources().getIntArray(R.array.clinton_economic_answers);
        cfAns = getResources().getIntArray(R.array.clinton_foreign_answers);

        tsAns = getResources().getIntArray(R.array.trump_social_answers);
        teAns = getResources().getIntArray(R.array.trump_economic_answers);
        tfAns = getResources().getIntArray(R.array.trump_foreign_answers);

        ssAns = getResources().getIntArray(R.array.stein_social_answers);
        seAns = getResources().getIntArray(R.array.stein_economic_answers);
        sfAns = getResources().getIntArray(R.array.stein_foreign_answers);

        jsAns = getResources().getIntArray(R.array.johnson_social_answers);
        jeAns = getResources().getIntArray(R.array.johnson_economic_answers);
        jfAns = getResources().getIntArray(R.array.johnson_foreign_answers);
    }

    private void calculateResults(){
        double[] vClinton, vTrump, vStein, vJohnson;
        double maxDiff = 30.9;

        vClinton = new double[4];
        vTrump = new double[4];
        vStein = new double[4];
        vJohnson = new double[4];

        vClinton[0] = calculateDistance(csAns, userSocial);
        vClinton[1] = calculateDistance(ceAns, userEcon);
        vClinton[2] = calculateDistance(cfAns, userForeign);
        vClinton[3] = ((vClinton[0] + vClinton[1] + vClinton[2]));

        if(vClinton[3] == 0){
            vClinton[3] = 100;
        }
        else{
            vClinton[3] = 100 - ((vClinton[3] / maxDiff) * 100);
        }

        vTrump[0] = calculateDistance(tsAns, userSocial);
        vTrump[1] = calculateDistance(teAns, userEcon);
        vTrump[2] = calculateDistance(tfAns, userForeign);
        vTrump[3] = ((vTrump[0] + vTrump[1] + vTrump[2]));

        if(vTrump[3] == 0){
            vTrump[3] = 100;
        }
        else{
            vTrump[3] = 100 -((vTrump[3] / maxDiff) * 100);
        }

        vStein[0] = calculateDistance(ssAns, userSocial);
        vStein[1] = calculateDistance(seAns, userEcon);
        vStein[2] = calculateDistance(sfAns, userForeign);
        vStein[3] = ((vStein[0] + vStein[1] + vStein[2]));

        if(vStein[3] == 0){
            vStein[3] = 100;
        }
        else{
            vStein[3] = 100 - ((vStein[3] / maxDiff) * 100);
        }

        vJohnson[0] = calculateDistance(jsAns, userSocial);
        vJohnson[1] = calculateDistance(jeAns, userEcon);
        vJohnson[2] = calculateDistance(jfAns, userForeign);
        vJohnson[3] = ((vJohnson[0] + vJohnson[1] + vJohnson[2]));

        if(vJohnson[3] == 0){
            vJohnson[3] = 100;
        }
        else{
            vJohnson[3] = 100 - ((vJohnson[3] / maxDiff) * 100);
        }

        String[][] results = new String[4][2];

        results[0][0] = (int)vClinton[3] + "";
        results[0][1] = "Hillary Clinton";

        results[1][0] = (int)vTrump[3] + "";
        results[1][1] = "Donald Trump";

        results[2][0] = (int)vStein[3] + "";
        results[2][1] = "Jill Stein";

        results[3][0] = (int)vJohnson[3] + "";
        results[3][1] = "Gary Johnson";

        results = sortResults(results);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, 0);
        prefs.edit().putString(FIRST, results[0][0] + "|" + results[0][1]).commit();
        prefs.edit().putString(SECOND, results[1][0] + "|" + results[1][1]).commit();
        prefs.edit().putString(THIRD, results[2][0] + "|" + results[2][1]).commit();
        prefs.edit().putString(FOUR, results[3][0] + "|" + results[3][1]).commit();
    }

    private double calculateDistance(int[] a1, int[] a2){
        double distance = 0;

        if(a1.length == a2.length) {
            for (int x = 0; x < a1.length; x++){
                distance += Math.pow((a1[x] - a2[x]), 2);
            }
            distance = Math.sqrt(distance);
        }

        return distance;
    }

    private String[][] sortResults(String[][] unsorted){
        String[][] sorted = new String[unsorted.length][unsorted[0].length];

        for(int x = 0; x < unsorted.length; x++){
            int max = -1;
            int maxI = -1;
            String can = "";
            for(int y = 0; y < unsorted.length; y++){
                if(Integer.parseInt(unsorted[y][0]) > max){
                    max = Integer.parseInt(unsorted[y][0]);
                    can = unsorted[y][1];
                    maxI = y;
                }
            }

            unsorted[maxI][0] = "-1";
            sorted[x][0] = max + "%";
            sorted[x][1] = can;
        }

        return sorted;
    }

}
