package com.example.testappdans;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testappdans.api.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListActivity extends AppCompatActivity {
    private ApiRetrofit apiRetrofit;
    private RecyclerView recyclerView;
    private JobListAdapter adapter;
    private ToggleButton toggleButton;
    private View activityFilter;
    View filterLayout;
    Button btnApply;
    Switch typeFullTime;
    EditText etLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        filterLayout = findViewById(R.id.filter_layout);
        btnApply = filterLayout.findViewById(R.id.btn_search);
        toggleButton = filterLayout.findViewById(R.id.tbFilter);
        etLocation = filterLayout.findViewById(R.id.et_location);
        typeFullTime = filterLayout.findViewById(R.id.filter_full_time);

        recyclerView = findViewById(R.id.rv_list_job);
        adapter = new JobListAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        setupJobList();
        showFilter();
    }

    private void setupJobList() {
        apiRetrofit = new ApiRetrofit();
        Call<List<JobListModel>> call = apiRetrofit.getApiDans().getJobList();

        call.enqueue(new Callback<List<JobListModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<JobListModel>> call, Response<List<JobListModel>> response) {
                if (response.isSuccessful()) {
                    List<JobListModel> jobListModels = response.body();
                    adapter = new JobListAdapter(jobListModels);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(JobListActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<JobListModel>> call, Throwable t) {
                Toast.makeText(JobListActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFilter() {
        toggleButton = findViewById(R.id.tbFilter);
        activityFilter = findViewById(R.id.filter_layout);
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                activityFilter.setVisibility(View.VISIBLE);
                filterJob();
            } else {
                activityFilter.setVisibility(View.GONE);
            }
        });
    }

    private void filterJob() {
        typeFullTime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String type = isChecked ? "fulltime" : "";
            String location = etLocation.getText().toString();

            btnApply.setOnClickListener(v -> {
                apiRetrofit = new ApiRetrofit();
                Call<List<JobListModel>> callFilter = apiRetrofit.getApiDans().getFilter(location, type);
                callFilter.enqueue(new Callback<List<JobListModel>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<List<JobListModel>> call, Response<List<JobListModel>> response) {
                        if (response.isSuccessful()) {
                            List<JobListModel> jobListModels = response.body();
                            adapter = new JobListAdapter(jobListModels);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setHasFixedSize(true);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(JobListActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<JobListModel>> call, Throwable t) {
                        Toast.makeText(JobListActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }
}