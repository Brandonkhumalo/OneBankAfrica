package com.techmania.onebankafrica.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techmania.onebankafrica.Models.Transactions;
import com.techmania.onebankafrica.R;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.cardViewHolder> {

    ArrayList<Transactions> arrayList;
    Context context;

    public TransactionAdapter (ArrayList<Transactions> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public cardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_design, parent, false);
        return new cardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cardViewHolder holder, int position) {
        Transactions model = arrayList.get(position);
        holder.Money.setText(model.getTransactionAmount());
        holder.status.setText(model.getTransactionStatus());
        holder.date.setText(model.getTransactionDate());
        holder.type.setText(model.getTransactionType());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class cardViewHolder extends RecyclerView.ViewHolder {
        TextView Money;
        TextView status;
        TextView date;
        TextView type;
        public cardViewHolder(@NonNull View itemView) {
            super(itemView);
            Money = itemView.findViewById(R.id.recipientName);
            status = itemView.findViewById(R.id.TransactionStatus);
            date = itemView.findViewById(R.id.recipientDate);
            type = itemView.findViewById(R.id.transactionName);
        }
    }
}
