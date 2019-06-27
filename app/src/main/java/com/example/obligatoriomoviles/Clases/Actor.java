package com.example.obligatoriomoviles.Clases;

import com.google.gson.annotations.SerializedName;

public class Actor {
    @SerializedName("name")
    String nombre;
    @SerializedName("character")
    String personaje;
    @SerializedName("profile_path")
    String imagen;

    public Actor(String nombre, String personaje, String imagen) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPersonaje() {
        return personaje;
    }

    public String getImagen() {
        return imagen;
    }
}
