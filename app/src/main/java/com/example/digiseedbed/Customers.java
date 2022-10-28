package com.example.digiseedbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Customers extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    RecyclerView customers_recyclerView;

    CustomerAdapter customerAdapter;
    ArrayList<AddCustomerClass> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        customers_recyclerView=findViewById(R.id.customers_recyclerView);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("CUSTOMERS");
        customers_recyclerView.hasFixedSize();
        customers_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<>();
        customerAdapter=new CustomerAdapter(this,arrayList);
        customers_recyclerView.setAdapter(customerAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    AddCustomerClass addCustomerClass=dataSnapshot.getValue(AddCustomerClass.class);
                    arrayList.add(addCustomerClass);
                }
                customerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}