package com.example.digiseedbed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WorkerDashBoard extends AppCompatActivity {

    LinearLayout add_activity,worker_detils_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_dash_board);
        add_activity=findViewById(R.id.add_activity);
        worker_detils_cv=findViewById(R.id.worker_detils_cv);

        add_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DoneWork.class));
            }
        });

        worker_detils_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),WorkerMyDetails.class));
            }
        });


    }
}