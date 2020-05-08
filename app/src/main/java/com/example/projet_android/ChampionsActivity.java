package com.example.projet_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ChampionsActivity extends AppCompatActivity {
    private Gson gson;
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champions);

        gson = new GsonBuilder()
                .setLenient()
                .create();
        Type ListType = new TypeToken<List<ClasseEtOrigine>>() {
        }.getType();

        TextView textView = (TextView) findViewById(R.id.textView);
        Intent champ = getIntent();
        String jsonList = champ.getStringExtra("champList");
        List<Champion> champList = gson.fromJson(jsonList, ListType);
    }

    private void showList(List<Champion> classList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(classList, getBaseContext());
        recyclerView.setAdapter(mAdapter);
    }
}
