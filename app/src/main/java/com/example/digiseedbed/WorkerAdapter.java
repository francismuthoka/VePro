package com.example.digiseedbed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.MyViewHolder> {
    Context context;
    ArrayList<AddWorkerClass> arrayList;

    public WorkerAdapter(Context context, ArrayList<AddWorkerClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public WorkerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.worker_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerAdapter.MyViewHolder holder, int position) {
        AddWorkerClass addWorkerClass=arrayList.get(position);
        holder.Worker_name_rv.setText(addWorkerClass.getWorker_name());
        holder.worker_id_cv.setText(addWorkerClass.getWorker_id_no());
        holder.worker_gender_rv.setText(addWorkerClass.getGender());
        holder.worker_email_rv.setText(addWorkerClass.getWorker_email());
        holder.worker_date_rv.setText(addWorkerClass.getWorker_date_of_birth());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Worker_name_rv,worker_id_cv,worker_gender_rv,worker_email_rv,worker_date_rv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Worker_name_rv=itemView.findViewById(R.id.Worker_name_rv);
            worker_id_cv=itemView.findViewById(R.id.worker_id_cv);
            worker_gender_rv=itemView.findViewById(R.id.worker_gender_rv);
            worker_email_rv=itemView.findViewById(R.id.worker_email_rv);
            worker_date_rv=itemView.findViewById(R.id.worker_date_rv);
        }
    }
}
