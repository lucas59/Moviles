package com.example.obligatoriomoviles.controladores;

import com.example.obligatoriomoviles.interfaces.IUsuario;

public class ControladorUsuario implements IUsuario {
    private static final ControladorUsuario ourInstance = new ControladorUsuario();

    public static ControladorUsuario getInstance() {
        return ourInstance;
    }

    private ControladorUsuario() {
    }
}
