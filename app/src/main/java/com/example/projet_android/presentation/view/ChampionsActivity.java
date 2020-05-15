package com.example.projet_android.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_android.R;
import com.example.projet_android.presentation.Singletons;
import com.example.projet_android.presentation.controller.ChampController;
import com.example.projet_android.presentation.model.Champion;

import java.util.List;

public class ChampionsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListAdapterChamp mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ChampController champController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champions);

        champController = new ChampController(this, getIntent());
        champController.onStart();
        champController.onBackButtonClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if( resultCode==1 ) {
            String s ="new " + data.getStringExtra("champion");
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void showChampList(List<Champion> champList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_champ);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ListAdapterChamp(champList, new ListAdapterChamp.OnItemClickListenerChamp() {
            @Override
            public void onItemClick(Champion item, String url) {
                champController.onItemClick(item, url);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void navigateToDetails(Champion champion, String url) {
        final String jsonChamp = Singletons.getGson().toJson(champion);
        Intent DescChamp = new Intent(ChampionsActivity.this, DescriptionChampActivity.class);
        DescChamp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        DescChamp.putExtra("url", url);
        DescChamp.putExtra("Champ", jsonChamp);
        ChampionsActivity.this.startActivity(DescChamp);
    }
}
