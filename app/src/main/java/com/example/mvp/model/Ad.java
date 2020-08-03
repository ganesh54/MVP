package com.example.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ad implements Serializable {
    @SerializedName("company")
    @Expose
    public String company;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("text")
    @Expose
    public String text;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
