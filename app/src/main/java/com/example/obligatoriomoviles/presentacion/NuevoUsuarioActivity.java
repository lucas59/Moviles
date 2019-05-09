package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
        BottomNavigationView navView = findViewById(R.id.nav_view);
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(4).setChecked(true);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void iniciar(View view){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void NuevoUsuario(View view ){
        String email = txtEmail.getText().toString();
        String pass = txtPass.getText().toString();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(NuevoUsuarioActivity.this, Menu_principal.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_buscar:
                    i = new Intent(NuevoUsuarioActivity.this, Perfil_elemento.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_perfil:
                    return true;
                case R.id.navigation_sesion:
                    i = new Intent(NuevoUsuarioActivity.this, login.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_registrarse:
                    i = new Intent(NuevoUsuarioActivity.this, NuevoUsuarioActivity.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };

}
