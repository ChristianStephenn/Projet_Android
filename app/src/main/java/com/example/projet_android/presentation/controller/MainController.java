package com.example.projet_android.presentation.controller;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projet_android.R;
import com.example.projet_android.presentation.Constants;
import com.example.projet_android.presentation.Singletons;
import com.example.projet_android.presentation.model.Champion;
import com.example.projet_android.presentation.model.ClasseEtOrigine;
import com.example.projet_android.presentation.model.RestTFTResponse;
import com.example.projet_android.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {

    private SharedPreferences sharedpreferences;
    private Gson gson;
    private MainActivity view;
    private Button button_team;

    public MainController(MainActivity view, Gson gson, SharedPreferences sharedPreferences){
        this.view = view;
        this.gson = gson;
        this.sharedpreferences = sharedPreferences;
    }

    public void onStart() {

        List<ClasseEtOrigine> classList = getDataFromCache();
        if(classList != null ){
            view.showList(classList);
        }else{
            MakeApiCall();
        }
        onTeamButtonClick();
    }

    private void onTeamButtonClick(){
        button_team = view.findViewById(R.id.MyTeamButton);
        button_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Champion> teamList = getTeamListFromCache();
                if(teamList != null){
                    view.navigateToTeam(teamList);
                }
            }
        });
    }

    private void MakeApiCall(){

        Call<RestTFTResponse> call = Singletons.getTFTApi().getTFTResponse();
        call.enqueue(new Callback<RestTFTResponse>() {
            @Override
            public void onResponse(Call<RestTFTResponse> call, Response<RestTFTResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<ClasseEtOrigine> classList = response.body().getResults();
                    savedList(classList);
                    view.showList(classList);
                }else{
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestTFTResponse> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void savedList(List<ClasseEtOrigine> classList) {
        String jsonList = gson.toJson(classList);
        sharedpreferences
                .edit()
                .putString(Constants.KEY_TFT_LIST, jsonList)
                .apply();

        Toast.makeText(view.getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();
    }

    private List<ClasseEtOrigine> getDataFromCache() {
        String jsonList = sharedpreferences.getString(Constants.KEY_TFT_LIST, null);

        if(jsonList == null){
            return null;
        }else {
            Type ListType = new TypeToken<List<ClasseEtOrigine>>() {
            }.getType();
            return gson.fromJson(jsonList, ListType);
        }
    }

    private List<Champion> getTeamListFromCache() {
        String TeamList = sharedpreferences.getString(Constants.KEY_TEAM_LIST, null);

        if(TeamList == null){
            return null;
        }else {
            Type ListType = new TypeToken<List<Champion>>() {
            }.getType();
            return Singletons.getGson().fromJson(TeamList, ListType);
        }
    }

    public void onItemClick(ClasseEtOrigine classe) {
        view.navigateToDetails(classe);
    }
}
