package com.ddr.logic;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Airport implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Airport(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
