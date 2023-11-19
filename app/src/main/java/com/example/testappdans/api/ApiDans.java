package com.example.testappdans.api;

import com.example.testappdans.JobListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiDans {

    @GET("api/recruitment/positions.json")
    Call<List<JobListModel>> getJobList();

    @GET("api/recruitment/positions.json")
    Call<List<JobListModel>> getFilter(
            @Query("location") String location,
            @Query("type") String type
    );

    @GET("api/recruitment/positions/{id}")
    Call<JobListModel> getJobDetail(@Path("id") String id);
}
