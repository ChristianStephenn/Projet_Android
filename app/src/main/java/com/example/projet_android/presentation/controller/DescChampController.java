package com.example.projet_android.presentation.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_android.R;
import com.example.projet_android.presentation.Constants;
import com.example.projet_android.presentation.Singletons;
import com.example.projet_android.presentation.model.Champion;
import com.example.projet_android.presentation.model.ClasseEtOrigine;
import com.example.projet_android.presentation.view.ChampionsActivity;
import com.example.projet_android.presentation.view.DescriptionChampActivity;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DescChampController {

    private DescriptionChampActivity view;
    private Intent intent;
    private ImageView imageView;
    private TextView textViewname;
    private TextView textViewcoast;
    private TextView textViewclass;
    private Button button_return;
    private Button button_ajt;
    private Champion champion;

    public DescChampController(DescriptionChampActivity view, Intent intent) {
        this.view = view;
        this.intent = intent;
    }

    public void onStart(){

        imageView = view.findViewById(R.id.DescChampIcon);
        textViewname = view.findViewById(R.id.ChampName);
        textViewcoast = view.findViewById(R.id.ChampCoast);
        textViewclass = view.findViewById(R.id.ChampClass);
        Type ListType = new TypeToken<Champion>() {
        }.getType();

        String url = intent.getStringExtra("url");
        champion = Singletons.getGson().fromJson(intent.getStringExtra("Champ"), ListType);

        if(champion != null) {
            String strcoast = "Coast: " + champion.getCost();
            String strclass = "Class or origin: " + champion.getTraitsToString();
            setItems(url, champion.getName(), strcoast, strclass);
        }
        onBackButtonClick();
        onAddButtonClick();
    }

    private void setItems(String url, String champname, String strcoast, String strclass){
        Picasso.get().load(url).into(imageView);
        textViewname.setText(champname);
        textViewcoast.setText(strcoast);
        textViewclass.setText(strclass);
    }

    private void onBackButtonClick(){
        button_return = view.findViewById(R.id.button_Desc_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.finish();
            }
        });
    }

    private void onAddButtonClick(){
        button_ajt = (Button) view.findViewById(R.id.Add_Button);
        button_ajt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChamp(champion);
                view.finish();
            }
        });
    }

    private void addChamp(Champion champ) {
        List<Champion> teamList = getTeamListFromCache();
        if(teamList == null){
            teamList = creacteList(champ);
        }
        if(!verifteam(champ, teamList)){
            if(teamList.size() < 9) {
                teamList.add(champ);
                Toast.makeText(view.getApplicationContext(), "added", Toast.LENGTH_SHORT).show();

                String jsonTeamList = Singletons.getGson().toJson(teamList);
                Singletons.getSharedPreferences(view.getApplicationContext())
                        .edit()
                        .putString(Constants.KEY_TEAM_LIST, jsonTeamList)
                        .apply();
            }else{
                Toast.makeText(view.getApplicationContext(), "Max reached", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(view.getApplicationContext(), "Is already on your team", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verifteam(Champion champ, List<Champion> teamList){
        for (int i = 0; i < teamList.size(); i++) {
            if(teamList.get(i).getName().equals(champ.getName())){
                return true;
            }
        }
        return false;
    }

    private List<Champion> creacteList(Champion champ) {
        List<Champion> teamList = new ArrayList<>();
        return teamList;
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
}
