package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.Clases.Cine.Cine_adapter;
import com.example.obligatoriomoviles.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Calendario_elementos extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //lista de peliculas
    List<Cine> lista_peliculas;

    //el recyclerview
    RecyclerView recyclerView;
    Call<Cine> call;
    Call<Cine> call_series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendario_elementos);

        //obtener recyclerview de xml
        recyclerView = findViewById(R.id.Lista);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        lista_peliculas = new ArrayList<>();

        Spinner spinner_2 = findViewById(R.id.lista_contenido);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.entretenimiento, R.layout.spinner_color);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_2.setAdapter(adapter);
        spinner_2.setOnItemSelectedListener(this);

        //Llamado de la API para que retorne el json de la consulta


        //Menu
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().getItem(1).setChecked(true);
        getSupportActionBar().setTitle("Moviles");

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
                    i = new Intent(getApplicationContext(), Calendario_elementos.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade, R.anim.outfade);
                    finish();
                    return true;

            }
            return false;
        }
    };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.lista_contenido) {
            if (position == 0) {
                APIInterface apiService = APICliente.getCalendario().create(APIInterface.class);
                call = apiService.getImagen("popularity.desc", 2019, "en-US", "0d81ceeb977ab515fd9f844377688c5a");
                lista_peliculas = new ArrayList<>();
                final ProgressBar spinner;
                spinner = findViewById(R.id.progressBar1);
                spinner.setVisibility(View.VISIBLE);
                call.enqueue(new Callback<Cine>() {
                    public void onResponse(Call<Cine> call, Response<Cine> response) {

                        for (Cine post : response.body().getData()) {
                            lista_peliculas.add(new Cine(
                                    post.getOriginal_title(), post.getNota(), post.getPoster_path(), post.getId(),post.getFecha()
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
                                i.putExtra("tipo", "pelicula");
                                startActivity(i);
                            }
                        });
                        Collections.sort(lista_peliculas, new Comparator<Cine>() {
                            DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                            int res;
                            @Override
                            public int compare(Cine o1, Cine o2) {
                                try {
                                    res = f.parse(o2.getFecha()).compareTo(f.parse(o1.getFecha()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                return res;
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
            } else {
                APIInterface apiService_series = APICliente.getCalendario().create(APIInterface.class);
                call_series = apiService_series.getSerie("popularity.desc", 2019, "en-US", "0d81ceeb977ab515fd9f844377688c5a");
                lista_peliculas = new ArrayList<>();
                final ProgressBar spinner;
                spinner = findViewById(R.id.progressBar1);
                spinner.setVisibility(View.VISIBLE);
                //Setear el focus a la opcion correspondiente (del 0 al numero de botones)

                call_series.enqueue(new Callback<Cine>() {
                    public void onResponse(Call<Cine> call, Response<Cine> response) {

                        for (Cine post : response.body().getData()) {
                            lista_peliculas.add(new Cine(
                                    post.getOriginal_name(), post.getNota(), post.getPoster_path(), post.getId(),post.getFecha()
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
                                i.putExtra("tipo", "serie");
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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }
}