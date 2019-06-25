package com.example.obligatoriomoviles.Clases.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.Clases.Adapters.recomendaciones_adapter;
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

public class recomendacion_fragment extends Fragment {
    GridView grilla;
    Call<Cine> call;
    List<Cine> lista_peliculas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lista_grupos,container,false);
        grilla = view.findViewById(R.id.lista_grupos);

        APIInterface apiService = APICliente.getPelicula().create(APIInterface.class);
         call = apiService.getRecomendaciones_pelicula(getActivity().getIntent().getExtras().getString("id"), "recommendations", "0d81ceeb977ab515fd9f844377688c5a", "es");

        lista_peliculas = new ArrayList<>();
        final ProgressBar spinner;
        spinner = view.findViewById(R.id.barra2);
        spinner.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Cine>() {
            public void onResponse(Call<Cine> call, Response<Cine> response) {

                for (Cine post : response.body().getData()) {
                    lista_peliculas.add(new Cine(
                            post.getOriginal_title(), post.getNota(), post.getPoster_path(), post.getId(),post.getFecha()
                    ));
                }
                //creando adapter recyclerview
                recomendaciones_adapter adapter = new recomendaciones_adapter(getActivity().getApplicationContext(), lista_peliculas);

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
                grilla.setAdapter(adapter);
                grilla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getActivity().getApplicationContext(), Perfil_elemento.class);
                        i.putExtra("id", lista_peliculas.get(position).getId());
                        i.putExtra("fecha", lista_peliculas.get(position).getFecha());
                        i.putExtra("genero", lista_peliculas.get(position).getPoster_path());
                        i.putExtra("titulo_elemento", lista_peliculas.get(position).getOriginal_title());
                        i.putExtra("tipo", "pelicula");
                        startActivity(i);
                    }
                });

            }

            @Override
            public void onFailure(Call<Cine> call, Throwable t) {

                Log.d("LoginActivity", t.getMessage() + t.getStackTrace().toString());
            }
        });
        spinner.setVisibility(View.GONE);
         return view;
    }
}
