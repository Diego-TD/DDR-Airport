package com.ddr.logic;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
