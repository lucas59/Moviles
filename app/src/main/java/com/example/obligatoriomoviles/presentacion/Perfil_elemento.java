package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIError;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Peliculas;
import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil_elemento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_elemento);
        //Llamado de la API para que retorne el json de la consulta
        APIInterface apiService = APICliente.getPelicula().create(APIInterface.class);
        Call<Peliculas> call = apiService.getPelicula(getIntent().getExtras().getString("id"),"0d81ceeb977ab515fd9f844377688c5a");
        //Menu
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        //Setear el focus a la opcion correspondiente (del 0 al numero de botones)
        navigationView.getMenu().getItem(1).setChecked(true);
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


                //Setear fondo y poster del elemento
                String fondo = "https://image.tmdb.org/t/p/w500" + response.body().getBackdrop_path();
                String poster = "https://image.tmdb.org/t/p/w500" + response.body().getPoster_path();

                //traer el ImageView y el textview del layout
                 ImageView fondo_view = (ImageView) findViewById(R.id.imagen_fondo);
                 ImageView poster_view = (ImageView) findViewById(R.id.imagen_poster);
                TextView titulo = (TextView) findViewById(R.id.titulo);
                //Setear información en los elementos
                titulo.setText(response.body().getOriginal_title());
                Picasso.get().load(fondo).fit().centerCrop().into(fondo_view);
                Picasso.get().load(poster).into(poster_view);

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
                    Intent i = new Intent(Perfil_elemento.this, Perfil_elemento.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_buscar:
                    i = new Intent(Perfil_elemento.this, Perfil_elemento.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_perfil:
                    return true;
                case R.id.navigation_sesion:
                    i = new Intent(Perfil_elemento.this, login.class);
                    startActivity(i);
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
