package com.example.jphil.how_to_vote;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jphil on 6/28/2016.
 */
public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder>{

    private String[] candidates, parties;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView candidate, party;
        public ImageView icon;
        public View card;

        public ViewHolder(View view) {
            super(view);

            candidate = (TextView) view.findViewById(R.id.candidate_name);
            party = (TextView) view.findViewById(R.id.candidate_party);
            icon = (ImageView) view.findViewById(R.id.candidate_icon);
            card = view;
        }
    }

    public CandidateAdapter(Context context){
        candidates = context.getResources().getStringArray(R.array.candidate_names);
        parties = context.getResources().getStringArray(R.array.candidate_parties);
        this.context = context;
    }

    @Override
    public CandidateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CandidateAdapter.ViewHolder holder, int position) {
        holder.candidate.setText(candidates[position]);
        holder.party.setText(parties[position]);

        int image = 0;

        switch (position){
            case 0:
                image = R.drawable.hillary_icon;
                break;
            case 1:
                image = R.drawable.trump_icon;
                break;
            case 2:
                image = R.drawable.stein_icon;
                break;
            case 3:
                image = R.drawable.johnson_icon;
                break;
        }

        holder.icon.setImageResource(image);

        holder.card.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CandidateActivity.class);
                intent.putExtra(CandidateActivity.CANDIDATE, holder.candidate.getText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return candidates.length;
    }
}
