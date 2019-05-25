package com.example.obligatoriomoviles.presentacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIError;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.Clases.Cine.Cine_adapter;
import com.example.obligatoriomoviles.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Calendario_elementos extends AppCompatActivity {

    //lista de peliculas
    List<Cine> lista_peliculas;

    //el recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendario_elementos);

        //obtener recyclerview de xml
        recyclerView = (RecyclerView) findViewById(R.id.Lista);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


       lista_peliculas = new ArrayList<>();

        //Llamado de la API para que retorne el json de la consulta
        APIInterface apiService = APICliente.getCalendario().create(APIInterface.class);
        Call<Cine> call = apiService.getImagen("popularity.desc",2019,"en-US","0d81ceeb977ab515fd9f844377688c5a");
        //Menu
        BottomNavigationView navView = findViewById(R.id.nav_view);
        final ProgressBar spinner;
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        //Setear el focus a la opcion correspondiente (del 0 al numero de botones)
           navView.getMenu().getItem(2).setChecked(true);
        call.enqueue(new Callback<Cine>() {
            @Override
            public void onResponse(Call<Cine> call, Response<Cine> response) {
                spinner.setVisibility(View.VISIBLE);
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

                for (Cine post : response.body().getData()) {
                    lista_peliculas.add(new Cine(
                            post.getOriginal_title(), post.getNota(), post.getPoster_path(), post.getId()
                    ));
                }

                //creando adapter recyclerview
                Cine_adapter adapter = new Cine_adapter(Calendario_elementos.this, lista_peliculas);
               adapter.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       //Enviar al la actividad de perfil elemento el ID
                      Intent i = new Intent(Calendario_elementos.this, Perfil_elemento.class);
                       i.putExtra("id", lista_peliculas.get(recyclerView.getChildAdapterPosition(v)).getId());
                       i.putExtra("fecha", lista_peliculas.get(recyclerView.getChildAdapterPosition(v)).getFecha());
                       i.putExtra("genero", lista_peliculas.get(recyclerView.getChildAdapterPosition(v)).getPoster_path());
                       i.putExtra("titulo_elemento", lista_peliculas.get(recyclerView.getChildAdapterPosition(v)).getOriginal_title());
                       startActivity(i);
                   }
               });

                //setear adapter al recyclerview
                recyclerView.setAdapter(adapter);
                spinner.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Cine> call, Throwable t) {

                Log.d("LoginActivity", t.getMessage() + t.getStackTrace().toString());
            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(getApplicationContext(), Menu_principal.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;
                case R.id.navigation_buscar:
                    //     i = new Intent(Menu_principal.this, Perfil_elemento.class);
                    //     startActivity(i);
                    return true;
                case R.id.navigation_perfil:
                    i = new Intent(getApplicationContext(), Calendario_elementos.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;
                case R.id.navigation_sesion:
                    i = new Intent(getApplicationContext(), login.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;
                case R.id.navigation_registrarse:
                    i = new Intent(getApplicationContext(), NuevoUsuarioActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;

            }
            return false;
        }
    };

}