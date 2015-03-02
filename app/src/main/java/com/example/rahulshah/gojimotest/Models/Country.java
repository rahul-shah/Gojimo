package com.example.rahulshah.gojimotest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country
{
    @SerializedName("code")
    @Expose
    private String code;

    public String getcode()
    {
        return code;
    }

    @SerializedName("name")
    @Expose
    private String name;

    public String getname()
    {
        return name;
    }
}