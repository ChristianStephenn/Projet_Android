package com.example.projet_android.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_android.R;
import com.example.projet_android.presentation.Singletons;
import com.example.projet_android.presentation.controller.MainController;
import com.example.projet_android.presentation.model.Champion;
import com.example.projet_android.presentation.model.ClasseEtOrigine;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController(this, Singletons.getGson(), Singletons.getSharedPreferences(getApplicationContext()));
        controller.onStart();
    }

    public void showList(List<ClasseEtOrigine> classList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(classList, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ClasseEtOrigine item) {
                controller.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    public void navigateToDetails(ClasseEtOrigine classe) {
        List<Champion> champList = classe.getChampions();
        String jsonList = Singletons.getGson().toJson(champList);
        Intent champ = new Intent(MainActivity.this, ChampionsActivity.class);
        champ.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        champ.putExtra("className", classe.getName());
        champ.putExtra("Desc",classe.getDescription());
        champ.putExtra("champList",jsonList);
        MainActivity.this.startActivity(champ);
    }
}
