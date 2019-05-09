package com.example.obligatoriomoviles.Clases;


import com.google.gson.annotations.SerializedName;

public class Peliculas {
    @SerializedName("original_title")
    private String original_title;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("poster_path")
    private String poster_path;
    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }
}
