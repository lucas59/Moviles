package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.obligatoriomoviles.Clases.Cine;
import com.example.obligatoriomoviles.Clases.Adapters.Fragment_adapter;
import com.example.obligatoriomoviles.Clases.Fragments.Peliculas_fragment;
import com.example.obligatoriomoviles.Clases.Fragments.Series_fragment;
import com.example.obligatoriomoviles.R;

import java.util.ArrayList;
import java.util.List;

public class Calendario_elementos extends AppCompatActivity {

    //lista de peliculas
    List<Cine> lista_peliculas;

    //el recyclerview
    private Fragment_adapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_elementos);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new Fragment_adapter(getSupportFragmentManager());
        adapter.addFragment(new Peliculas_fragment(), "Peliculas");
        adapter.addFragment(new Series_fragment(), "Series");

        viewPager.setAdapter(adapter);
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
        tabLayout.setupWithViewPager(viewPager);



        lista_peliculas = new ArrayList<>();

        //Menu
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().getItem(1).setChecked(true);
        getSupportActionBar().setTitle("MTV Show");

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(getApplicationContext(), Menu_principal.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.infade, R.anim.outfade);
                    return true;
                case R.id.navigation_notificacion:
                    //     i = new Intent(Menu_principal.this, Perfil_elemento.class);
                    //     startActivity(i);
                    //finish();
                    return true;
                case R.id.navigation_calendario:
                    return true;

            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        switch (item.getItemId()) {
            case R.id.perfil:
                Intent i = new Intent(getApplicationContext(), Perfil_usuario.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.infade, R.anim.outfade);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}