package com.techmania.onebankafrica.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.card.MaterialCardView;
import com.techmania.onebankafrica.Activities.ExtraActivities.Send_Money_Activity;
import com.techmania.onebankafrica.R;

public class PaymentsActivity extends AppCompatActivity {
    MaterialCardView cardRecipient;
    MaterialCardView cardOnceOffPay;
    MaterialCardView cardCellphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        cardRecipient = findViewById(R.id.cardRecipient);
        cardOnceOffPay = findViewById(R.id.cardOnceOffPay);
        cardCellphone = findViewById(R.id.cardCellphone);

        cardRecipient.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentsActivity.this, RecipientsActivity.class);
            startActivity(intent);
        });

        cardOnceOffPay.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentsActivity.this, Send_Money_Activity.class);
            startActivity(intent);
        });

        cardCellphone.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentsActivity.this, CashOutActivity.class);
            startActivity(intent);
        });

    }
}