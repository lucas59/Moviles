package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.obligatoriomoviles.R;

public class Menu_principal extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(0).setChecked(true);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(Menu_principal.this, Menu_principal.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_buscar:
                   i = new Intent(Menu_principal.this, Perfil_elemento.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_perfil:
                    return true;
                case R.id.navigation_sesion:
                    i = new Intent(Menu_principal.this, login.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_registrarse:
                    i = new Intent(Menu_principal.this, NuevoUsuarioActivity.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
