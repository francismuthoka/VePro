package com.example.digiseedbed;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminProgressAdapter extends RecyclerView.Adapter<AdminProgressAdapter.MyViewHolder> {
    Context context;
    ArrayList<NursuryBedClass> arrayList;

    public AdminProgressAdapter(Context context, ArrayList<NursuryBedClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AdminProgressAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.admin_progess_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminProgressAdapter.MyViewHolder holder, int position) {
        NursuryBedClass nursuryBedClass=arrayList.get(position);
        holder.admin_code.setText(nursuryBedClass.getCustomer_codetxt());
        holder.admin_date_pla.setText(nursuryBedClass.getDate_plantedtxt());
        holder.admin_plant_name.setText(nursuryBedClass.getPlant_na());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context.getApplicationContext(),NurseryBedProgress.class);
                intent.putExtra("name",nursuryBedClass.getCustomer_codetxt());
                intent.putExtra("activity","AdminProgressAdapter");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView admin_code,admin_date_pla,admin_plant_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            admin_code=itemView.findViewById(R.id.admin_code);
            admin_date_pla=itemView.findViewById(R.id.admin_date_pla);
            admin_plant_name=itemView.findViewById(R.id.admin_plant_name);
        }
    }
}
