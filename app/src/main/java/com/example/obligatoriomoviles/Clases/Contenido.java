package com.example.obligatoriomoviles.Clases;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public abstract class Contenido {
    @SerializedName("id")
    private String id;
    @SerializedName("titulo")
    private String titulo;
    @SerializedName("fecha")
    private Date fecha;
    @SerializedName("generos")
    private List<String> generos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public abstract void GenerarNotificaciones();
}
