package com.techmania.onebankafrica.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.techmania.onebankafrica.Adapters.StatementsAdapter;
import com.techmania.onebankafrica.Models.StatementsModel;
import com.techmania.onebankafrica.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import java.util.ArrayList;

public class StatementsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<StatementsModel> arrayList;
    private StatementsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statements);

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calender.getTime();

        //1 month
        calender.add(Calendar.MONTH, -1);
        Date pastMonth = calender.getTime();

        //2 months
        calender.add(Calendar.MONTH, -1);
        Date past2Month = calender.getTime();

        //3 months
        calender.add(Calendar.MONTH, -1);
        Date past3Month = calender.getTime();

        //4 months
        calender.add(Calendar.MONTH, -1);
        Date past4Month = calender.getTime();

        //5 months
        calender.add(Calendar.MONTH, -1);
        Date past5Month = calender.getTime();

        //6 months
        calender.add(Calendar.MONTH, -1);
        Date past6Month = calender.getTime();

        //3 months
        calender.add(Calendar.MONTH, +3);
        Date pastDate = calender.getTime();

        //6 months
        calender.add(Calendar.MONTH, -3);
        Date pastDates = calender.getTime();

        //Singular Months
        SimpleDateFormat simpleMonthFormat = new SimpleDateFormat("MMMM");
        String pastMonthName = simpleMonthFormat.format(pastMonth);
        String past2MonthName = simpleMonthFormat.format(past2Month);
        String past3MonthName = simpleMonthFormat.format(past3Month);
        String past4MonthName = simpleMonthFormat.format(past4Month);
        String past5MonthName = simpleMonthFormat.format(past5Month);
        String past6MonthName = simpleMonthFormat.format(past6Month);

        //Multiple Months
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ThreeMonths = simpleDateFormat.format(pastDate) + " - " + simpleDateFormat.format(date);
        String SixMonths = simpleDateFormat.format(pastDates) + " - " + simpleDateFormat.format(date);

        recyclerView = findViewById(R.id.recyclerViewStatements);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();
        arrayList.add(new StatementsModel("Last 3 Months", ThreeMonths));
        arrayList.add(new StatementsModel("Last 6 Months", SixMonths));
        arrayList.add(new StatementsModel(pastMonthName, "free"));
        arrayList.add(new StatementsModel(past2MonthName, "free"));
        arrayList.add(new StatementsModel(past3MonthName, "free"));
        arrayList.add(new StatementsModel(past4MonthName, "free"));
        arrayList.add(new StatementsModel(past5MonthName, "free"));
        arrayList.add(new StatementsModel(past6MonthName, "free"));

        adapter = new StatementsAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
    }
}













