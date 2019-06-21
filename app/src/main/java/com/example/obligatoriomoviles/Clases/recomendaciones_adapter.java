package com.example.obligatoriomoviles.Clases;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class recomendaciones_adapter extends BaseAdapter {
    private Context mContext;
    List<Cine> lista_peliculas;
    public recomendaciones_adapter(Context c,List<Cine> cine) {
        mContext = c;
        lista_peliculas = cine;
    }
    @Override
    public int getCount() {
        return lista_peliculas.size();
    }

    @Override
    public Object getItem(int position) {
        return lista_peliculas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater lay = LayoutInflater.from(mContext);
            convertView = lay.inflate(R.layout.imagen_solo,parent,false);
            ImageView imagen = (ImageView) convertView.findViewById(R.id.perfil);
            String fondo = "https://image.tmdb.org/t/p/w500" + lista_peliculas.get(position).getPoster_path();
            Picasso.get().load(fondo).fit().centerCrop().into(imagen);
        }
        return convertView;
    }
}
