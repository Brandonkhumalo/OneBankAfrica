package com.techmania.onebankafrica.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.techmania.onebankafrica.Activities.ExtraActivities.PdfDownloader;
import com.techmania.onebankafrica.Models.StatementsModel;
import com.techmania.onebankafrica.R;
import java.util.ArrayList;

public class StatementsAdapter extends RecyclerView.Adapter<StatementsAdapter.cardViewHolder> {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String FILE_NAME = "myfile.pdf";


    ArrayList<StatementsModel> arrayList;
    Context context;

    public StatementsAdapter(ArrayList<StatementsModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public cardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statement_design, parent, false);
        return new cardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cardViewHolder holder, final int position) {
        StatementsModel model = arrayList.get(position);
        holder.Months.setText(model.getMonth());
        holder.Dates.setText(model.getDate());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfDownloader pdfDownloader = new PdfDownloader(v.getContext());
                pdfDownloader.savePdfToStorage(R.raw.statement, "statement.pdf");

                /*if (position == 0){
                    pdfDownloader.savePdfToStorage(R.raw.statement, "statement.pdf");
                } else if (position == 1){
                    pdfDownloader.savePdfToStorage(R.raw.statement, "statement.pdf");
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class cardViewHolder extends RecyclerView.ViewHolder {
        TextView Months;
        TextView Dates;
        CardView cardView;

        public cardViewHolder(@NonNull View itemView) {
            super(itemView);
            Months = itemView.findViewById(R.id.textMonth);
            Dates = itemView.findViewById(R.id.textDates);
            cardView = itemView.findViewById(R.id.cardStatements);

        }
    }
}