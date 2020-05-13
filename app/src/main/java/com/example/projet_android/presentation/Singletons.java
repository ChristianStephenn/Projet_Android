package com.example.projet_android.presentation;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.projet_android.data.TFTApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static TFTApi tftApiInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson(){
        if(gsonInstance == null){
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }

        return gsonInstance;
    }

    public static TFTApi getTFTApi(){
        if(tftApiInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            tftApiInstance = retrofit.create(TFTApi.class);
        }

        return tftApiInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        if(sharedPreferencesInstance == null){
            sharedPreferencesInstance = context.getSharedPreferences("ProjetTFT", Context.MODE_PRIVATE);
        }

        return sharedPreferencesInstance;
    }
}
