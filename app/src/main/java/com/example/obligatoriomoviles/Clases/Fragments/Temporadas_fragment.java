package com.example.obligatoriomoviles.Clases.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Adapters.capitulos_adapter;
import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.Clases.Cine.Temporadas;
import com.example.obligatoriomoviles.R;
import com.example.obligatoriomoviles.presentacion.Perfil_capitulo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Temporadas_fragment extends Fragment {
    private ExpandableListView expLV;
    private capitulos_adapter adapter;
    private ArrayList<String> listaTemporadas;
    private Map<String, ArrayList<String>> mapCapitulos;
    private Integer temporada;
    private Integer capitulo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_temporadas, container, false);
        expLV = view.findViewById(R.id.lista);
        listaTemporadas = new ArrayList<>();
        mapCapitulos = new HashMap<>();
        cargarLista();
        return view;
    }

    private void cargarLista() {
        APIInterface apiService_serie = APICliente.getPelicula().create(APIInterface.class);
        Call<Cine> call_serie = apiService_serie.getSerie_unica(getActivity().getIntent().getExtras().getString("id"), "0d81ceeb977ab515fd9f844377688c5a", "credits", "es");
        call_serie.enqueue(new Callback<Cine>() {
            @Override
            public void onResponse(Call<Cine> call, Response<Cine> response) {
                List<Temporadas> temporadas = new ArrayList<>();
                temporadas = response.body().getLista_temporadas();
                ArrayList<String> capitulos = new ArrayList<>();
                ArrayList<String> listaTemporadas = new ArrayList<>();
                ArrayList<String> imagenes = new ArrayList<>();
                for (int i = 0; i < temporadas.size(); i++) {
                    capitulos = new ArrayList<>();
                    if(temporadas.get(i).getTemporada() == 0){
                        listaTemporadas.add("Especiales");
                    }else {
                        listaTemporadas.add("Temporada " + temporadas.get(i).getTemporada());
                    }
                    imagenes.add(temporadas.get(i).getImagen());
                    for (int j = 1; j <= temporadas.get(i).getCapitulos(); j++) {
                        capitulos.add("Capitulo " + j);
                    }
                    mapCapitulos.put(listaTemporadas.get(i), capitulos);
                }
                adapter = new capitulos_adapter(listaTemporadas, mapCapitulos,imagenes, getActivity());
                expLV.setAdapter(adapter);
                expLV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        temporada = groupPosition;
                        capitulo = childPosition + 1;
                        Intent i = new Intent(getActivity().getApplicationContext(), Perfil_capitulo.class);
                        i.putExtra("id",getActivity().getIntent().getExtras().getString("id"));
                        i.putExtra("temporada", temporada);
                        i.putExtra("capitulo", capitulo);
                        startActivity(i);
                        return true;
                    }
                });
            }

            @Override
            public void onFailure(Call<Cine> call, Throwable t) {

            }


        });
    }
}
