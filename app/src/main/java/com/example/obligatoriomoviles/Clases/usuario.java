package com.example.obligatoriomoviles.Clases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class usuario {

    @SerializedName("id")
    private int id;

    @SerializedName("numero")
    private Integer numerosContenido;

    @SerializedName("foto")
    private String foto;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("apellido")
    private String apellido;

    @SerializedName("correo")
    private String correo;

    @SerializedName("edad")
    private int edad;

    @SerializedName("Numero_contenido")
    private Integer nContenido;

    @SerializedName("Numero_comentario")
    private int Numero_comentario;

    @SerializedName("notificaciones")
    private List<Notificaciones> notificaciones;

    @SerializedName("contrasenia")
    private String contrasenia;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getnContenido() {
        return nContenido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getNumero_comentario() {
        return Numero_comentario;
    }

    public Integer getNumerosContenido() {
        return numerosContenido;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public List<Notificaciones> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificaciones> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public String getFoto() {
        return foto;
    }

}
