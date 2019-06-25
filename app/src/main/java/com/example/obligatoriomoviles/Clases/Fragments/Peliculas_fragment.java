package com.example.obligatoriomoviles.Clases.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.Clases.Adapters.Cine_adapter;
import com.example.obligatoriomoviles.R;
import com.example.obligatoriomoviles.presentacion.Perfil_elemento;

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

public class Peliculas_fragment extends Fragment {
    RecyclerView recyclerView;
    Call<Cine> call;
    List<Cine> lista_peliculas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.fragment_lista_contenido,container,false);
        recyclerView = view.findViewById(R.id.Lista_calendario);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        APIInterface apiService = APICliente.getCalendario().create(APIInterface.class);
        call = apiService.getImagen("popularity.desc", 2019, "en-US", "0d81ceeb977ab515fd9f844377688c5a");
        lista_peliculas = new ArrayList<>();
        final ProgressBar spinner;
        spinner = view.findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Cine>() {
            public void onResponse(Call<Cine> call, Response<Cine> response) {

                for (Cine post : response.body().getData()) {
                    lista_peliculas.add(new Cine(
                            post.getOriginal_title(), post.getNota(), post.getPoster_path(), post.getId(),post.getFecha()
                    ));
                }
                //creando adapter recyclerview
                Cine_adapter adapter = new Cine_adapter(getActivity().getApplicationContext(), lista_peliculas);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Enviar al la actividad de perfil elemento el ID
                        Intent i = new Intent(getActivity().getApplicationContext(), Perfil_elemento.class);
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

       return view;
    }
}