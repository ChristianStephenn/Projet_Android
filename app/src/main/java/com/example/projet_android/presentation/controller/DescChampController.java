package com.example.projet_android.presentation.controller;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projet_android.R;
import com.example.projet_android.presentation.Singletons;
import com.example.projet_android.presentation.model.Champion;
import com.example.projet_android.presentation.view.DescriptionChampActivity;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

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

        imageView = (ImageView) view.findViewById(R.id.DescChampIcon);
        textViewname = (TextView) view.findViewById(R.id.ChampName);
        textViewcoast = (TextView) view.findViewById(R.id.ChampCoast);
        textViewclass = (TextView) view.findViewById(R.id.ChampClass);
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
        //onAddButtonClick();
    }

    private void setItems(String url, String champname, String strcoast, String strclass){
        Picasso.get().load(url).into(imageView);
        textViewname.setText(champname);
        textViewcoast.setText(strcoast);
        textViewclass.setText(strclass);
    }

    private void onBackButtonClick(){
        button_return = (Button) view.findViewById(R.id.button_Desc_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.finish();
            }
        });
    }

    /*private void onAddButtonClick(){
        final String champ = Singletons.getGson().toJson(champion);
        button_ajt = (Button) view.findViewById(R.id.Add_Button);
        button_ajt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retour = new Intent();
                retour.putExtra("champion", champ);
                view.setResult(1,retour);
                view.finish();
            }
        });
    }*/
}
