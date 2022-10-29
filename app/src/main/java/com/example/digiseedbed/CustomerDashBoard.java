package com.example.digiseedbed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomerDashBoard extends AppCompatActivity {
    LinearLayout Customer_my_details,customer_my_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dash_board);

        Customer_my_details=findViewById(R.id.Customer_my_details);
        customer_my_progress=findViewById(R.id.customer_my_progress);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setNavigationBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        Customer_my_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CustomerViewMyDetails.class));
            }
        });

        customer_my_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDashBoard.this,NurseryBedProgress.class);
                intent.putExtra("activity","Customer Activity");
                startActivity(intent);
            }
        });
    }
}