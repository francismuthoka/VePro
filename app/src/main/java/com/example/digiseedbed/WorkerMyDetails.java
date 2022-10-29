package com.example.digiseedbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WorkerMyDetails extends AppCompatActivity {
    TextView mydetailsnametxt,mydetailsemailtxt,mydetailspassswordtxt,mydetailsdateofbirthtxt,mydetailsidtxt,mydetailsgender;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_my_details);
        mydetailsnametxt=findViewById(R.id.mydetailsnametxt);
        mydetailsemailtxt=findViewById(R.id.mydetailsemailtxt);
        mydetailspassswordtxt=findViewById(R.id.mydetailspassswordtxt);
        mydetailsdateofbirthtxt=findViewById(R.id.mydetailsdateofbirthtxt);
        mydetailsidtxt=findViewById(R.id.mydetailsidtxt);
        mydetailsgender=findViewById(R.id.mydetailsgender);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("WORKERS");
        firebaseAuth=FirebaseAuth.getInstance();

        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AddWorkerClass addWorkerClass=snapshot.getValue(AddWorkerClass.class);
                mydetailsnametxt.setText(addWorkerClass.getWorker_name());
                mydetailsemailtxt.setText(addWorkerClass.getWorker_email());
                mydetailspassswordtxt.setText(addWorkerClass.getWorker_passwo());
                mydetailsdateofbirthtxt.setText(addWorkerClass.getWorker_date_of_birth());
                mydetailsidtxt.setText(addWorkerClass.getWorker_id_no());
                mydetailsgender.setText(addWorkerClass.getGender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WorkerMyDetails.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}