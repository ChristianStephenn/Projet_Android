package com.example.projet_android.presentation.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_android.R;
import com.example.projet_android.presentation.controller.DescChampController;

public class DescriptionChampActivity extends AppCompatActivity {

    private DescChampController descChampController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_champ);

        descChampController = new DescChampController(this, getIntent());
        descChampController.onStart();
        descChampController.onBackButtonClick();

    }
}
