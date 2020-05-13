package com.example.projet_android.presentation.controller;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projet_android.R;
import com.example.projet_android.presentation.Singletons;
import com.example.projet_android.presentation.model.Champion;
import com.example.projet_android.presentation.view.ChampionsActivity;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ChampController {

    private TextView textView;
    private Intent intent;
    private ChampionsActivity view;
    private Button button_return;

    public ChampController(ChampionsActivity view, Intent intent){
        this.view = view;
        this.intent = intent;
    }

    public void onStart(){

        Type ListType = new TypeToken<List<Champion>>() {
        }.getType();

        String activity_title = intent.getStringExtra("className");
        view.setTitle(activity_title);

        textView = (TextView) view.findViewById(R.id.DescClass);
        String Desc = intent.getStringExtra("Desc");
        textView.setText(Desc);

        String jsonList = intent.getStringExtra("champList");
        List<Champion> champList = Singletons.getGson().fromJson(jsonList, ListType);
        view.showChampList(champList);

    }

    public void onBackButtonClick(){

        button_return = (Button) view.findViewById(R.id.button_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.finish();
            }
        });
    }
}
