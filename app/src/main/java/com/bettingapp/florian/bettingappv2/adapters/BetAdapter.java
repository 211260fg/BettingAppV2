package com.bettingapp.florian.bettingappv2.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bettingapp.florian.bettingappv2.R;
import com.bettingapp.florian.bettingappv2.model.Bet;

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
        TextView betOptionA;
        TextView betOptionB;

        BetViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.betCardView);
            betTitle = (TextView) itemView.findViewById(R.id.betTitle);
            betOptionA = (TextView) itemView.findViewById(R.id.betOptionA);
            betOptionB = (TextView) itemView.findViewById(R.id.betOptionB);
        }

    }

}
