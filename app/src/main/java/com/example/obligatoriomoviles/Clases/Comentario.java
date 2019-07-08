package com.example.obligatoriomoviles.Clases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Comentario {

    @SerializedName("id")
    private Integer id;
    @SerializedName("texto")
    private String texto;
    @SerializedName("titulo")
    private String titulo;
    @SerializedName("capitulo_id")
    private String capitulo_id;
    @SerializedName("contenido_id")
    private String contenido_id;
    @SerializedName("usuario_correo")
    private String usuario_correo;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("puntuaciones")
    private List<retorno> puntuaciones;
    @SerializedName("puntuacion")
    private Float puntuacion;
    @SerializedName("total")
    private Float total_puntuaciones;
    private String foto;

    public Float getTotal_puntuaciones() {
        return total_puntuaciones;
    }
    public Comentario(String texto, String usuario_correo, Integer id2,Float puntaje) {
        this.texto = texto;
        this.usuario_correo = usuario_correo;
        this.id=id2;
        this.puntuacion = puntaje;
    }

    public Comentario(){

    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public List<retorno> getPuntuaciones() {
        return puntuaciones;
    }

    public Float getPuntuacion() {
        return puntuacion;
    }
    public Integer getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCapitulo_id() {
        return capitulo_id;
    }

    public String getContenido_id() {
        return contenido_id;
    }

    public String getUsuario_correo() {
        return usuario_correo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCapitulo_id(String capitulo_id) {
        this.capitulo_id = capitulo_id;
    }

    public void setContenido_id(String contenido_id) {
        this.contenido_id = contenido_id;
    }

    public void setUsuario_correo(String usuario_correo) {
        this.usuario_correo = usuario_correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntuaciones(List<retorno> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }

    public void setPuntuacion(Float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setTotal_puntuaciones(Float total_puntuaciones) {
        this.total_puntuaciones = total_puntuaciones;
    }
}
