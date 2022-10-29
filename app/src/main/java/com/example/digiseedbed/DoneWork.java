package com.example.digiseedbed;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class DoneWork extends AppCompatActivity {
    TextInputEditText activity_done,Reason_done,code_done;
    Button addactivity_btn;

    String name;
    ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_work);
        activity_done=findViewById(R.id.activity_done);
        Reason_done=findViewById(R.id.Reason_done);
        code_done=findViewById(R.id.code_done);
        addactivity_btn=findViewById(R.id.addactivity_btn);

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("WORKERS");
        firebaseAuth=FirebaseAuth.getInstance();

        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AddWorkerClass addWorkerClass=snapshot.getValue(AddWorkerClass.class);
                name=addWorkerClass.getWorker_name();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        code_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });

        addactivity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addActivity();

            }
        });
    }

    private void scanCode() {
        ScanOptions options=new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLoucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLoucher=registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents()!=null){
            code_done.setText(result.getContents());
        }
    });

    private void addActivity() {
        String activity_donetxt=activity_done.getText().toString().trim();
        String Reason_donetxt=Reason_done.getText().toString().trim();
        String code_donetxt=code_done.getText().toString().trim();

        Calendar calendar;
        calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("ddMMyyyyHHmmss");
        String timetxt=dateFormat.format(calendar.getTime());
        SimpleDateFormat dformat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String time=dformat.format(calendar.getTime());


        if (activity_donetxt.isEmpty()){
            activity_done.setError("field required");
            activity_done.requestFocus();
        }
        else if (Reason_donetxt.isEmpty()){
            Reason_done.setError("field required");
            Reason_done.requestFocus();
        }
        else if (code_donetxt.isEmpty()){
            code_done.setError("field required");
            code_done.requestFocus();
        }
        else {
            progressDialog=new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setTitle("adding activity");
            progressDialog.setMessage("please wait...");
            progressDialog.show();

          Helper helper=new Helper(timetxt,code_donetxt,name,activity_donetxt,Reason_donetxt,time);
            Toast.makeText(this, ""+helper.code_donetxt, Toast.LENGTH_SHORT).show();
            databaseReference=firebaseDatabase.getReference("NURSERY BEDS").child(code_donetxt);
            databaseReference.child(timetxt).setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(DoneWork.this, "Activity Added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DoneWork.this,WorkerDashBoard.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DoneWork.this, "Failed to add user", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}