package com.example.testappdans;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.testappdans.api.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailActivity extends AppCompatActivity {
    private ApiRetrofit apiRetrofit;
    TextView companyName, companyUrl, jobTitle, jobLocation, jobType, jobDescription;
    ImageView companyLogo, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        companyName = findViewById(R.id.tv_company_name);
        jobTitle = findViewById(R.id.tv_job_title);
        jobLocation = findViewById(R.id.tv_location);
        jobType = findViewById(R.id.tv_job_type);
        jobDescription = findViewById(R.id.tv_job_description);
        companyLogo = findViewById(R.id.iv_company_logo);
        companyUrl = findViewById(R.id.tv_company_url);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            Intent intentBack = new Intent(JobDetailActivity.this, JobListActivity.class);
            startActivity(intentBack);
            finish();
        });

        getJobDetail();
    }

    private void getJobDetail(){
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("jobId")){
            String id = intent.getStringExtra("jobId");
            apiRetrofit = new ApiRetrofit();
            Call<JobListModel> call = apiRetrofit.getApiDans().getJobDetail(id);
            call.enqueue(new Callback<JobListModel>() {
                @Override
                public void onResponse(Call<JobListModel> call, Response<JobListModel> response) {
                    if (response.isSuccessful()){
                        JobListModel jobListModel = response.body();
                        companyName.setText(jobListModel.company);
                        jobTitle.setText(jobListModel.title);
                        jobLocation.setText(jobListModel.location);
                        jobType.setText(jobListModel.type);
                        jobDescription.setText(jobListModel.description);

                        Glide.with(JobDetailActivity.this)
                                .load(jobListModel.company_logo)
                                .placeholder(R.drawable.ic_image)
                                .error(R.drawable.ic_no_image)
                                .into(companyLogo);

                        SpannableString spannableString = new SpannableString(jobListModel.company_url);
                        ClickableSpan clickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Uri uri = Uri.parse(jobListModel.company_url);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        };

                        spannableString.setSpan(clickableSpan, 0, jobListModel.company_url.length(), 0);
                        companyUrl.setText(spannableString);
                        companyUrl.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
                    }
                }

                @Override
                public void onFailure(Call<JobListModel> call, Throwable t) {
                    Toast.makeText(JobDetailActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}