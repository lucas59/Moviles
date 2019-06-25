package com.example.obligatoriomoviles.Clases.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class capitulos_adapter extends BaseExpandableListAdapter {
    private ArrayList<String> listCategoria;
    private Map<String,ArrayList<String>> mapChild;
    private ArrayList<String> listaImagenes;
    private Context context;

    public capitulos_adapter(ArrayList<String> listCategoria, Map<String, ArrayList<String>> mapChild,ArrayList<String> imagenes, Context context) {
        this.listCategoria = listCategoria;
        this.mapChild = mapChild;
        this.listaImagenes = imagenes;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return listCategoria.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mapChild.get(listCategoria.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listCategoria.get(groupPosition);
    }

    public Object getImagen(int groupPosition) {
        return listaImagenes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mapChild.get(listCategoria.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String tituloCategoria = (String) getGroup(groupPosition);
        String imagen_poster = (String) getImagen(groupPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.card_temporadas,null);
        TextView tvGrupo = convertView.findViewById(R.id.titulo);
        ImageView imagen = convertView.findViewById(R.id.poster);
        tvGrupo.setText(tituloCategoria);
        String fondo_imagen = "https://image.tmdb.org/t/p/w500" + imagen_poster;
        Picasso.get().load(fondo_imagen).fit().centerCrop().into(imagen);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String tituloSerie = (String) getChild(groupPosition,childPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.card_capitulos,null);
        TextView tvcapitulo = (TextView) convertView.findViewById(R.id.Capitulo);
        tvcapitulo.setText(tituloSerie);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
