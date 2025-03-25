package com.techmania.onebankafrica.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.techmania.onebankafrica.Adapters.ViewPagerAdapter;
import com.techmania.onebankafrica.R;

public class TransactionsActivity extends AppCompatActivity {
    private TabLayout tabLayoutTransactions;
    private ViewPager2 viewPagerTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        tabLayoutTransactions = findViewById(R.id.tabLayoutTransactions);
        viewPagerTransactions = findViewById(R.id.viewPagerTransactions);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPagerTransactions.setAdapter(adapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayoutTransactions, viewPagerTransactions
                , true, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Inbound Transactions");
                        break;
                    case 1: tab.setText("Outbound Transactions");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }
}