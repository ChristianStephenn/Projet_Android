package com.example.projet_android.presentation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projet_android.R;
import com.squareup.picasso.Picasso;

public class DescriptionChampActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textViewname;
    private TextView textViewcoast;
    private TextView textViewclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_champ);

        imageView = (ImageView) findViewById(R.id.DescChampIcon);
        textViewname = (TextView) findViewById(R.id.ChampName);
        textViewcoast = (TextView) findViewById(R.id.ChampCoast);
        textViewclass = (TextView) findViewById(R.id.ChampClass);

        Intent champ = getIntent();

        String url = champ.getStringExtra("url");
        Picasso.get().load(url).into(imageView);

        String champname = champ.getStringExtra("ChampName");
        textViewname.setText(champname);

        Integer champcoast = champ.getIntExtra("ChampCoast", 0);
        String strcoast = "Coast: " + champcoast.toString();
        textViewcoast.setText(strcoast);

        String champclass = champ.getStringExtra("ChampClass");
        String strclass = "Class or origin: " + champclass;
        textViewclass.setText(strclass);

        Button button_return = (Button) findViewById(R.id.button_Desc_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
