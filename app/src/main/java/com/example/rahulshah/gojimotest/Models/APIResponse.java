package com.example.rahulshah.gojimotest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class APIResponse
{
    @SerializedName("id")
    @Expose
    private String Id;

    public String getId()
    {
        return Id;
    }

    @SerializedName("name")
    @Expose
    private String name;

    public String getName()
    {
        return name;
    }

    @SerializedName("country")
    @Expose
    private Country country;

    public Country getcountry()
    {
        return country;
    }

    @SerializedName("subjects")
    @Expose
    private ArrayList<Subjects> subjects;

    public ArrayList<Subjects> getsubjects()
    {
        return subjects;
    }
}