package com.example.listaapifilmes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApiService {
    final String KEY = "?apikey=2785f90b";

    @GET(KEY)
    Call<SearchModel> searchByTitle(@Query("s") String title);

}
