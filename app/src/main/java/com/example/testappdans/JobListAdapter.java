package com.example.testappdans;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {
    final List<JobListModel> jobList;
    public JobListAdapter(List<JobListModel> jobListModels) {
        this.jobList = jobListModels;
    }

    @NonNull
    @Override
    public JobListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobListModel jobListModel = jobList.get(position);
        holder.tvTitle.setText(jobListModel.title);
        holder.tvCompany.setText(jobListModel.company);
        holder.tvLocation.setText(jobListModel.location);

        Glide.with(holder.itemView.getContext())
                .load(jobListModel.company_logo)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_no_image)
                .into(holder.imgLogo);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), JobDetailActivity.class);
            intent.putExtra("jobId", jobListModel.id);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView tvTitle, tvCompany, tvLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.ivLogo);
            tvTitle = itemView.findViewById(R.id.tv_jobname);
            tvCompany = itemView.findViewById(R.id.tv_companyname);
            tvLocation = itemView.findViewById(R.id.tv_location);
        }
    }
}
