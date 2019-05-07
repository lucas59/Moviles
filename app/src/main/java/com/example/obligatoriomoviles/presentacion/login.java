package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.obligatoriomoviles.R;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void crearNuevaCuenta(View view){
        Intent intent = new Intent(this, NuevoUsuarioActivity.class);
        startActivity(intent);
    }
}
