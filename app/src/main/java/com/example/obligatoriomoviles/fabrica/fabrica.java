package com.example.obligatoriomoviles.fabrica;

import com.example.obligatoriomoviles.controladores.ControladorElemento;
import com.example.obligatoriomoviles.controladores.ControladorUsuario;
import com.example.obligatoriomoviles.interfaces.IElemento;
import com.example.obligatoriomoviles.interfaces.IUsuario;

public class fabrica {
    private static final fabrica ourInstance = new fabrica();

    public static fabrica getInstance() {
        return ourInstance;
    }

    private fabrica() {
    }

    public IUsuario getUsuario_Interface(){
        IUsuario controladorUsuario = ControladorUsuario.getInstance();
        return controladorUsuario;
    }
    public IElemento getElemento_Interface() {
        // IElemento controladorElemento = ControladorElemento.getInstance();
    // return controladorElemento;
        return null;
    }
}
