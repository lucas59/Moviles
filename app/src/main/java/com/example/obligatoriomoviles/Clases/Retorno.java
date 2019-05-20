package com.example.obligatoriomoviles.Clases;

import com.google.gson.annotations.SerializedName;

public class retorno {
    @SerializedName("correo")
    private String correo;
    @SerializedName("pass")
    private String pass;
    @SerializedName("retorno")
    private boolean retorno;

    public Boolean getRetorno() {
        return retorno;
    }

    public retorno(String correo, String pass) {
        this.correo = correo;
        this.pass = pass;
    }


}
