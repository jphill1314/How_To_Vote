package com.example.jphil.how_to_vote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jphil on 6/30/2016.
 */
public class QuizResultsAdapter extends RecyclerView.Adapter<QuizResultsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView candidate, party, match;
        public ImageView icon;
        public View card;

        public ViewHolder(View view){
            super(view);

            candidate = (TextView) view.findViewById(R.id.candidate_name);
            party = (TextView) view.findViewById(R.id.candidate_party);
            match = (TextView) view.findViewById(R.id.percent_match);
            icon = (ImageView) view.findViewById(R.id.candidate_icon);
            card = view;
        }
    }

    String[][] results;
    Context context;

    public QuizResultsAdapter(Context context){
        String one, two, three, four;
        SharedPreferences prefs = context.getSharedPreferences(QuizResultsActivity.SHARED_PREFS, 0);
        one = prefs.getString(QuizResultsActivity.FIRST, "Error");
        two = prefs.getString(QuizResultsActivity.SECOND, "Error");
        three = prefs.getString(QuizResultsActivity.THIRD, "Error");
        four = prefs.getString(QuizResultsActivity.FOUR, "Error");

        results = new String[4][2];
        results[0] = getData(one);
        results[1] = getData(two);
        results[2] = getData(three);
        results[3] = getData(four);

        this.context = context;
    }

    private String[] getData(String data){
        String[] result = new String[2];
        int index = data.indexOf("|");

        result[0] = data.substring(0, index);
        result[1] = data.substring(index+1);

        return result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final QuizResultsAdapter.ViewHolder holder, int position) {
        holder.candidate.setText(results[position][1]);
        holder.match.setText(results[position][0]);

        String p = "";
        int image = 0;

        switch (results[position][1]){
            case "Hillary Clinton":
                p = "Democrat";
                image = R.drawable.hillary_icon;
                break;
            case "Donald Trump":
                p = "Republican";
                image = R.drawable.trump_icon;
                break;
            case "Jill Stein":
                p = "Green";
                image = R.drawable.stein_icon;
                break;
            case "Gary Johnson":
                p = "Libertarian";
                image = R.drawable.johnson_icon;
                break;
        }

        holder.party.setText(p);
        holder.icon.setImageResource(image);

        holder.card.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(context, CompareResultsActivity.class);
                i.putExtra(CompareResultsActivity.INTENT_INFO, holder.candidate.getText());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.length;
    }
}
