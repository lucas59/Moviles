package com.example.obligatoriomoviles.controladores;

import com.example.obligatoriomoviles.Clases.Peliculas;

public class ControladorElemento{
    private static final ControladorElemento ourInstance = new ControladorElemento();

    public static ControladorElemento getInstance() {
        return ourInstance;
    }

    public ControladorElemento() {
    }

    public void enviarNotificaciones(Peliculas pelicula) {


    }
}
