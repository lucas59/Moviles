package com.example.obligatoriomoviles.Clases;

public class DatosPerfilElemento{
public static String id;
    public static String  fecha;
    public static String poster;
    public static String originalTitle;

    public static String getPuntuacion() {
        return puntuacion;
    }

    public static void setPuntuacion(String puntuacion) {
        DatosPerfilElemento.puntuacion = puntuacion;
    }

    public static String puntuacion;

    public DatosPerfilElemento() {
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        DatosPerfilElemento.id = id;
    }

    public static String getFecha() {
        return fecha;
    }

    public static void setFecha(String fecha) {
        DatosPerfilElemento.fecha = fecha;
    }

    public static String getPoster() {
        return poster;
    }

    public static void setPoster(String poster) {
        DatosPerfilElemento.poster = poster;
    }

    public static String getOriginalTitle() {
        return originalTitle;
    }

    public static void setOriginalTitle(String originalTitle) {
        DatosPerfilElemento.originalTitle = originalTitle;
    }
}
