package com.example.projet_android.data;

import com.example.projet_android.presentation.model.RestTFTResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TFTApi {

    @GET("TFTAPI.json")
    Call<RestTFTResponse> getTFTResponse();

}
