package com.example.obligatoriomoviles.Clases.Cine;

import com.google.gson.annotations.SerializedName;

public class Capitulo {

    @SerializedName("name")
    private String nombre;
    @SerializedName("overview")
    private String sinopsis;
    @SerializedName("still_path")
    private String Imagen;

    public String getNombre() {
        return nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public String getImagen() {
        return Imagen;
    }
}
