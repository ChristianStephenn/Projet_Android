package com.example.projet_android.presentation.model;

import com.example.projet_android.presentation.model.Champion;

import java.util.List;

public class ClasseEtOrigine {

    private String name;
    private String description;
    private String icon;
    private List<Champion> champions;

    public String getName() {
        return  name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() { return icon; }

    public List<Champion> getChampions() {
        return champions;
    }

}
