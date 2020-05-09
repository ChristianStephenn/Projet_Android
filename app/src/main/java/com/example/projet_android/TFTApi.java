package com.example.projet_android;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TFTApi {

    @GET("TFTAPI.json")
    Call<RestTFTResponse> getTFTResponse();

}
