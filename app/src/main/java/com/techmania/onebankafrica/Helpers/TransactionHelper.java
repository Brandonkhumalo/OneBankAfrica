package com.techmania.onebankafrica.Helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techmania.onebankafrica.Models.Transactions;

public class TransactionHelper {
    private FirebaseUser user;
    private FirebaseDatabase database;

    public TransactionHelper(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
    }

    public void saveTransaction(Transactions transaction) {
        String userId = user.getUid();
        DatabaseReference transactionsRef = database.getReference("Users").child(userId).child("Transactions").child("OutBound");

        // Generate a unique key for the transaction
        String transactionKey = transactionsRef.push().getKey();

        if (transactionKey != null) {
            // Save the transaction under the generated key
            transactionsRef.child(transactionKey).setValue(transaction);
        }
    }
}




