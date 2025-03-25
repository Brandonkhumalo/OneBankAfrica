package com.techmania.onebankafrica.Activities.ExtraActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techmania.onebankafrica.MainActivity;
import com.techmania.onebankafrica.Models.UserBank;
import com.techmania.onebankafrica.R;

public class Send_Money_Activity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    TextInputEditText recipientName;
    TextInputEditText recipientBank;
    TextInputEditText recipientAccountType;
    TextInputEditText recipientAccountNumber;
    TextInputEditText amount;
    TextInputEditText reference;
    ProgressBar progressBar;
    String recAccNum;
    String amountText;
    Button confirm;

    Double currentBalance;
    String userId;

    Double amountValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        progressBar = findViewById(R.id.progressPay);
        progressBar.setVisibility(View.INVISIBLE);

        recipientName = findViewById(R.id.recName);
        recipientBank = findViewById(R.id.recBank);
        recipientAccountType = findViewById(R.id.recAcc);
        recipientAccountNumber = findViewById(R.id.recAccNum);
        reference = findViewById(R.id.recRef);
        confirm = findViewById(R.id.confirmpayment);
        amount = findViewById(R.id.amountToSend);

        GetBankDetails();
        CheckIntent();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckFields();
            }
        });
    }
    public void CheckIntent(){
        String name = getIntent().getStringExtra("recipientName");
        String bank = getIntent().getStringExtra("recipientBank");
        String accType = getIntent().getStringExtra("recipientAccountType");
        recAccNum = getIntent().getStringExtra("recipientAccountNumber");

        if(name != null && bank != null && accType != null && recAccNum != null &&
                !name.isEmpty() && !bank.isEmpty() && !accType.isEmpty() && !recAccNum.isEmpty()){
            recipientName.setText(name);
            recipientBank.setText(bank);
            recipientAccountType.setText(accType);
            recipientAccountNumber.setText(recAccNum);
        }
    }

    public void CheckFields(){
        String recName = recipientName.getText().toString();
        String recBank = recipientBank.getText().toString();
        String recAccType = recipientAccountType.getText().toString();
        recAccNum = recipientAccountNumber.getText().toString();
        String recRef = reference.getText().toString();
        amountText = amount.getText().toString().trim();

        if(!recName.isEmpty() && !recBank.isEmpty() && !recAccType.isEmpty() && !recAccNum.isEmpty()
                && !recRef.isEmpty() && !amountText.isEmpty()){
            Transfer();
        } else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }
    public void Transfer(){
        progressBar.setVisibility(View.VISIBLE);
        amountValue = Double.parseDouble(amountText);

        if (currentBalance > 23){
            if(amountValue >= 20 && amountValue <= 5000){
                //get current balance
                double CurrentBalanceValue = Double.parseDouble(currentBalance.toString().trim());
                double newBalance = CurrentBalanceValue - amountValue - 3;
                String newBalanceString = String.valueOf(newBalance);

                //Save amount to database
                DatabaseReference databaseReference = database.getReference("Users").child(user.getUid()).child("Bank").child("currentBalance");
                databaseReference.setValue(newBalanceString);
                DatabaseReference dbref = database.getReference("Users").child(user.getUid()).child("Bank").child("bankBalance");
                dbref.setValue(newBalanceString);
                Toast.makeText(Send_Money_Activity.this,"Amount sent successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(Send_Money_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Send_Money_Activity.this,"Amount value must be between R20 and R5000", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Send_Money_Activity.this,"Insufficient funds", Toast.LENGTH_SHORT).show();
        }

    }

    public void GetBankDetails(){
        if (user != null){
            userId = user.getUid();

            DatabaseReference databaseReference = database.getReference("Users").child(userId).child("Bank");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserBank userBank = snapshot.getValue(UserBank.class);
                    if(userBank != null){
                        currentBalance = Double.parseDouble(userBank.getcurrentBalance());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}








