package com.techmania.onebankafrica.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techmania.onebankafrica.Activities.ExtraActivities.AddRecipientActivity;
import com.techmania.onebankafrica.Adapters.RecipientsAdapter;
import com.techmania.onebankafrica.Models.HomeActivityModel;
import com.techmania.onebankafrica.Models.UserRecipients;
import com.techmania.onebankafrica.R;

import java.util.ArrayList;

public class RecipientsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<UserRecipients> userRecipients;
    private RecipientsAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    CardView addRecipient;
    ImageView noRecepients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipients);

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        noRecepients = findViewById(R.id.noRecepients);
        noRecepients.setVisibility(View.INVISIBLE);

        recyclerView = findViewById(R.id.recipientCardView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        userRecipients = new ArrayList<>();
        adapter = new RecipientsAdapter(userRecipients, this);
        recyclerView.setAdapter(adapter);

        addRecipient = findViewById(R.id.addRecipientCard);

        getRecipients();

        addRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipientsActivity.this, AddRecipientActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getRecipients(){
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference databaseReference = database.getReference("Users").child(userId).child("Recipients");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userRecipients.clear();

                    if (snapshot.exists()) {
                        for (DataSnapshot recipientSnapshot : snapshot.getChildren()) {
                            UserRecipients recipient = recipientSnapshot.getValue(UserRecipients.class);

                            if (recipient != null) {
                                userRecipients.add(new UserRecipients(
                                        recipient.getRecipientName(),
                                        recipient.getRecipientBank(),
                                        recipient.getRecipientAccountNumber(),
                                        recipient.getRecipientAccountType()
                                ));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        noRecepients.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Database error: " + error.getMessage());
                }
            });
        }
    }
}










