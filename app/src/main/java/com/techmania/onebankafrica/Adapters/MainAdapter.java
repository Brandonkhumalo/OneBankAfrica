package com.techmania.onebankafrica.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.techmania.onebankafrica.Activities.AccountActivity;
import com.techmania.onebankafrica.Activities.AirtimeActivity;
import com.techmania.onebankafrica.Activities.CardActivity;
import com.techmania.onebankafrica.Activities.CashOutActivity;
import com.techmania.onebankafrica.Activities.NotificationsActivity;
import com.techmania.onebankafrica.Activities.PaymentsActivity;
import com.techmania.onebankafrica.Activities.RecipientsActivity;
import com.techmania.onebankafrica.Activities.StatementsActivity;
import com.techmania.onebankafrica.Activities.TransactionsActivity;
import com.techmania.onebankafrica.Activities.VouchersActivity;
import com.techmania.onebankafrica.Models.HomeActivityModel;
import com.techmania.onebankafrica.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.cardViewHolder> {
    private ArrayList<HomeActivityModel> arrayList;
    private Context context;

    public MainAdapter(ArrayList<HomeActivityModel> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public cardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.layout_design, parent, false);
        return new cardViewHolder(view);
    }

    //SETTING THE IMAGE AND TEXT TO THE SCREEN
    @Override
    public void onBindViewHolder(@NonNull cardViewHolder holder, final int position) {
        HomeActivityModel homeModel = arrayList.get(position);
        holder.textViewSplash.setText(homeModel.getCategoryName());
        holder.imageViewSplash.setImageResource(context.getResources()
                .getIdentifier(homeModel.getImageName(), "drawable", context.getPackageName()));

       holder.cardViewSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Intent intent = new Intent(context, PaymentsActivity.class);
                    context.startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(context, CashOutActivity.class);
                    context.startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(context, CardActivity.class);
                    context.startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(context, NotificationsActivity.class);
                    context.startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(context, RecipientsActivity.class);
                    context.startActivity(intent);
                } else if (position == 5) {
                    Intent intent = new Intent(context, TransactionsActivity.class);
                    context.startActivity(intent);
                } else if (position == 6) {
                    Intent intent = new Intent(context, StatementsActivity.class);
                    context.startActivity(intent);
                } else if (position == 7) {
                    Intent intent = new Intent(context, AccountActivity.class);
                    context.startActivity(intent);
                } else if (position == 8) {
                    Intent intent = new Intent(context, VouchersActivity.class);
                    context.startActivity(intent);
                } else if (position == 9) {
                    Intent intent = new Intent(context, AirtimeActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class cardViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewSplash;
        private TextView textViewSplash;
        private CardView cardViewSplash;

        public cardViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewSplash = itemView.findViewById(R.id.imageViewSplash);
            textViewSplash = itemView.findViewById(R.id.textViewSplash);
            cardViewSplash = itemView.findViewById(R.id.cardViewDesign);
        }
    }
}









