package com.example.digiseedbed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.MyViewHolder>{
    Context context;
    ArrayList<Helper> arrayList;

    public ProgressAdapter(Context context, ArrayList<Helper> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ProgressAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.progress_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressAdapter.MyViewHolder holder, int position) {
        Helper helper=arrayList.get(position);
        holder.time_rv.setText(helper.getTime());
        holder.name_rv.setText(helper.getName());
        holder.activity_rv.setText(helper.getActivity_donetxt());
        holder.reason_rv.setText(helper.getReason_donetxt());
    }

    @Override
    public int getItemCount() {
        return  arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView time_rv,name_rv,activity_rv,reason_rv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time_rv=itemView.findViewById(R.id.time_rv);
            name_rv=itemView.findViewById(R.id.name_rv);
            activity_rv=itemView.findViewById(R.id.activity_rv);
            reason_rv=itemView.findViewById(R.id.reason_rv);

        }
    }
}
