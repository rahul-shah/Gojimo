package com.example.rahulshah.gojimotest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subjects
{
    @SerializedName("id")
    @Expose
    private String Id;

    public String getId()
    {
        return Id;
    }

    @SerializedName("title")
    @Expose
    private String title;

    public String gettitle()
    {
        return title;
    }

    @SerializedName("colour")
    @Expose
    private String colour;

    public String getcolour()
    {
        return colour;
    }
}