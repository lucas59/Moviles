package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIError;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Cine.Actor;
import com.example.obligatoriomoviles.Clases.Cine.Actor_adapter;
import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil_elemento extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_elemento);
        //Llamado de la API para que retorne el json de la consulta
        APIInterface apiService = APICliente.getPelicula().create(APIInterface.class);
        Call<Cine> call = apiService.getPelicula( getIntent().getExtras().getString("id"),"0d81ceeb977ab515fd9f844377688c5a","credits","es");

        recyclerView = (RecyclerView) findViewById(R.id.Actores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Menu
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        //Setear el focus a la opcion correspondiente (del 0 al numero de botones)
        navigationView.getMenu().getItem(2).setChecked(true);
        //setear el menu de navegación de abajo
        BottomNavigationView navView = findViewById(R.id.nav_view);
        call.enqueue(new Callback<Cine>() {
            @Override
            public void onResponse(Call<Cine> call, Response<Cine> response) {
                if (!response.isSuccessful()) {
                    String error = "Ha ocurrido un error. Contacte al administrador";
                    if (response.errorBody()
                            .contentType()
                            .subtype()
                            .equals("json")) {
                        APIError apiError = APIError.fromResponseBody(response.errorBody());
                        error = apiError.getMessage();
                        Log.d("LoginActivity", apiError.getDeveloperMessage());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API
                            Log.d("LoginActivity", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    return;
                }


                JsonObject creditos=response.body().getCreditos();
                JsonArray casts=creditos.get("cast").getAsJsonArray();
                List<Actor> actores = new ArrayList<>();
                if (casts != null) {
                    int len = casts.size();
                    for (int i=0;i<len;i++){
                        actores.add(new Actor(casts.get(i).getAsJsonObject().get("name").toString(),casts.get(i).getAsJsonObject().get("character").toString(),casts.get(i).getAsJsonObject().get("profile_path").toString() ));
                    }
                }
                Actor_adapter adapter = new Actor_adapter(Perfil_elemento.this,actores);
                recyclerView.setAdapter(adapter);
                //Setear fondo y poster del elemento
                String fondo = "https://image.tmdb.org/t/p/w500" + response.body().getBackdrop_path();
                String poster = "https://image.tmdb.org/t/p/w500" + response.body().getPoster_path();
                LinearLayoutManager layoutManager = new LinearLayoutManager(Perfil_elemento.this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView myList = (RecyclerView) findViewById(R.id.Actores);
                myList.setLayoutManager(layoutManager);
                //traer el ImageView y el textview del layout
                 ImageView fondo_view = (ImageView) findViewById(R.id.imagen_fondo);
                 ImageView poster_view = (ImageView) findViewById(R.id.imagen_poster);
                 TextView fecha = (TextView) findViewById(R.id.fecha);
                TextView titulo = (TextView) findViewById(R.id.titulo);
                TextView sinopsis = (TextView) findViewById(R.id.sinopsis);
                TextView valor = (TextView) findViewById(R.id.valor);
                ProgressBar votos = (ProgressBar) findViewById(R.id.votos);
                //Setear información en los elementos
                titulo.setText(response.body().getOriginal_title());
                Picasso.get().load(fondo).fit().centerCrop().into(fondo_view);
                Picasso.get().load(poster).into(poster_view);
                fecha.setText("Estreno: " + response.body().getFecha());
                sinopsis.setText(response.body().getSinopsis());
                votos.setProgress((int) Float.parseFloat(response.body().getNota()));
                valor.setText(response.body().getNota());


            }

            @Override
            public void onFailure(Call<Cine> call, Throwable t) {

            }



        });

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(Perfil_elemento.this, Menu_principal.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;
                case R.id.navigation_buscar:
                    i = new Intent(Perfil_elemento.this, Perfil_elemento.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;
                case R.id.navigation_perfil:
                    i = new Intent(Perfil_elemento.this, Calendario_elementos.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;
                case R.id.navigation_sesion:
                    i = new Intent(Perfil_elemento.this, login.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;
                case R.id.navigation_registrarse:
                    i = new Intent(Perfil_elemento.this, NuevoUsuarioActivity.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };
}
