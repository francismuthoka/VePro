package com.example.digiseedbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.Util;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    EditText username,pass;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    Button login_btn;

    CheckBox checkbox;

    TextView forgot_pass;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        username=findViewById(R.id.username);
        pass=findViewById(R.id.pass);
        login_btn=findViewById(R.id.login_btn);
        forgot_pass=findViewById(R.id.forgot_pass);
        checkbox=findViewById(R.id.checkbox);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked())
                {
                    SharedPreferences sharedPreferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();

                }
                else if (!compoundButton.isChecked()){
                    SharedPreferences sharedPreferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("remember", "false");
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
            window.setNavigationBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Patterns.EMAIL_ADDRESS.matcher(username.getText().toString()).matches()){
                    username.setError("please enter valid password");
                    username.requestFocus();
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(username.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Login.this, "please check on your email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }
    private void loginUser() {
        String emailText=username.getText().toString().trim();
        String passwordText=pass.getText().toString().trim();
        if(emailText.isEmpty()){
            username.setError("enter valid email address");
            username.requestFocus();
        }
        else if(passwordText.isEmpty()){
            pass.setError("enter strong password");
            pass.requestFocus();

        }
        else{
            progressDialog=new ProgressDialog(Login.this);
            progressDialog.setTitle("login");
            progressDialog.setMessage("please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        if (emailText.equals("francismutua664@gmail.com")){
                            progressDialog.dismiss();
                            startActivity(new Intent(Login.this, AdminDashBoard.class));

                        }
                        else {
                            databaseReference=firebaseDatabase.getReference("ROLES").child(firebaseAuth.getCurrentUser().getUid());
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Roles roles=snapshot.getValue(Roles.class);
                                        if (roles.getRole().equals("Worker")){
                                            progressDialog.dismiss();
                                            startActivity(new Intent(Login.this, WorkerDashBoard.class));
                                        }
                                        else if(roles.getRole().equals("Customer")){
                                            progressDialog.dismiss();
                                            startActivity(new Intent(Login.this, CustomerDashBoard.class));
                                        }
                                        else{
                                            progressDialog.dismiss();
                                            firebaseAuth.signOut();
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    progressDialog.dismiss();
                                    firebaseAuth.signOut();
                                    Toast.makeText(getApplicationContext(),""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}