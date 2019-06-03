package com.example.obligatoriomoviles.Clases;

import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class retorno {
    @SerializedName("correo")
    private String correo;
    @SerializedName("pass")
    private String pass;
    @SerializedName("retorno")
    private boolean retorno;
    @SerializedName("results")
    private List<Cine> lista_elementos;
    @SerializedName("Comentarios")
    private List<Comentario> lista_comentarios;

    public Boolean getRetorno() {
        return retorno;
    }
    public List<Comentario> getLista_comentarios() {
        return lista_comentarios;
    }
    public List<Cine> getLista_elementos() {
        return lista_elementos;
    }
    public retorno(String correo, String pass) {
        this.correo = correo;
        this.pass = pass;
    }


}
