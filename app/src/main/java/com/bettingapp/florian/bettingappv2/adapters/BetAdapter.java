package com.bettingapp.florian.bettingappv2.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bettingapp.florian.bettingappv2.R;
import com.bettingapp.florian.bettingappv2.model.Bet;
import com.bettingapp.florian.bettingappv2.repo.Repository;

import java.util.List;

public class BetAdapter extends RecyclerView.Adapter<BetAdapter.BetViewHolder>{


    private List<Bet> bets;
    private Context context;

    private RecyclerView rv;


    //doorgeven van de fragmentInteractionListener en items
    public BetAdapter(List<Bet> bets,  Context context){

        //this.items=new ArrayList<>(items);
        this.bets=bets;
        this.context=context;
    }


    public void setBets(List<Bet> bets){
        this.bets = bets;
        notifyDataSetChanged();
    }

    @Override
    public BetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_bet, viewGroup, false);
        return new BetViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final BetViewHolder betViewHolder, final int i) {

        //pas text en omschrijving in de itemviewholder aan voor elk item
        final Bet bet = bets.get(i);

        betViewHolder.betTitle.setText(bet.getTitle());
        betViewHolder.betOptionA.setText(bet.getOptionA().getText());
        betViewHolder.betOptionB.setText(bet.getOptionB().getText());

        int optionAVotes = bet.getOptionA().getVotes();
        int optionBVotes = bet.getOptionB().getVotes();
        int totalUpvotes = optionAVotes + optionBVotes;
        Log.d("upvotes", ""+totalUpvotes);
        if(totalUpvotes!=0)
            betViewHolder.betProgress.setProgress((optionAVotes*100)/totalUpvotes);
        else
            betViewHolder.betProgress.setProgress(50);

        betViewHolder.betOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.upvoteAnswer(bet, bet.getOptionA());
            }
        });
        betViewHolder.betOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.upvoteAnswer(bet, bet.getOptionB());
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.rv = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return bets!=null?bets.size():0;
    }


    //itemviewholder: custom layout voor items
    public static class BetViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView betTitle;
        Button betOptionA;
        Button betOptionB;
        ProgressBar betProgress;

        BetViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.betCardView);
            betTitle = (TextView) itemView.findViewById(R.id.betTitle);
            betOptionA = (Button) itemView.findViewById(R.id.betOptionA);
            betOptionB = (Button) itemView.findViewById(R.id.betOptionB);
            betProgress = (ProgressBar) itemView.findViewById(R.id.betprogress);
        }

    }

}
