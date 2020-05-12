package com.example.projet_android.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projet_android.presentation.model.ClasseEtOrigine;
import com.example.projet_android.R;
import com.example.projet_android.data.TFTApi;
import com.example.projet_android.presentation.model.RestTFTResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String BASE_URL = "https://raw.githubusercontent.com/ChristianStephenn/Projet_Android/master/";
    private SharedPreferences sharedpreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences("ProjetTFT", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<ClasseEtOrigine> classList = getDataFromCache();
        if(classList != null ){
            showList(classList);
        }else{
            MakeApiCall();
        }
    }

    private List<ClasseEtOrigine> getDataFromCache() {
        String jsonList = sharedpreferences.getString("jsonTFTList", null);

        if(jsonList == null){
            return null;
        }else {
            Type ListType = new TypeToken<List<ClasseEtOrigine>>() {
            }.getType();
            return gson.fromJson(jsonList, ListType);
        }
    }

    private void showList(List<ClasseEtOrigine> classList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(classList, getBaseContext());
        recyclerView.setAdapter(mAdapter);
    }

    private void MakeApiCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TFTApi tftApi = retrofit.create(TFTApi.class);

        Call<RestTFTResponse> call = tftApi.getTFTResponse();
        call.enqueue(new Callback<RestTFTResponse>() {
            @Override
            public void onResponse(Call<RestTFTResponse> call, Response<RestTFTResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<ClasseEtOrigine> classList = response.body().getResults();
                    savedList(classList);
                    showList(classList);
                }else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestTFTResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void savedList(List<ClasseEtOrigine> classList) {
        String jsonList = gson.toJson(classList);
        sharedpreferences
                .edit()
                .putString("jsonTFTList", jsonList)
                .apply();

        Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();


    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}
