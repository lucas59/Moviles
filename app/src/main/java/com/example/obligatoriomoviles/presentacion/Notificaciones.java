package com.example.obligatoriomoviles.presentacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Adapters.Cine_adapter;
import com.example.obligatoriomoviles.Clases.Adapters.Comentarios_adapter;
import com.example.obligatoriomoviles.Clases.Adapters.Notificaciones_adapter;
import com.example.obligatoriomoviles.Clases.Cine;
import com.example.obligatoriomoviles.Clases.Comentario;
import com.example.obligatoriomoviles.Clases.DatosPerfilElemento;
import com.example.obligatoriomoviles.Clases.retorno;
import com.example.obligatoriomoviles.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notificaciones extends AppCompatActivity {
    Call<retorno> call;
    RecyclerView recyclerView;
    List<Comentario> lista_notificaciones;
    Call<Cine> call_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        recyclerView = findViewById(R.id.notificaciones);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().getItem(2).setChecked(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        SharedPreferences prefs = getSharedPreferences("session", Context.MODE_PRIVATE);
        final String email = prefs.getString("sessionCorreo", null);
        APIInterface apiService = APICliente.getServidor().create(APIInterface.class);
        call = apiService.getNotificaciones(email);
        final APIInterface apiService_2 = APICliente.getPelicula().create(APIInterface.class);
        lista_notificaciones = new ArrayList<>();
        call.enqueue(new Callback<retorno>() {
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
                for(final Comentario noti : response.body().getLista_comentarios()){
                    call_2 =  apiService_2.getPelicula(noti.getContenido_id(), "0d81ceeb977ab515fd9f844377688c5a", "credits", "es");
                    final Comentario com = new Comentario();
                    call_2.enqueue(new Callback<Cine>() {
                        @Override
                        public void onResponse(Call<Cine> call, Response<Cine> response) {
                            com.setFoto(response.body().getPoster_path());
                            com.setContenido_id(noti.getContenido_id());
                            com.setTexto(noti.getTexto());
                            com.setUsuario_correo(noti.getUsuario_correo());
                            lista_notificaciones.add(com);
                            Notificaciones_adapter adapter = new Notificaciones_adapter(getApplicationContext(), lista_notificaciones);
                            adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Enviar al la actividad de perfil elemento el ID
                                    Intent i = new Intent(getApplicationContext(), Perfil_elemento.class);
                                    i.putExtra("id", lista_notificaciones.get(recyclerView.getChildAdapterPosition(v)).getContenido_id());
                                    i.putExtra("tipo", "pelicula");
                                    startActivity(i);
                                }
                            });
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<Cine> call, Throwable t) {

                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<retorno> call, Throwable t) {

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
                    finish();
                    return true;
                case R.id.navigation_notificacion:
                    i = new Intent(Notificaciones.this, Notificaciones.class);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    startActivity(i);
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
                overridePendingTransition(R.anim.infade,R.anim.outfade);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
