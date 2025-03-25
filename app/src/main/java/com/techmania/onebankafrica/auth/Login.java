package com.techmania.onebankafrica.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.techmania.onebankafrica.MainActivity;
import com.techmania.onebankafrica.R;

public class Login extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    TextInputEditText EmailLogin, PasswordLogin;
    Button login;
    TextView register;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailLogin = findViewById(R.id.emaillogin);
        PasswordLogin = findViewById(R.id.passlogin);
        login = findViewById(R.id.buttonLogin);
        register = findViewById(R.id.loginRegister);
        progressBar = findViewById(R.id.progressLogin);
        progressBar.setVisibility(View.INVISIBLE);

        login.setOnClickListener(v -> LoginUser());

        register.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    public void LoginUser() {
        String userEmail = EmailLogin.getText().toString().trim();
        String userPass = PasswordLogin.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

        if (userEmail.isEmpty() || userPass.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            auth.signInWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(v -> {
                if (v.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);

                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "Incorrect details", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}










