package com.example.projet_android.presentation.controller;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projet_android.R;
import com.example.projet_android.presentation.Constants;
import com.example.projet_android.presentation.Singletons;
import com.example.projet_android.presentation.model.Champion;
import com.example.projet_android.presentation.view.MyTeamActivity;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MyTeamController {

    private Intent intent;
    private MyTeamActivity view;
    private Button button_return;
    private Button button_reset;
    private List<Champion> teamList;

    public MyTeamController(MyTeamActivity view, Intent intent){
        this.view = view;
        this.intent = intent;
    }

    public void onStart(){
        Type ListType = new TypeToken<List<Champion>>() {
        }.getType();

        teamList = getTeamListFromCache();
        if (teamList != null && teamList.isEmpty()){
            Toast.makeText(view.getApplicationContext(), "Your team is empty", Toast.LENGTH_SHORT).show();
        }
        String jsonList = intent.getStringExtra("myTeamList");
        List<Champion> teamList = Singletons.getGson().fromJson(jsonList, ListType);
        view.showTeamList(teamList);

        onBackButtonClick();
        onResetButtonClick();
    }

    private void onBackButtonClick(){
        button_return = view.findViewById(R.id.button_MyTeam_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.finish();
            }
        });
    }

    private void onResetButtonClick(){
        button_reset = view.findViewById(R.id.button_MyTeam_reset);
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTeam();
            }
        });
    }

    private void resetTeam(){
        if(teamList.isEmpty()){
            Toast.makeText(view.getApplicationContext(), "Your team is empty", Toast.LENGTH_SHORT).show();
        }else {
            teamList.clear();
            saveNewTeamList();
            Toast.makeText(view.getApplicationContext(), "Team reset", Toast.LENGTH_SHORT).show();
            view.finish();
        }
    }

    private List<Champion> getTeamListFromCache() {
        String TeamList = Singletons.getSharedPreferences(view.getApplicationContext()).getString(Constants.KEY_TEAM_LIST, null);

        if(TeamList == null){
            return null;
        }else {
            Type ListType = new TypeToken<List<Champion>>() {
            }.getType();
            return Singletons.getGson().fromJson(TeamList, ListType);
        }
    }

    private void saveNewTeamList(){

        String jsonTeamList = Singletons.getGson().toJson(teamList);
        Singletons.getSharedPreferences(view.getApplicationContext())
                .edit()
                .putString(Constants.KEY_TEAM_LIST, jsonTeamList)
                .apply();
    }

    public void removeItem(Champion item) {
        for (int i = 0; i < teamList.size(); i++) {
            if(teamList.get(i).getName().equals(item.getName())){
                teamList.remove(i);
                saveNewTeamList();
                Toast.makeText(view.getApplicationContext(), "Champion removed", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
