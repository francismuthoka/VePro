package com.example.digiseedbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddWorker extends AppCompatActivity {
    TextInputEditText worker_name,worker_id_no,worker_date_of_birth,worker_email,worker_password;
    RadioButton w_female_rd,w_male_rd;

    Spinner genderWorker;
    String sex;

    Button add_worker_btn;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    DatePickerDialog.OnDateSetListener dateOfBirthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);
        worker_name=findViewById(R.id.worker_name);
        worker_id_no=findViewById(R.id.worker_id_no);
        worker_date_of_birth=findViewById(R.id.worker_date_of_birth);
        worker_email=findViewById(R.id.worker_email);
        worker_password=findViewById(R.id.worker_password);
        genderWorker=findViewById(R.id.genderWorker);
        add_worker_btn=findViewById(R.id.add_worker_btn);

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("WORKERS");
        firebaseAuth=FirebaseAuth.getInstance();

        List<String> genderlist=new ArrayList<>();
        genderlist.add("male");
        genderlist.add("female");

        ArrayAdapter<String> workeradaptergender=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,genderlist);
        genderWorker.setAdapter(workeradaptergender);
        genderWorker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sex=genderlist.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        worker_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddWorker.this,dateOfBirthListener,year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.getDatePicker().setMinDate((long) (System.currentTimeMillis()-(60*60*24*364.25*100*1000)));
                datePickerDialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis()-(60*60*24*364.25*18*1000)));
                datePickerDialog.show();
            }
        });
        dateOfBirthListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                worker_date_of_birth.setText(dateFormat.format(calendar.getTime()));
            }
        };

        add_worker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWorker();
            }
        });


    }

    private void addWorker() {
        String worker_nametxt=worker_name.getText().toString().trim();
        String worker_id_notxt=worker_id_no.getText().toString().trim();
        String worker_date_of_birthtxt=worker_date_of_birth.getText().toString().trim();
        String worker_emailtxt=worker_email.getText().toString().trim();
        String worker_passwordtxt=worker_password.getText().toString().trim();

        if (worker_nametxt.isEmpty()){
            worker_name.setError("field required");
            worker_name.requestFocus();
        }

        else if (worker_id_notxt.isEmpty()){
            worker_id_no.setError("field required");
            worker_id_no.requestFocus();
        }

        else if (worker_date_of_birthtxt.isEmpty()){
            worker_date_of_birth.setError("field required");
            worker_date_of_birth.requestFocus();
        }

        else if (worker_emailtxt.isEmpty()){
            worker_email.setError("field required");
            worker_email.requestFocus();
        }

        else if (worker_passwordtxt.isEmpty()){
            worker_password.setError("field require");
            worker_password.requestFocus();
        }
        else if (sex.isEmpty()){
            genderWorker.requestFocus();
        }

        else {
            progressDialog=new ProgressDialog(AddWorker.this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("adding worker");
            progressDialog.setMessage("please wait...");
            progressDialog.show();
            AddWorkerClass addWorkerClass=new AddWorkerClass(worker_nametxt,worker_id_notxt,worker_date_of_birthtxt,worker_emailtxt,worker_passwordtxt,sex);
            firebaseAuth.createUserWithEmailAndPassword(worker_emailtxt,worker_passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(addWorkerClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                databaseReference=firebaseDatabase.getReference("ROLES");
                                Roles roles=new Roles(firebaseAuth.getCurrentUser().getUid(),"Worker");
                                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(roles).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(),AdminDashBoard.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), " Failed to add worker", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), " Failed to add worker", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

    }
}