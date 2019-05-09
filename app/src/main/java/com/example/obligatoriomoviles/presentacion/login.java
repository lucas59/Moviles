package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.obligatoriomoviles.R;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(3).setChecked(true);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void crearNuevaCuenta(View view){
        Intent intent = new Intent(this, NuevoUsuarioActivity.class);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(login.this, Menu_principal.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_buscar:
                    i = new Intent(login.this, Perfil_elemento.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_perfil:
                    return true;
                case R.id.navigation_sesion:
                    i = new Intent(login.this, login.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_registrarse:
                    i = new Intent(login.this, NuevoUsuarioActivity.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };
}
