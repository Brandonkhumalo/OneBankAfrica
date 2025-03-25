package com.techmania.onebankafrica.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techmania.onebankafrica.Activities.ExtraActivities.Send_Money_Activity;
import com.techmania.onebankafrica.R;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.techmania.onebankafrica.Models.UserRecipients;

import java.util.ArrayList;

public class RecipientsAdapter extends RecyclerView.Adapter<RecipientsAdapter.RecipientsViewHolder> {
    private ArrayList<UserRecipients> arrayList;
    private Context context;

    public RecipientsAdapter(ArrayList<UserRecipients> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.recipient_design, parent, false);
        return new RecipientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipientsViewHolder holder, final int position) {
        UserRecipients userRecipients1 = arrayList.get(position);
        holder.recipientName.setText(userRecipients1.getRecipientName());
        holder.recipientBank.setText(userRecipients1.getRecipientBank());
        String recName = userRecipients1.getRecipientName();
        String recBank = userRecipients1.getRecipientBank();
        String recAccNum = userRecipients1.getRecipientAccountNumber();
        String recAccType = userRecipients1.getRecipientAccountType();

        holder.cardViewDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Send_Money_Activity.class);
                intent.putExtra("recipientName", recName);
                intent.putExtra("recipientBank", recBank);
                intent.putExtra("recipientAccountNumber", recAccNum);
                intent.putExtra("recipientAccountType", recAccType);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecipientsViewHolder extends RecyclerView.ViewHolder {
        private TextView recipientName;
        private TextView recipientBank;
        private CardView cardViewDesign;

        public RecipientsViewHolder(@NonNull View itemView) {
            super(itemView);
            recipientName = itemView.findViewById(R.id.recipientName);
            recipientBank = itemView.findViewById(R.id.recipientBank);
            cardViewDesign = itemView.findViewById(R.id.cardViewDesign);
        }
    }
}








