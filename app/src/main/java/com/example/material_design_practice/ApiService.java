package com.example.material_design_practice;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot")
    Call<pictureResponse> getPicture();
}
