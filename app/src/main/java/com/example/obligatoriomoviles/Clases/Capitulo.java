package com.example.obligatoriomoviles.Clases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Capitulo {

    @SerializedName("name")
    private String nombre;
    @SerializedName("overview")
    private String sinopsis;
    @SerializedName("still_path")
    private String Imagen;
    @SerializedName("id")
    private Integer id;
    @SerializedName("vote_average")
    private String nota;
    @SerializedName("guest_stars")
    private List<Actor> Actores;
    @SerializedName("air_date")
    private String fecha;

    public String getFecha() {
        return fecha;
    }

    public List<Actor> getActores() {
        return Actores;
    }

    public String getNota() {
        return nota;
    }

    public Integer getId() {
        return id;
    }

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
