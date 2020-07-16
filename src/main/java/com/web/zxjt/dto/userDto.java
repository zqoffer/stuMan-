package com.web.zxjt.dto;


import java.io.Serializable;

public class userDto implements Serializable {
    public Integer id;
    public String name;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
