package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Adapters.generalAdapters;
import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_principal extends AppCompatActivity {
    private TextView mTextMessage;
    private RecyclerView listaPeliculasPolulares;
    private List<Cine> listPeliculasPolulares;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(getApplicationContext(), Menu_principal.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    finish();
                    return true;
                case R.id.navigation_notificacion:
              //     i = new Intent(Menu_principal.this, Perfil_elemento.class);
               //     startActivity(i);
                    return true;
                case R.id.navigation_calendario:
                    i = new Intent(getApplicationContext(), Calendario_elementos.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    finish();
                    return true;

            }
            return false;
        }
    };

    private long backPressedTime;

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getApplicationContext(), "Vuelve a presionar el botón atrás para salir", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportActionBar().setTitle("IView");
        listaPeliculasPolulares=findViewById(R.id.Actores_lista);
        listPeliculasPolulares= new ArrayList<>();
        listaPeliculasPolulares.setHasFixedSize(true);
        listaPeliculasPolulares.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView myList = (RecyclerView) findViewById(R.id.Actores_lista);
        myList.setLayoutManager(layoutManager);

        //**********************************************************************

        APIInterface apiService = APICliente.getPelicula().create(APIInterface.class);
        Call<Cine> call = apiService.getPeliculaPolulares("0d81ceeb977ab515fd9f844377688c5a", "es");

        call.enqueue(new Callback<Cine>() {
            public void onResponse(Call<Cine> call, Response<Cine> response) {
                for (Cine post : response.body().getData()) {
                    listPeliculasPolulares.add(new Cine(post.getOriginal_title(), post.getNota(), post.getPoster_path(), post.getId(),post.getFecha()
                ));
            }
                generalAdapters adapter = new generalAdapters(getApplicationContext(), listPeliculasPolulares);
                listaPeliculasPolulares.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<Cine> call, Throwable t) {

            }
        });
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
                overridePendingTransition(R.anim.infade,R.anim.outfade);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }
}
