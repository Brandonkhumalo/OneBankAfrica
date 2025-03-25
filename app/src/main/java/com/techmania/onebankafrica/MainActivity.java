package com.techmania.onebankafrica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techmania.onebankafrica.Adapters.MainAdapter;
import com.techmania.onebankafrica.Models.HomeActivityModel;
import com.techmania.onebankafrica.Models.User;
import com.techmania.onebankafrica.Models.UserBank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    String accountNumber;
    TextView accountnumber;
    TextView bankbalance;
    TextView currentbalance;
    TextView accounttime;
    ProgressBar progressBar;

    private RecyclerView recyclerView;
    private ArrayList<HomeActivityModel> arrayList;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressMain);
        progressBar.setVisibility(View.INVISIBLE);

        accountnumber = findViewById(R.id.accountNumber);
        bankbalance = findViewById(R.id.accountBalance);
        currentbalance = findViewById(R.id.accountCurrent);
        accounttime = findViewById(R.id.accountTime);
        GetBankDetails();

        recyclerView = findViewById(R.id.recyclerViewMain);
        //Determine whether the data will be displayed in a horizontal or vertical format
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        arrayList = new ArrayList<>();
        arrayList.add(new HomeActivityModel("transaction", "Payments"));
        arrayList.add(new HomeActivityModel("money", "CashOut"));
        arrayList.add(new HomeActivityModel("card", "Card"));
        arrayList.add(new HomeActivityModel("notifications", "Notifications"));
        arrayList.add(new HomeActivityModel("recepients", "Recipients"));
        arrayList.add(new HomeActivityModel("transaction", "Transactions"));
        arrayList.add(new HomeActivityModel("statements", "Statements"));
        arrayList.add(new HomeActivityModel("confirmation", "Account"));
        /**arrayList.add(new HomeActivityModel("airtime", "Airtime"));
        arrayList.add(new HomeActivityModel("money", "Vouchers"));*/

        adapter = new MainAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);

        updateTime();
    }

    public void GetBankDetails(){
        if(user != null){
            String userId = user.getUid();
            DatabaseReference databaseReference = database.getReference("Users").child(userId).child("Bank");
            progressBar.setVisibility(View.VISIBLE);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserBank bank = snapshot.getValue(UserBank.class);
                    if(bank != null){
                        accountNumber = bank.getaccountNumber();

                        accountnumber.setText("My account " + accountNumber);
                        Double bankBalance = Double.parseDouble(bank.getbankBalance());
                        bankbalance.setText("R" + bankBalance);
                        Double currentBalance = Double.parseDouble(bank.getcurrentBalance());
                        currentbalance.setText("Current Balance: " + "R" + currentBalance);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            DatabaseReference dataBaseRef = database.getReference("Users").child(userId);
            dataBaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userData = snapshot.getValue(User.class);
                    if(userData != null){
                        String userName = userData.getUserName();
                        String userSurname = userData.getUserSurname();

                        TextView nameSurname = findViewById(R.id.nameSurname);
                        nameSurname.setText(userName + " " + userSurname);

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void updateTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String time = sdf.format(calendar.getTime());

        accounttime.setText("As of " + time);
    }
}












