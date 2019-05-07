package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.obligatoriomoviles.R;

public class NuevoUsuarioActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        this.txtEmail=findViewById(R.id.txtEmail);
        this.txtPass=findViewById(R.id.txtPass);
    }

    public void iniciar(View view){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void NuevoUsuario(View view ){
        String email = txtEmail.getText().toString();
        String pass = txtPass.getText().toString();

    }


}
