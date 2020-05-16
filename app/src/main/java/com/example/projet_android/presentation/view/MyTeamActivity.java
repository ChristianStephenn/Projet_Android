package com.example.projet_android.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.projet_android.R;
import com.example.projet_android.presentation.controller.MyTeamController;
import com.example.projet_android.presentation.model.Champion;

import java.util.List;

public class MyTeamActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapterChamp mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MyTeamController myTeamController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);

        myTeamController = new MyTeamController(this, getIntent());
        myTeamController.onStart();
    }

    public void showTeamList(List<Champion> champList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_team);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ListAdapterChamp(champList, new ListAdapterChamp.OnItemClickListenerChamp() {
            @Override
            public void onItemClick(Champion item, String url) {
                //myTeamController.onItemClick(item, url);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

}
