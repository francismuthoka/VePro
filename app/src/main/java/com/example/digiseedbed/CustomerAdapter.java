package com.example.digiseedbed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {
    public CustomerAdapter(Context context, ArrayList<AddCustomerClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    Context context;
    ArrayList<AddCustomerClass> arrayList;
    @NonNull
    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.customer_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.MyViewHolder holder, int position) {
        AddCustomerClass addCustomerClass=arrayList.get(position);
        holder.name_rv.setText(addCustomerClass.getCustomer_name());
        holder.code_cv.setText(addCustomerClass.getCustomer_code());
        holder.resident_rv.setText(addCustomerClass.getCustomer_resident());
        holder.email_rv.setText(addCustomerClass.getCustomer_email());
        holder.date_rv.setText(addCustomerClass.getCustomer_date_birth());



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_rv,code_cv,resident_rv,email_rv,date_rv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_rv=itemView.findViewById(R.id.name_rv);
            code_cv=itemView.findViewById(R.id.code_cv);
            resident_rv=itemView.findViewById(R.id.resident_rv);
            email_rv=itemView.findViewById(R.id.email_rv);
            date_rv=itemView.findViewById(R.id.date_rv);
        }
    }
}
