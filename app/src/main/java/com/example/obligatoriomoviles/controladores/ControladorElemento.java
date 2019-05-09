package com.example.obligatoriomoviles.controladores;

import com.example.obligatoriomoviles.interfaces.IElemento;

public class ControladorElemento{
    private static final ControladorElemento ourInstance = new ControladorElemento();

    public static ControladorElemento getInstance() {
        return ourInstance;
    }

    public ControladorElemento() {
    }
}
