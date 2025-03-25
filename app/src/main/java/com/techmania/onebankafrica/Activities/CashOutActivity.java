package com.techmania.onebankafrica.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.techmania.onebankafrica.Helpers.BankHelper;
import com.techmania.onebankafrica.Helpers.TransactionHelper;
import com.techmania.onebankafrica.Models.Transactions;
import com.techmania.onebankafrica.Models.UserBank;
import com.techmania.onebankafrica.R;
import com.techmania.onebankafrica.auth.CountryCodes;

import java.time.LocalDate;

public class CashOutActivity extends AppCompatActivity {
    private Spinner spinnerCountryCode;
    private TextInputEditText phoneNumberInput;
    String countryCode;
    private final String DEFAULT_COUNTRY = "🇿🇦 South Africa";
    TextInputEditText amount;
    Double amountValue;
    Button buttonCashOut;
    String userId;
    TextView accountnumber;
    TextView currentbalance;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    private TransactionHelper transactionHelper;

    private BankHelper bankHelper;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_out);

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        spinnerCountryCode = findViewById(R.id.spinnerCountryCode);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        amount = findViewById(R.id.nameRegister);

        accountnumber = findViewById(R.id.accountNumber);
        currentbalance = findViewById(R.id.currentBalance);
        buttonCashOut = findViewById(R.id.buttonCashOut);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now();
            date = String.valueOf(today);
        }

        transactionHelper = new TransactionHelper();

        bankHelper = new BankHelper();
        bankHelper.GetBankDetails(accountnumber, currentbalance);

        //PHONE NUMBER
        // Spinner Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item
                , CountryCodes.getCountryNames());
        spinnerCountryCode.setAdapter(adapter);

        int defaultPosition = CountryCodes.getCountryNames().indexOf(DEFAULT_COUNTRY);
        spinnerCountryCode.setSelection(defaultPosition);
        phoneNumberInput.setText(CountryCodes.getCode(DEFAULT_COUNTRY) + " "); // Pre-fill South Africa's code
        phoneNumberInput.setSelection(phoneNumberInput.getText().length()); // Move cursor to end


        // Handle Country Selection
        spinnerCountryCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = parent.getItemAtPosition(position).toString();
                countryCode = CountryCodes.getCode(selectedCountry);

                if (countryCode != null) {
                    phoneNumberInput.setText(countryCode + " ");
                    phoneNumberInput.setSelection(phoneNumberInput.getText().length());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        buttonCashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendAmount();
            }
        });
    }

    public void SendAmount(){
        String phoneNumber = phoneNumberInput.getText().toString().trim();
        if(phoneNumber.isEmpty()) {
            Toast.makeText(CashOutActivity.this,"Phone number is required", Toast.LENGTH_SHORT).show();
            return;
        }

        String amountText = amount.getText().toString().trim();
        if(!amountText.isEmpty()){
            amountValue = Double.parseDouble(amountText);
            if(amountValue >= 100 && amountValue <= 4000){
                if (user != null) {
                    userId = user.getUid();  // Make sure userId is initialized
                } else {
                    // Handle the case where user is not logged in
                    Toast.makeText(CashOutActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
                    return;
                }

                //send amount to recepient
                double currentBalanceValue = Double.parseDouble(currentbalance.getText().toString().replace("R", "").trim());
                double newBalance = currentBalanceValue - amountValue - 20;
                String newBalanceString = String.valueOf(newBalance);
                String newUserBalance ="R" + newBalance;
                currentbalance.setText(newUserBalance);

                DatabaseReference databaseReference = database.getReference("Users").child(userId).child("Bank").child("currentBalance");
                databaseReference.setValue(newBalanceString);
                DatabaseReference dbref = database.getReference("Users").child(userId).child("Bank").child("bankBalance");
                dbref.setValue(newBalanceString);

                Transactions transaction = new Transactions(
                        "Cash-out",
                        date,
                        amountText,
                        "Outbound"
                );
                transactionHelper.saveTransaction(transaction);

                Toast.makeText(CashOutActivity.this,"Amount sent successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                amountValue = 0.0;
                Toast.makeText(CashOutActivity.this,"Amount value must be between R100 and R4000", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CashOutActivity.this,"Amount value can't be empty", Toast.LENGTH_SHORT).show();
        }
        //send amount to recepient
    }

    /**public void GetBankDetails(){
        if (user != null){
            userId = user.getUid();

            DatabaseReference databaseReference = database.getReference("Users").child(userId).child("Bank");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserBank userBank = snapshot.getValue(UserBank.class);
                    if(userBank != null){
                        accountnumber.setText("My account " + userBank.getaccountNumber());
                        Double BankBalance = Double.parseDouble(userBank.getcurrentBalance());
                        currentbalance.setText("R" + BankBalance);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }*/

    private String getRawPhoneNumber() {
        if (phoneNumberInput != null && phoneNumberInput.getText() != null) {
            String text = phoneNumberInput.getText().toString();
            return text.replace(countryCode + " ", "").trim();
        }
        return "";
    }

    public String getFullPhoneNumber() {
        return countryCode + getRawPhoneNumber();
    }

    public void UpdateTransactions(){
        userId = user.getUid();
    }
}













