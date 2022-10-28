package com.example.digiseedbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {
    EditText username,pass;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    Button login_btn;
    DatabaseReference databaseReference;
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
                            databaseReference=firebaseDatabase.getReference("ROLES").child(firebaseAuth.getCurrentUser().getUid().toString());
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