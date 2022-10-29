package com.example.digiseedbed;

import static com.example.digiseedbed.R.id.genderSpinner;
import static com.example.digiseedbed.R.id.spinner_plant_name;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddCustomer extends AppCompatActivity {
    TextInputEditText customer_name,customer_Email,customer_password,customer_resident,customer_code,customer_date_birth,customer_phone,date_planted,gender;
   Button add_customer_btn;

   Spinner spinner_plant,genderSpinner;

   ProgressDialog progressDialog;

   String plant_na,gend;

   FirebaseDatabase firebaseDatabase;
   FirebaseAuth firebaseAuth;
   DatabaseReference databaseReference;
    DatePickerDialog.OnDateSetListener dateOfBirthListener;
    DatePickerDialog.OnDateSetListener dateOfPlantingListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=firebaseDatabase.getReference("CUSTOMERS");

        customer_name=findViewById(R.id.customer_name);
        customer_Email=findViewById(R.id.customer_Email);
        customer_password=findViewById(R.id.customer_password);
        customer_resident=findViewById(R.id.customer_resident);
        customer_code=findViewById(R.id.customer_code);
        customer_date_birth=findViewById(R.id.customer_date_birth);
        customer_phone=findViewById(R.id.customer_phone);
        date_planted=findViewById(R.id.date_planted);
        add_customer_btn=findViewById(R.id.add_customer_btn);
        spinner_plant=findViewById(spinner_plant_name);
        genderSpinner=findViewById(R.id.genderSpinner);

        List<String> plantlist=new ArrayList<>();
        plantlist.add("Coffee plant");
        plantlist.add("Mango plant");
        plantlist.add("Cabbages plants");
        plantlist.add("Kales plant");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,plantlist);
        spinner_plant.setAdapter(adapter);

        spinner_plant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                plant_na=plantlist.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> genderlist=new ArrayList<>();
        genderlist.add("male");
        genderlist.add("female");

        ArrayAdapter<String> adaptergender=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,genderlist);
        genderSpinner.setAdapter(adaptergender);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gend=genderlist.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        add_customer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomer();
            }
        });

        customer_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });

        customer_date_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddCustomer.this,dateOfBirthListener,year,month,day);
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
                customer_date_birth.setText(dateFormat.format(calendar.getTime()));
            }
        };

        date_planted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddCustomer.this,dateOfPlantingListener,year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.getDatePicker().setMinDate((long) (System.currentTimeMillis()-(60*60*24*364.25*10*1000)));
                datePickerDialog.show();
            }
        });
        dateOfPlantingListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                date_planted.setText(dateFormat.format(calendar.getTime()));
            }
        };


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
            customer_code.setText(result.getContents());
        }
    });

    private void addCustomer(){
        String customer_nametxt=customer_name.getText().toString().trim();
        String customer_emailtxt=customer_Email.getText().toString().trim();
        String customer_passwordtxt=customer_password.getText().toString().trim();
        String customer_residenttxt=customer_resident.getText().toString().trim();
        String customer_codetxt=customer_code.getText().toString().trim();
        String customer_date_birthtxt=customer_date_birth.getText().toString().trim();
        String customer_phonetxt=customer_phone.getText().toString().trim();
        String date_plantedtxt=date_planted.getText().toString().trim();

        if(customer_nametxt.isEmpty())
        {
            customer_name.setError("this field can not be empty");
            customer_name.requestFocus();
        }
        else if(customer_emailtxt.isEmpty()){
            customer_Email.setError("field can not be empty");
            customer_Email.requestFocus();
        }

        else if(customer_passwordtxt.isEmpty()){
            customer_password.setError("field can not be empty");
            customer_password.requestFocus();
        }

        else if(customer_residenttxt.isEmpty()){
            customer_resident.setError("field can not be empty");
            customer_resident.requestFocus();
        }

        else if(customer_codetxt.isEmpty()){
            customer_code.setError("field can not be empty");
            customer_code.requestFocus();
        }

        else if(customer_date_birthtxt.isEmpty()){
            customer_date_birth.setError("field can not be empty");
            customer_date_birth.requestFocus();
        }

        else if(customer_phonetxt.isEmpty()){
            customer_phone.setError("field can not be empty");
            customer_phone.requestFocus();
        }

        else if(date_plantedtxt.isEmpty()){
            date_planted.setError("field can not be empty");
            date_planted.requestFocus();
        }

        else if(plant_na.isEmpty()){
            spinner_plant.requestFocus();
        }
        else if(gend.isEmpty()){
            genderSpinner.requestFocus();
        }

        else{
            progressDialog=new ProgressDialog(AddCustomer.this);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setTitle("adding customer");
            progressDialog.setMessage("please wait...");
            progressDialog.show();
            AddCustomerClass addCustomerClass=new AddCustomerClass(customer_nametxt,customer_emailtxt,customer_passwordtxt,
                         customer_residenttxt,customer_codetxt,customer_date_birthtxt,customer_phonetxt,gend);
            NursuryBedClass nursuryBedClass=new NursuryBedClass(customer_codetxt,date_plantedtxt,plant_na);
            firebaseAuth.createUserWithEmailAndPassword(customer_emailtxt,customer_passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()){
                                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(addCustomerClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                                databaseReference.child(customer_codetxt);
                                                databaseReference = firebaseDatabase.getReference("ROLES");
                                                Roles roles = new Roles(firebaseAuth.getCurrentUser().getUid(), "Customer");
                                                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(roles).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        databaseReference = firebaseDatabase.getReference("NURSERY BEDS DESCRIPTION");
                                                        databaseReference.child(customer_codetxt).setValue(nursuryBedClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                progressDialog.dismiss();
                                                                startActivity(new Intent(AddCustomer.this, AdminDashBoard.class));
                                                            }
                                                        });

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(AddCustomer.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }
                                });

                         }
                         else {
                             progressDialog.dismiss();
                             Toast.makeText(AddCustomer.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }

            });
        }

    }
}