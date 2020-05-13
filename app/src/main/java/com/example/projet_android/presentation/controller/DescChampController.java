package com.example.projet_android.presentation.controller;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projet_android.R;
import com.example.projet_android.presentation.view.DescriptionChampActivity;
import com.squareup.picasso.Picasso;

public class DescChampController {

    private DescriptionChampActivity view;
    private Intent intent;
    private ImageView imageView;
    private TextView textViewname;
    private TextView textViewcoast;
    private TextView textViewclass;
    private Button button_return;

    public DescChampController(DescriptionChampActivity view, Intent intent) {
        this.view = view;
        this.intent = intent;
    }

    public void onStart(){

        imageView = (ImageView) view.findViewById(R.id.DescChampIcon);
        textViewname = (TextView) view.findViewById(R.id.ChampName);
        textViewcoast = (TextView) view.findViewById(R.id.ChampCoast);
        textViewclass = (TextView) view.findViewById(R.id.ChampClass);

        String url = intent.getStringExtra("url");

        String champname = intent.getStringExtra("ChampName");

        Integer champcoast = intent.getIntExtra("ChampCoast", 0);
        String strcoast = "Coast: " + champcoast.toString();

        String champclass = intent.getStringExtra("ChampClass");
        String strclass = "Class or origin: " + champclass;

        setItems(url, champname, strcoast, strclass);
    }

    private void setItems(String url, String champname, String strcoast, String strclass){
        Picasso.get().load(url).into(imageView);
        textViewname.setText(champname);
        textViewcoast.setText(strcoast);
        textViewclass.setText(strclass);
    }

    public void onBackButtonClick(){

        button_return = (Button) view.findViewById(R.id.button_Desc_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.finish();
            }
        });
    }
}
