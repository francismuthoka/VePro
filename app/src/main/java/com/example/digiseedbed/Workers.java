package com.example.digiseedbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Workers extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    RecyclerView workers_recyclerView;

    WorkerAdapter workerAdapter;
    ArrayList<AddWorkerClass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers);
        workers_recyclerView=findViewById(R.id.workers_recyclerView);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("WORKERS");
        workers_recyclerView.hasFixedSize();
        workers_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<>();
        workerAdapter=new WorkerAdapter(this,arrayList);
        workers_recyclerView.setAdapter(workerAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    AddWorkerClass addWorkerClass=dataSnapshot.getValue(AddWorkerClass.class);
                    arrayList.add(addWorkerClass);
                }
                workerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}