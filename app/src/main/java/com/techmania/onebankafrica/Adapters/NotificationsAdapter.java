package com.techmania.onebankafrica.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techmania.onebankafrica.Models.NotificationsModel;
import com.techmania.onebankafrica.R;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {
    private ArrayList<NotificationsModel> arrayList;
    private Context context;

    public NotificationsAdapter(ArrayList<NotificationsModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.notification, parent, false);
        return new NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {
        NotificationsModel notificationsModel = arrayList.get(position);
        holder.header.setText(notificationsModel.getHeader());
        holder.message.setText(notificationsModel.getMessage());
        holder.date.setText(notificationsModel.getDate());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class NotificationsViewHolder extends RecyclerView.ViewHolder {
        private TextView header;
        private TextView message;
        private TextView date;

        public NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.notificationhead);
            message = itemView.findViewById(R.id.notificationmessage);
            date = itemView.findViewById(R.id.notificationdate);
        }
    }
}
