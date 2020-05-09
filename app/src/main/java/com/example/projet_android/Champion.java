package com.example.projet_android;

import java.util.List;

public class Champion {

    private String name;
    private Integer cost;
    private String icon;
    private List<String> traits;

    public String getName() {
        return name;
    }

    public Integer getCost() {
        return cost;
    }

    public List<String> getTraits() {
        return traits;
    }

    public String getIcon() { return icon; }

    public String getTraitsToString() {
        String classes = traits.get(0);

        for (int i = 1; i < traits.size(); i++) {
            classes += ", " + traits.get(i);
        }

        return classes;
    }
}
