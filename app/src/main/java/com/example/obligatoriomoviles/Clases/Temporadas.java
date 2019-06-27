package com.example.obligatoriomoviles.Clases;

import com.google.gson.annotations.SerializedName;

public class Temporadas {
    @SerializedName("season_number")
    private int temporada;
    @SerializedName("episode_count")
    private int capitulos;
    @SerializedName("poster_path")
    private String imagen;

    public String getImagen() {
        return imagen;
    }

    public int getTemporada() {
        return temporada;
    }

    public int getCapitulos() {
        return capitulos;
    }
}
