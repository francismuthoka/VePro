package com.example.digiseedbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NurseryBedProgress extends AppCompatActivity {

    TextView customer_plant_rv,date_planted_cv,plant_code_rv;

    RecyclerView progress_recyclerView;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    String code;
    String name;
    String activity;

    ProgressAdapter progressAdapter;

    ArrayList<Helper> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursery_bed_progress);
        customer_plant_rv=findViewById(R.id.customer_plant_rv);
        date_planted_cv=findViewById(R.id.date_planted_cv);
        plant_code_rv=findViewById(R.id.plant_code_rv);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setNavigationBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        Intent intent=getIntent();
        name =intent.getStringExtra("name");
        activity=intent.getStringExtra("activity");

        progress_recyclerView=findViewById(R.id.progress_recyclerView);

        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        progress_recyclerView.hasFixedSize();
        progress_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<>();
        progressAdapter=new ProgressAdapter(this,arrayList);
        progress_recyclerView.setAdapter(progressAdapter);

        if (activity.equals("AdminProgressAdapter")) {
            databaseReference=firebaseDatabase.getReference("NURSERY BEDS");

            databaseReference.child(name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Helper helper=dataSnapshot.getValue(Helper.class);
                        arrayList.add(helper);
                    }
                    progressAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            databaseReference=firebaseDatabase.getReference("NURSERY BEDS DESCRIPTION").child(name);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    NursuryBedClass nursuryBedClass=snapshot.getValue(NursuryBedClass.class);
                    customer_plant_rv.setText(nursuryBedClass.getPlant_na());
                    date_planted_cv.setText(nursuryBedClass.getDate_plantedtxt());
                    plant_code_rv.setText(nursuryBedClass.getCustomer_codetxt());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        else if (activity.equals("Customer Activity")){

            databaseReference=firebaseDatabase.getReference("CUSTOMERS").child(firebaseAuth.getCurrentUser().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    AddCustomerClass addCustomerClass=snapshot.getValue(AddCustomerClass.class);
                    code = addCustomerClass.getCustomer_code();

                    databaseReference=firebaseDatabase.getReference("NURSERY BEDS");

                    databaseReference.child(code).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                Helper helper=dataSnapshot.getValue(Helper.class);
                                arrayList.add(helper);
                            }
                            progressAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    databaseReference=firebaseDatabase.getReference("NURSERY BEDS DESCRIPTION").child(code);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            NursuryBedClass nursuryBedClass=snapshot.getValue(NursuryBedClass.class);
                            customer_plant_rv.setText(nursuryBedClass.getPlant_na());
                            date_planted_cv.setText(nursuryBedClass.getDate_plantedtxt());
                            plant_code_rv.setText(nursuryBedClass.getCustomer_codetxt());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });


        }

    }
}