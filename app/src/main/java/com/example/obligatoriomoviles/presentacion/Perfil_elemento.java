package com.example.obligatoriomoviles.presentacion;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIError;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Cine.Actor;
import com.example.obligatoriomoviles.Clases.Cine.Actor_adapter;
import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.Clases.Cine.Comentarios_adapter;
import com.example.obligatoriomoviles.Clases.Comentario;
import com.example.obligatoriomoviles.Clases.retorno;
import com.example.obligatoriomoviles.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil_elemento extends AppCompatActivity {
    //lista de actores
    RecyclerView recyclerView;
    //boton de comentarios
    private Button dialogBtn;
    //lista de comentarios
    RecyclerView recyclerView_comentarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_elemento);
        //animación de carga
        final ProgressBar spinner;
        spinner = (ProgressBar)findViewById(R.id.barra);

        //Llamado de la API para que retorne el json de la consulta
        APIInterface apiService = APICliente.getPelicula().create(APIInterface.class);
        Call<Cine> call = apiService.getPelicula( getIntent().getExtras().getString("id"),"0d81ceeb977ab515fd9f844377688c5a","credits","es");

        //mostraar la lista de actores
        recyclerView = (RecyclerView) findViewById(R.id.Actores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Menu
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //Setear el focus a la opcion correspondiente (del 0 al numero de botones)
        navigationView.getMenu().getItem(2).setChecked(true);
        //setear el menu de navegación de abajo

        call.enqueue(new Callback<Cine>() {
            @Override
            public void onResponse(Call<Cine> call, Response<Cine> response) {
                spinner.setVisibility(View.VISIBLE);
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

                //quitar animación de carga
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Cine> call, Throwable t) {

            }



        });
        //llamar función dialog de comentarios
        dialogBtn = findViewById(R.id.boton_comentarios);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Perfil_elemento.this);
            }
        });

    }

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.comentarios);
        Button btndialog = (Button) dialog.findViewById(R.id.salir);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        recyclerView_comentarios = (RecyclerView) dialog.findViewById(R.id.comentario_lista);
        recyclerView_comentarios.setHasFixedSize(true);
        recyclerView_comentarios.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = getSharedPreferences("session", Context.MODE_PRIVATE);
        String email = prefs.getString("sessionCorreo", null);
        APIInterface apiService_2 = APICliente.getServidor().create(APIInterface.class);
        Call<retorno> call_2 = apiService_2.getComentario(getIntent().getExtras().getString("id"));

        call_2.enqueue(new Callback<retorno>() {
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
                final List<Comentario> lista_Comentarios;
                lista_Comentarios = new ArrayList<>();

                for (Comentario post : response.body().getLista_comentarios()) {
                    lista_Comentarios.add(new Comentario(
                            post.getTexto(), post.getUsuario_correo()));
                }
                if(!lista_Comentarios.isEmpty()) {
                    Comentarios_adapter adapter = new Comentarios_adapter(Perfil_elemento.this, lista_Comentarios);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView_comentarios.setAdapter(adapter);
                    dialog.show();
                }
                else{
                    Toast.makeText(Perfil_elemento.this, "No existen comentarios para esta pelicula", Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<retorno> call, Throwable t) {

                Log.d("LoginActivity", t.getMessage() + t.getStackTrace().toString());
            }
        });





    }

 public void Comentar(View view){
     AlertDialog.Builder builder = new AlertDialog.Builder(this);
     final View mView = getLayoutInflater().inflate(R.layout.comentar_mensaje,null);
     final EditText txtcomentario = (EditText) mView.findViewById(R.id.Comentario);
     SharedPreferences prefs = getSharedPreferences("session", Context.MODE_PRIVATE);
     final String email = prefs.getString("sessionCorreo", null);
     builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            if(!txtcomentario.getText().toString().isEmpty()) {
                if (email != null) {
                    final APIInterface apiService_2 = APICliente.getServidor().create(APIInterface.class);
                    String comentario = txtcomentario.getText().toString();
                    Integer id = Integer.parseInt(getIntent().getExtras().getString("id"));
                    String fecha = getIntent().getExtras().getString("fecha");
                    String genero = getIntent().getExtras().getString("genero");
                    String titulo_ele = getIntent().getExtras().getString("titulo_elemento");
                    Call<retorno> call = apiService_2.SetComentario(comentario,null,id,email,fecha,genero,titulo_ele);
                    call.enqueue(new Callback<retorno>() {
                        @Override
                        public void onResponse(Call<retorno> call, Response<retorno> response) {
                            if(response.body().getRetorno()) {
                                Toast.makeText(Perfil_elemento.this, "Comentario agregado", Toast.LENGTH_SHORT).show();
System.out.println("llego");                            }
                            else{
                                Toast.makeText(Perfil_elemento.this, "Error al comentar", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<retorno> call, Throwable t) {
                        }
                    });
                } else {
                    Toast.makeText(Perfil_elemento.this, "No existe una sesión en el sistema", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Perfil_elemento.this, "Complete los campos", Toast.LENGTH_SHORT).show();
            }
         }
     });
     builder.setNegativeButton(android.R.string.cancel,null);
     builder.setView(mView);
     Dialog dialog = builder.create();
    dialog.show();
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
