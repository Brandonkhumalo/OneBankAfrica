package com.techmania.onebankafrica.Helpers;

import android.content.Context;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techmania.onebankafrica.Models.UserBank;

public class BankHelper {
    private FirebaseUser user;
    private FirebaseDatabase database;

    public BankHelper() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
    }

    public void GetBankDetails(TextView accountNumberView, TextView balanceView) {
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference databaseReference = database.getReference("Users").child(userId).child("Bank");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserBank userBank = snapshot.getValue(UserBank.class);
                    if (userBank != null) {
                        accountNumberView.setText("My account " + userBank.getaccountNumber());
                        Double BankBalance = Double.parseDouble(userBank.getcurrentBalance());
                        balanceView.setText("R" + BankBalance);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}
