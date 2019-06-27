package com.example.obligatoriomoviles.presentacion;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Actor;
import com.example.obligatoriomoviles.Clases.Adapters.Actor_adapter;
import com.example.obligatoriomoviles.Clases.Adapters.Comentarios_adapter;
import com.example.obligatoriomoviles.Clases.Capitulo;
import com.example.obligatoriomoviles.Clases.Comentario;
import com.example.obligatoriomoviles.Clases.retorno;
import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil_capitulo extends AppCompatActivity {
    RecyclerView recyclerView_comentarios;
    private Button dialogBtn;
    private FloatingActionButton comentar_btn;
    private Integer id_capitulo;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitulo_perfil);
        APIInterface apiService = APICliente.getPelicula().create(APIInterface.class);
        Call<Capitulo> call = apiService.getCapitulo(getIntent().getExtras().getString("id"), getIntent().getExtras().getInt("temporada"), getIntent().getExtras().getInt("capitulo"), "0d81ceeb977ab515fd9f844377688c5a", "es");
        recyclerView = (RecyclerView) findViewById(R.id.lista_actores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        call.enqueue(new Callback<Capitulo>() {
            @Override
            public void onResponse(Call<Capitulo> call, Response<Capitulo> response) {
                if(response.isSuccessful()) {

                    id_capitulo = response.body().getId();
                    TextView titulo = findViewById(R.id.titulo);
                    TextView valor = (TextView) findViewById(R.id.valor);
                    TextView fecha = (TextView) findViewById(R.id.fecha);
                    ProgressBar votos = (ProgressBar) findViewById(R.id.votos);
                    TextView sinopsis = findViewById(R.id.sinopsis);
                    ImageView fondo = findViewById(R.id.imagen_fondo);
                    String fondo_imagen = "https://image.tmdb.org/t/p/w500" + response.body().getImagen();
                    Picasso.get().load(fondo_imagen).fit().centerCrop().into(fondo);
                    titulo.setText(response.body().getNombre());
                    sinopsis.setText(response.body().getSinopsis());
                    votos.setProgress((int) Float.parseFloat(response.body().getNota()));
                    valor.setText(response.body().getNota());
                    fecha.setText(response.body().getFecha());
                    List<Actor> actores = new ArrayList<>();
                    if (response.body().getActores() != null) {
                        int len = response.body().getActores().size();
                        for (int i = 0; i < len; i++) {
                            actores.add(new Actor(response.body().getActores().get(i).getNombre(), response.body().getActores().get(i).getPersonaje(), response.body().getActores().get(i).getImagen()));
                        }
                    }
                    Actor_adapter adapter = new Actor_adapter(Perfil_capitulo.this, actores);
                    recyclerView.setAdapter(adapter);
                }
                else{
                    Perfil_capitulo.this.finish();
                    Toast.makeText(getApplicationContext(), "Serie no Agregada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Capitulo> call, Throwable t) {

            }
        });
        dialogBtn = findViewById(R.id.boton_comentarios);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


    }

    public void showDialog() {
        final Dialog dialog = new Dialog(Perfil_capitulo.this);
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
        recyclerView_comentarios.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        APIInterface apiService_2 = APICliente.getServidor().create(APIInterface.class);
        Call<retorno> call_2 = apiService_2.getComentarioSerie(id_capitulo);

        call_2.enqueue(new Callback<retorno>() {
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
                final List<Comentario> lista_Comentarios;
                lista_Comentarios = new ArrayList<>();

                for (Comentario post : response.body().getLista_comentarios()) {
                    lista_Comentarios.add(new Comentario(
                            post.getTexto(), post.getUsuario_correo(), post.getId()));
                }
                if (!lista_Comentarios.isEmpty()) {
                    Comentarios_adapter adapter = new Comentarios_adapter(getApplicationContext(), lista_Comentarios);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext().getApplicationContext());
                    recyclerView_comentarios.setAdapter(adapter);
                    dialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "No existen comentarios para este capitulo", Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<retorno> call, Throwable t) {

                Log.d("LoginActivity", t.getMessage() + t.getStackTrace().toString());
            }
        });




    }

    public void Comentar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View mView = getLayoutInflater().inflate(R.layout.comentar_mensaje, null);
        final EditText txtcomentario = (EditText) mView.findViewById(R.id.Comentario);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        final String email = prefs.getString("sessionCorreo", null);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!txtcomentario.getText().toString().isEmpty()) {
                    if (email != null) {
                        final APIInterface apiService_2 = APICliente.getServidor().create(APIInterface.class);
                        String comentario = txtcomentario.getText().toString();
                        Integer id = Integer.parseInt(getIntent().getExtras().getString("id"));
                        Integer temporada = getIntent().getExtras().getInt("temporada");
                        Integer capitulo = getIntent().getExtras().getInt("capitulo");
                        String fecha = getIntent().getExtras().getString("fecha");
                        String genero = getIntent().getExtras().getString("genero");
                        String titulo_ele = getIntent().getExtras().getString("titulo_elemento");
                        Call<retorno> call = apiService_2.SetComentario(comentario, id,temporada,capitulo,id_capitulo, null, email, fecha, genero, titulo_ele);
                        call.enqueue(new Callback<retorno>() {
                            @Override
                            public void onResponse(Call<retorno> call, Response<retorno> response) {
                                if (response.body().getRetorno()) {
                                    Toast.makeText(getApplicationContext(), "Comentario agregado", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error al comentar", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<retorno> call, Throwable t) {
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "No existe una sesión en el sistema", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setView(mView);
        Dialog dialog = builder.create();
        dialog.show();
    }
}
