package com.example.digiseedbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerViewMyDetails extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    TextView customer_name_rv,phone_phone_cv,customer_pas_rv,customer_email_rv,customer_date_rv,customer_my_co_rv,customer_gen_rv,customer_res_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_my_details);

        customer_name_rv=findViewById(R.id.customer_name_rv);
        phone_phone_cv=findViewById(R.id.phone_phone_cv);
        customer_pas_rv=findViewById(R.id.customer_pas_rv);
        customer_email_rv=findViewById(R.id.customer_email_rv);
        customer_date_rv=findViewById(R.id.customer_date_rv);
        customer_my_co_rv=findViewById(R.id.customer_my_co_rv);
        customer_gen_rv=findViewById(R.id.customer_gen_rv);
        customer_res_rv=findViewById(R.id.customer_res_rv);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setNavigationBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference=firebaseDatabase.getReference("CUSTOMERS").child(firebaseAuth.getCurrentUser().getUid().trim());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AddCustomerClass addCustomerClass=snapshot.getValue(AddCustomerClass.class);
                customer_name_rv.setText(addCustomerClass.getCustomer_name());
                phone_phone_cv.setText(addCustomerClass.getCustomer_phone());
                customer_pas_rv.setText(addCustomerClass.getCustomer_password());
                customer_email_rv.setText(addCustomerClass.getCustomer_email());
                customer_date_rv.setText(addCustomerClass.getCustomer_date_birth());
                customer_my_co_rv.setText(addCustomerClass.getCustomer_code());
                customer_gen_rv.setText(addCustomerClass.getGender());
                customer_res_rv.setText(addCustomerClass.getCustomer_resident());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}