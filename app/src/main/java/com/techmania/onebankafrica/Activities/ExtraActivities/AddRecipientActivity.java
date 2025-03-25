package com.techmania.onebankafrica.Activities.ExtraActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techmania.onebankafrica.Activities.RecipientsActivity;
import com.techmania.onebankafrica.Models.UserRecipients;
import com.techmania.onebankafrica.R;

public class AddRecipientActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("Users");

    MaterialAutoCompleteTextView dropdownMenu;
    MaterialAutoCompleteTextView dropdownMenu2;
    TextInputEditText number, holder;
    Button addRecipient;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipient);

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        dropdownMenu = findViewById(R.id.dropdown_menu);
        dropdownMenu2 = findViewById(R.id.dropdown_menu2);
        addRecipient = findViewById(R.id.AddRecipient);
        number = findViewById(R.id.accNum);
        holder = findViewById(R.id.accName);
        progressBar = findViewById(R.id.progressBar);

        String[] banks = {"Capitec Bank", "Standard Bank", "FNB", "ABSA", "Nedbank"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, banks);
        dropdownMenu.setAdapter(adapter);

        String[] accountType = {"Current Account", "Savings Account", "Transmission", "Bonds"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, accountType);
        dropdownMenu2.setAdapter(adapter2);

        progressBar.setVisibility(View.INVISIBLE);

        addRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipientBank = dropdownMenu.getText().toString();
                String recipientAccountType = dropdownMenu2.getText().toString();
                String recipientAccountNumber = number.getText().toString();
                String recipientName = holder.getText().toString();

                if (!recipientBank.isEmpty() && !recipientAccountType.isEmpty() && !recipientAccountNumber.isEmpty() && !recipientName.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    addRecipient.setEnabled(false);

                    String userId = auth.getCurrentUser().getUid();
                    UserRecipients userRecipient = new UserRecipients(recipientName, recipientBank, recipientAccountNumber, recipientAccountType);

                    databaseReference.child(userId).child("Recipients").push().setValue(userRecipient).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            addRecipient.setEnabled(true);
                            finish();

                            Toast.makeText(AddRecipientActivity.this, "Recipient added successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(AddRecipientActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}












