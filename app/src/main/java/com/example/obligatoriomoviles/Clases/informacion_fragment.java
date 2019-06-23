package com.example.obligatoriomoviles.Clases;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Cine.Actor;
import com.example.obligatoriomoviles.Clases.Cine.Actor_adapter;
import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.Clases.Cine.Comentarios_adapter;
import com.example.obligatoriomoviles.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class informacion_fragment extends Fragment {
    //lista de actores
    RecyclerView recyclerView;
    //boton de comentarios
    private Button dialogBtn;
    //lista de comentarios
    RecyclerView recyclerView_comentarios;
    String tituloElemento = null;
    TextView titulo = null;
    ImageButton favorito;
    private String identificador;
    private String fechaElemento;
    private String genero;
    private String tituloelemento;
    private APIInterface apiServidor;
    private Boolean seguir = false;
    boolean tipoElemento = Boolean.parseBoolean(null);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_informacion,container,false);
        //animación de carga
        final ProgressBar spinner;
        spinner = (ProgressBar) view.findViewById(R.id.barra);

        //Llamado de la API para que retorne el json de la consulta
        APIInterface apiService = APICliente.getPelicula().create(APIInterface.class);
        Call<Cine> call = apiService.getPelicula(getActivity().getIntent().getExtras().getString("id"), "0d81ceeb977ab515fd9f844377688c5a", "credits", "es");

        APIInterface apiService_serie = APICliente.getPelicula().create(APIInterface.class);
        Call<Cine> call_serie = apiService_serie.getSerie_unica(getActivity().getIntent().getExtras().getString("id"), "0d81ceeb977ab515fd9f844377688c5a", "credits", "es");

        APIInterface apiCliente = APICliente.getServidor().create(APIInterface.class);
        this.apiServidor = apiCliente;
        //mostraar la lista de actores
        recyclerView = (RecyclerView) view.findViewById(R.id.Actores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (getActivity().getIntent().getExtras().getString("tipo").toString().compareTo("serie") != 0) {//pelicula
            this.tipoElemento = true;
            call.enqueue(new Callback<Cine>() {
                @Override
                public void onResponse(Call<Cine> call, Response<Cine> response) {
                    spinner.setVisibility(View.VISIBLE);
                    JsonObject creditos = response.body().getCreditos();
                    JsonArray casts = creditos.get("cast").getAsJsonArray();
                    List<Actor> actores = new ArrayList<>();
                    if (casts != null) {
                        int len = casts.size();
                        for (int i = 0; i < len; i++) {
                            actores.add(new Actor(casts.get(i).getAsJsonObject().get("name").toString(), casts.get(i).getAsJsonObject().get("character").toString(), casts.get(i).getAsJsonObject().get("profile_path").toString()));
                        }
                    }
                    Actor_adapter adapter = new Actor_adapter(getActivity(), actores);
                    recyclerView.setAdapter(adapter);
                    //Setear fondo y poster del elemento
                    String fondo = "https://image.tmdb.org/t/p/w500" + response.body().getBackdrop_path();
                    String poster = "https://image.tmdb.org/t/p/w500" + response.body().getPoster_path();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    RecyclerView myList = (RecyclerView) view.findViewById(R.id.Actores);
                    myList.setLayoutManager(layoutManager);
                    //traer el ImageView y el textview del layout
                    ImageView fondo_view = (ImageView) view.findViewById(R.id.imagen_fondo);
                    ImageView poster_view = (ImageView) view.findViewById(R.id.imagen_poster);
                    TextView fecha = (TextView) view.findViewById(R.id.fecha);
                    titulo = (TextView) view.findViewById(R.id.titulo);
                    TextView sinopsis = (TextView) view.findViewById(R.id.sinopsis);
                    TextView valor = (TextView) view.findViewById(R.id.valor);
                    ProgressBar votos = (ProgressBar) view.findViewById(R.id.votos);
                    //Setear información en los elementos
                    titulo.setText(response.body().getOriginal_title());
                    tituloelemento = response.body().getOriginal_title();
                    fechaElemento = response.body().getFecha();

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
        } else {
            this.tipoElemento = false;
            call_serie.enqueue(new Callback<Cine>() {
                @Override
                public void onResponse(Call<Cine> call, Response<Cine> response) {
                    spinner.setVisibility(View.VISIBLE);
                    JsonObject creditos = response.body().getCreditos();
                    JsonArray casts = creditos.get("cast").getAsJsonArray();
                    List<Actor> actores = new ArrayList<>();
                    if (casts != null) {
                        int len = casts.size();
                        for (int i = 0; i < len; i++) {
                            actores.add(new Actor(casts.get(i).getAsJsonObject().get("name").toString(), casts.get(i).getAsJsonObject().get("character").toString(), casts.get(i).getAsJsonObject().get("profile_path").toString()));
                        }
                    }
                    Actor_adapter adapter = new Actor_adapter(getActivity(), actores);
                    recyclerView.setAdapter(adapter);
                    //Setear fondo y poster del elemento
                    String fondo = "https://image.tmdb.org/t/p/w500" + response.body().getBackdrop_path();
                    String poster = "https://image.tmdb.org/t/p/w500" + response.body().getPoster_path();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    RecyclerView myList = (RecyclerView) view.findViewById(R.id.Actores);
                    myList.setLayoutManager(layoutManager);
                    //traer el ImageView y el textview del layout
                    ImageView fondo_view = (ImageView) view.findViewById(R.id.imagen_fondo);
                    ImageView poster_view = (ImageView) view.findViewById(R.id.imagen_poster);
                    TextView fecha = (TextView) view.findViewById(R.id.fecha);
                    TextView titulo = (TextView) view.findViewById(R.id.titulo);
                    TextView sinopsis = (TextView) view.findViewById(R.id.sinopsis);
                    TextView valor = (TextView) view.findViewById(R.id.valor);
                    ProgressBar votos = (ProgressBar) view.findViewById(R.id.votos);
                    //Setear información en los elementos

                    fechaElemento = response.body().getFecha();
                    tituloElemento = (response.body().getOriginal_title());
                    Button comentarios = view.findViewById(R.id.boton_comentarios);
                    comentarios.setVisibility(View.GONE);
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
        }
        //llamar función dialog de comentarios
        dialogBtn = view.findViewById(R.id.boton_comentarios);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity());
            }
        });
        this.favorito = view.findViewById(R.id.favorito); ///obtener si es favorita o no
        SharedPreferences prefs = getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        final String email = prefs.getString("sessionCorreo", null);
        if (email != null) {
            identificador = getActivity().getIntent().getExtras().getString("id");
            mostrarFavorito(1);
            final Call<retorno> seguido = apiCliente.verificarSuscripcion(email, this.identificador);
            seguido.enqueue(new Callback<retorno>() {
                @Override
                public void onResponse(Call<retorno> call, Response<retorno> response) {
                    if (response.body().getRetorno()) {
                        marcarFavorito(1);//marcar el boton en activo
                        seguir = true;
                        favorito.setImageResource(R.drawable.check);
                    } else {
                        marcarFavorito(0);//marcar el boton en desactivado
                        seguir = false;
                        favorito.setImageResource(R.drawable.no_check);
                    }
                }

                @Override
                public void onFailure(Call<retorno> call, Throwable t) {

                }
            });
        } else {
            mostrarFavorito(0);//no mostrar el boton si no hay session
        }


        final View view_2 = inflater.inflate(R.layout.lista_comentarios,container,false);
        Button boton_punt = view_2.findViewById(R.id.puntuar);
        boton_punt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               PuntuarComentario(getView());
            }
        });

        Button boton_rep = view_2.findViewById(R.id.reportar);
        boton_rep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ReportarComentario(getView());
            }
        });

        ImageButton boton_fav = view.findViewById(R.id.favorito);
        boton_fav.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                seguirElemento(getView());
            }
        });

        FloatingActionButton boton_comentar = view.findViewById(R.id.boton_comentar);
        boton_comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comentar(getView());
            }
        });
        return view;
    }

    private void mostrarFavorito(int i) {
        if (i == 1) {
            favorito.setImageResource(R.drawable.check);
        } else {
            favorito.setImageResource(R.drawable.no_check);
        }
    }

    private void marcarFavorito(int i) {
        if (i == 1)
            favorito.setImageResource(R.drawable.check);
        else
            favorito.setImageResource(R.drawable.no_check);

    }


    public void showDialog(Activity activity) {
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
        recyclerView_comentarios.setLayoutManager(new LinearLayoutManager(getActivity()));
        APIInterface apiService_2 = APICliente.getServidor().create(APIInterface.class);
        Call<retorno> call_2 = apiService_2.getComentario(getActivity().getIntent().getExtras().getString("id"));

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
                    Comentarios_adapter adapter = new Comentarios_adapter(getActivity(), lista_Comentarios);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView_comentarios.setAdapter(adapter);
                    dialog.show();
                } else {
                    Toast.makeText(getActivity(), "No existen comentarios para esta pelicula", Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<retorno> call, Throwable t) {

                Log.d("LoginActivity", t.getMessage() + t.getStackTrace().toString());
            }
        });




    }

    public void Comentar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View mView = getLayoutInflater().inflate(R.layout.comentar_mensaje, null);
        final EditText txtcomentario = (EditText) mView.findViewById(R.id.Comentario);
        SharedPreferences prefs = getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        final String email = prefs.getString("sessionCorreo", null);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!txtcomentario.getText().toString().isEmpty()) {
                    if (email != null) {

                        final APIInterface apiService_2 = APICliente.getServidor().create(APIInterface.class);
                        String comentario = txtcomentario.getText().toString();
                        Integer id = Integer.parseInt(getActivity().getIntent().getExtras().getString("id"));
                        String fecha = getActivity().getIntent().getExtras().getString("fecha");
                        String genero = getActivity().getIntent().getExtras().getString("genero");
                        String titulo_ele = getActivity().getIntent().getExtras().getString("titulo_elemento");
                        Call<retorno> call = apiService_2.SetComentario(comentario, null, id, email, fecha, genero, titulo_ele);
                        call.enqueue(new Callback<retorno>() {
                            @Override
                            public void onResponse(Call<retorno> call, Response<retorno> response) {
                                if (response.body().getRetorno()) {
                                    Toast.makeText(getActivity(), "Comentario agregado", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Error al comentar", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<retorno> call, Throwable t) {
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "No existe una sesión en el sistema", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setView(mView);
        Dialog dialog = builder.create();
        dialog.show();
    }


    public void cambiarEstado() {
        if (this.seguir) {
            favorito.setImageResource(R.drawable.no_check);
            Toast.makeText(getActivity().getApplicationContext(), "Usted no sigue este elemento", Toast.LENGTH_SHORT).show();
            this.seguir = false;
        } else {
            favorito.setImageResource(R.drawable.check);
            Toast.makeText(getActivity().getApplicationContext(), "Usted sigue este elemento", Toast.LENGTH_SHORT).show();
            this.seguir = true;
        }
    }
    public void ReportarComentario(View view) {
        LayoutInflater li = LayoutInflater.from(getActivity());
        final View myView = li.inflate(R.layout.lista_comentarios, null);
        TextView idcomentario = myView.findViewById(R.id.idcomentario);
        String id = idcomentario.getText().toString();
        Integer comentario = Integer.parseInt(id);
        final APIInterface apiService_2 = APICliente.getServidor().create(APIInterface.class);
        Call<retorno> call = apiService_2.ReportarComentario(comentario);
        call.enqueue(new Callback<retorno>() {
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
                if (response.body().getRetorno()) {
                    Toast.makeText(getActivity(), "Comentario reportado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Error al reportar comentario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<retorno> call, Throwable t) {
            }
        });
    }

    public void PuntuarComentario(View view) {
        LayoutInflater li = LayoutInflater.from(getActivity());
        final View myView = li.inflate(R.layout.lista_comentarios, null);
        TextView idcomentario = myView.findViewById(R.id.idcomentario);
        TextView usuario = view.findViewById(R.id.titulo);
        RatingBar puntuacion = view.findViewById(R.id.puntuacion);

        String id = idcomentario.getText().toString();
        String Usuario = usuario.getText().toString();
        Integer comentario = Integer.parseInt(id);
        final APIInterface apiService_2 = APICliente.getServidor().create(APIInterface.class);
        Call<retorno> call = apiService_2.PuntuarComentario(comentario, Usuario, puntuacion.getRating());
        call.enqueue(new Callback<retorno>() {
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
                if (response.body().getRetorno()) {
                    Toast.makeText(getActivity(), "Comentario puntuado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Error al puntuar comentario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<retorno> call, Throwable t) {

            }
        });
    }

    public void seguirElemento(View view) {

        SharedPreferences prefs = getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        final String email = prefs.getString("sessionCorreo", null);

        Call<retorno> call = apiServidor.seguirElemento(email, identificador, fechaElemento, genero, tituloelemento,tipoElemento);
        call.enqueue(new Callback<retorno>() {
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
                if (response.body().getRetorno() == true) {
                    cambiarEstado();//cambiar el estado de color y mostrar un toast avisando

                    //la llamada devuelve verdadero si se dejo de seguir o si se emprezo a seguir
                }
            }

            @Override
            public void onFailure(Call<retorno> call, Throwable t) {
            }
        });
    }
}
