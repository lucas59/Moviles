package com.example.obligatoriomoviles.Clases;

import com.google.gson.annotations.SerializedName;

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

    public Comentario(String texto, String usuario_correo, Integer id2) {
        this.texto = texto;
        this.usuario_correo = usuario_correo;
        this.id=id2;
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


}
