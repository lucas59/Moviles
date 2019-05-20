package com.example.obligatoriomoviles.Clases;

import com.google.gson.annotations.SerializedName;

public class Retorno {
    @SerializedName("correo")
    private String correo;
    @SerializedName("pass")
    private String pass;

    public Boolean getRetorno() {
        return retorno;
    }

    @SerializedName("retorno")
    private boolean retorno;
    public Retorno(String correo, String pass) {
        this.correo = correo;
        this.pass = pass;
    }


}
