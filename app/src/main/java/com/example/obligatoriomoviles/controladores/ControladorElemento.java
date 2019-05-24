package com.example.obligatoriomoviles.controladores;

import com.example.obligatoriomoviles.Clases.Cine.Cine;

public class ControladorElemento{
    private static final ControladorElemento ourInstance = new ControladorElemento();

    String Nombre_actor;

    public void setNombre_actor(String nombre_actor) {
        Nombre_actor = nombre_actor;
    }

    public void setFoto_perfil_actor(String foto_perfil_actor) {
        this.foto_perfil_actor = foto_perfil_actor;
    }

    public String getNombre_actor() {
        return Nombre_actor;
    }

    public String getFoto_perfil_actor() {
        return foto_perfil_actor;
    }

    String foto_perfil_actor;

    public static ControladorElemento getInstance() {
        return ourInstance;
    }

    public ControladorElemento() {
    }

    public void enviarNotificaciones(Cine pelicula) {
	}


    public boolean Comentar(String comentario, String titulo, Integer capitulo, Integer contenido,String correo){

    return true;
    }

}
