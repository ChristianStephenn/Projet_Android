package com.example.projet_android.presentation.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_android.R;
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

    public void showChampList(List<Champion> champList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_champ);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapterChamp(champList, getBaseContext());
        recyclerView.setAdapter(mAdapter);
    }
}
