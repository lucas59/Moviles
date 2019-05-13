package com.example.obligatoriomoviles.Clases;

import java.util.Date;

public class Notificaciones {

    private Date fechaN;
    private String asunto;
    private String direccion;


    public Notificaciones(Date fechaN, String asunto, String direccion) {
        this.fechaN = fechaN;
        this.asunto = asunto;
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaN() {
        return fechaN;
    }

    public void setFechaN(Date fechaN) {
        this.fechaN = fechaN;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
}
