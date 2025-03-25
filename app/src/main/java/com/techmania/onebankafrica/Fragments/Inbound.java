package com.techmania.onebankafrica.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techmania.onebankafrica.Adapters.TransactionAdapter;
import com.techmania.onebankafrica.Models.Transactions;
import com.techmania.onebankafrica.R;

import java.util.ArrayList;

public class Inbound extends Fragment {
    public static Inbound newInstance() {
        return new Inbound();
    }

    private RecyclerView recyclerView;
    private ArrayList<Transactions> arrayList;
    TransactionAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    ImageView inboundImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbound, container, false);

        inboundImage = view.findViewById(R.id.inboundImage);
        inboundImage.setVisibility(View.INVISIBLE);

        recyclerView = view.findViewById(R.id.FragmentRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayList = new ArrayList<>();
        /**arrayList.add(new Transactions("Cash-out","16-02-2025","R500","Outbound"));
        arrayList.add(new Transactions("Swipped","14-02-2025","R3000","Outbound"));
        arrayList.add(new Transactions("Bank Transfer","18-01-2025","R800","Outbound"));
        arrayList.add(new Transactions("Cash-out","10-01-2025","R2000","Outbound"));*/

        adapter = new TransactionAdapter(arrayList, requireContext());
        recyclerView.setAdapter(adapter);

        getOutboundTransactions();

        return view;
    }

    public void getOutboundTransactions(){
         if (user != null){
             String userId = user.getUid();
             DatabaseReference databaseReference = database.getReference("Users").child(userId)
             .child("Transactions").child("OutboundTransactions");

             databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

            if (snapshot.exists()){
                for (DataSnapshot transactionSnapshot : snapshot.getChildren()){
                Transactions transactions = transactionSnapshot.getValue(Transactions.class);

                if (transactions != null){
                    arrayList.add(transactions);
                }
            }
            adapter.notifyDataSetChanged();
            } else {
                inboundImage.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
        });
         }
     }
}















