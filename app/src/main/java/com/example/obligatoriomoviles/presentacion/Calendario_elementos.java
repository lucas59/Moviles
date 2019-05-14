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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIError;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Peliculas;
import com.example.obligatoriomoviles.Clases.Peliculas_adapter;
import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Calendario_elementos extends AppCompatActivity {

    //lista de peliculas
    List<Peliculas> lista_peliculas;

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
        Call<Peliculas> call = apiService.getImagen("popularity.desc",2019,"en-US","0d81ceeb977ab515fd9f844377688c5a");
        //Menu
          BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        //Setear el focus a la opcion correspondiente (del 0 al numero de botones)
           navigationView.getMenu().getItem(2).setChecked(true);
        //setear el menu de navegación de abajo
        BottomNavigationView navView = findViewById(R.id.nav_view);
        call.enqueue(new Callback<Peliculas>() {
            @Override
            public void onResponse(Call<Peliculas> call, Response<Peliculas> response) {
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


                for (Peliculas post : response.body().getData()) {
                    lista_peliculas.add(new Peliculas(
                            post.getOriginal_title(), post.getNota(), post.getPoster_path(), post.getId()
                    ));
                }
                //creando adapter recyclerview
                Peliculas_adapter adapter = new Peliculas_adapter(Calendario_elementos.this, lista_peliculas);
               adapter.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       //Enviar al la actividad de perfil elemento el ID
                      Intent i = new Intent(Calendario_elementos.this, Perfil_elemento.class);
                       i.putExtra("id", lista_peliculas.get(recyclerView.getChildAdapterPosition(v)).getId());
                       startActivity(i);
                   }
               });

                //setear adapter al recyclerview
                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<Peliculas> call, Throwable t) {

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
                    Intent i = new Intent(Calendario_elementos.this, Perfil_elemento.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_buscar:
                    i = new Intent(Calendario_elementos.this, Perfil_elemento.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_perfil:
                    return true;
                case R.id.navigation_sesion:
                    i = new Intent(Calendario_elementos.this, login.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_registrarse:
                    i = new Intent(Calendario_elementos.this, NuevoUsuarioActivity.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };



}