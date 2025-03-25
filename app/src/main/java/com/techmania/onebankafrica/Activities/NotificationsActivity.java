package com.techmania.onebankafrica.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techmania.onebankafrica.Adapters.NotificationsAdapter;
import com.techmania.onebankafrica.Models.NotificationsModel;
import com.techmania.onebankafrica.R;
import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<NotificationsModel> arrayList;
    private NotificationsAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    ImageView noNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        noNotification = findViewById(R.id.noNotification);
        noNotification.setVisibility(View.INVISIBLE);

        recyclerView = findViewById(R.id.notificationRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();
        adapter = new NotificationsAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);

        getNotifications();
    }

    public void getNotifications(){
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference databaseReference = database.getReference("Users").child(userId).child("Notifications");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();

                    if (snapshot.exists()) {
                        for (DataSnapshot notificationSnapshot : snapshot.getChildren()) {
                            NotificationsModel notification = notificationSnapshot.getValue(NotificationsModel.class);

                            if (notification != null) {
                                arrayList.add(new NotificationsModel(
                                        notification.getHeader(),
                                        notification.getMessage(),
                                        notification.getDate()
                                ));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Handle the case where there are no notifications
                        recyclerView.setVisibility(View.INVISIBLE);
                        noNotification.setVisibility(View.VISIBLE);
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















