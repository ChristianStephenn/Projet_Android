package com.example.projet_android.presentation.controller;

import android.content.Intent;

import com.example.projet_android.presentation.Singletons;
import com.example.projet_android.presentation.model.Champion;
import com.example.projet_android.presentation.view.MyTeamActivity;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MyTeamController {

    private Intent intent;
    private MyTeamActivity view;

    public MyTeamController(MyTeamActivity view, Intent intent){
        this.view = view;
        this.intent = intent;
    }

    public void onStart(){
        Type ListType = new TypeToken<List<Champion>>() {
        }.getType();

        String jsonList = intent.getStringExtra("myTeamList");
        List<Champion> teamList = Singletons.getGson().fromJson(jsonList, ListType);
        view.showTeamList(teamList);
    }
}
