package com.example.digiseedbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProgressAdmin extends AppCompatActivity {

    RecyclerView admin_progress_recyclerView;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    AdminProgressAdapter adminProgressAdapter;

    ArrayList<NursuryBedClass> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_admin);

        admin_progress_recyclerView=findViewById(R.id.admin_progress_recyclerView);

        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        admin_progress_recyclerView.hasFixedSize();
        admin_progress_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<>();
        adminProgressAdapter=new AdminProgressAdapter(this,arrayList);
        admin_progress_recyclerView.setAdapter(adminProgressAdapter);

        databaseReference=firebaseDatabase.getReference("NURSERY BEDS DESCRIPTION");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    NursuryBedClass nursuryBedClass=dataSnapshot.getValue(NursuryBedClass.class);
                    arrayList.add(nursuryBedClass);
                }
                adminProgressAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}