package com.example.digiseedbed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AdminDashBoard extends AppCompatActivity {

    LinearLayout progress,customers,workers,add_customer,add_worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);
        progress=findViewById(R.id.progress);
        customers=findViewById(R.id.customers);
        workers=findViewById(R.id.workers);
        add_customer=findViewById(R.id.add_customer);
        add_worker=findViewById(R.id.add_worker);
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBoard.this,ProgressAdmin.class));
            }
        });

        add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddCustomer.class));
            }
        });

        add_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddWorker.class));
            }
        });

        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Customers.class));
            }
        });
        workers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Workers.class));
            }
        });


    }
}