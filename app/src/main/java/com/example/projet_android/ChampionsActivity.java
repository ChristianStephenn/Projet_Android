package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ChampionsActivity extends AppCompatActivity {
    private Gson gson;
    private RecyclerView recyclerView;
    private ListAdapterChamp mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champions);
        gson = new GsonBuilder()
                .setLenient()
                .create();
        Type ListType = new TypeToken<List<Champion>>() {
        }.getType();

        Intent champ = getIntent();

        String activity_title = champ.getStringExtra("className");
        setTitle(activity_title);

        textView = (TextView) findViewById(R.id.DescClass);
        String Desc = champ.getStringExtra("Desc");
        textView.setText(Desc);

        String jsonList = champ.getStringExtra("champList");
        List<Champion> champList = gson.fromJson(jsonList, ListType);
        showChampList(champList);

        Button button_return = (Button) findViewById(R.id.button_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showChampList(List<Champion> champList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_champ);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapterChamp(champList, getBaseContext());
        recyclerView.setAdapter(mAdapter);
    }
}
