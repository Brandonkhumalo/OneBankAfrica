package com.techmania.onebankafrica.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.techmania.onebankafrica.MainActivity;
import com.techmania.onebankafrica.Models.User;
import com.techmania.onebankafrica.Models.UserBank;
import com.techmania.onebankafrica.R;

import java.util.Random;

public class Signup extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("Users");
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    String bankBalance;
    String currentBalance;
    String accountNumber;
    Random random = new Random();

    TextInputEditText EmailSignup, PasswordSignup;
    Button ButtonSignup;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EmailSignup = findViewById(R.id.emailSignup);
        PasswordSignup = findViewById(R.id.passwordSignup);
        ButtonSignup = findViewById(R.id.buttonSignup);
        progressBar = findViewById(R.id.progressBarSignup);
        progressBar.setVisibility(View.INVISIBLE);

        ButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpUser();
                Intent intent = getIntent();
                if (intent != null && Intent.ACTION_SEND.equals(intent.getAction())) {
                    if (intent.getType() != null && intent.getType().startsWith("image/")) {
                        handleFile(intent);
                    }
                }
            }
        });
    }

    private void handleFile(Intent intent) {
        Uri fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (fileUri != null) {
            if(auth.getCurrentUser() != null) {
                String userId = auth.getCurrentUser().getUid();
                StorageReference FileReference = storageReference.child("files").child(userId);
                FileReference.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(this, "File sent successfully", Toast.LENGTH_SHORT).show();
                });
            }
        } else {
            Toast.makeText(this, "No file received", Toast.LENGTH_SHORT).show();
        }
    }

    public void SignUpUser(){
        String userEmail = EmailSignup.getText().toString().trim();
        String userPass = PasswordSignup.getText().toString().trim();

        int number = random.nextInt(9000000) + 1000000;
        accountNumber = String.valueOf(number);
        int number2 = random.nextInt(20000);
        bankBalance = String.valueOf(number2);
        int number3 = number2 - 7;
        currentBalance = String.valueOf(number3);

        progressBar.setVisibility(View.VISIBLE);
        ButtonSignup.setEnabled(false);

        if(userEmail.isEmpty() && userPass.isEmpty()){
           Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
           progressBar.setVisibility(View.INVISIBLE);
           ButtonSignup.setEnabled(true);
           return;
        }
        if (userPass.length() < 8){
            Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            ButtonSignup.setEnabled(true);
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                String userId = auth.getCurrentUser().getUid();
                String userName = getIntent().getStringExtra("Name");
                String userSurname = getIntent().getStringExtra("Surname");
                String userNumber = getIntent().getStringExtra("MobileNumber");
                String userIDNumber = getIntent().getStringExtra("IDNumber");

                User user = new User(userId, userName, userSurname, userEmail, userNumber, userIDNumber);
                UserBank userBank = new UserBank(accountNumber, bankBalance, currentBalance);
                databaseReference.child(userId).setValue(user).addOnCompleteListener(v -> {
                    if(v.isSuccessful()){
                        databaseReference.child(userId).child("Bank").setValue(userBank).addOnCompleteListener(b -> {
                            if (b.isSuccessful()){
                                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Signup.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                                progressBar.setVisibility(View.INVISIBLE);
                                ButtonSignup.setEnabled(true);
                            }
                        });
                    }else{
                        Toast.makeText(this, "Database error: " + v.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Signing up failed. Please try again later" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                ButtonSignup.setEnabled(true);
            }
        });
    }
}












