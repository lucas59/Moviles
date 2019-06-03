package com.example.obligatoriomoviles.Clases;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.obligatoriomoviles.R;

public class Slider_Adapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutFlater;

    public Slider_Adapter(Context context){
        this.context = context;
    }


    public int[] slide_images = {
            R.drawable.series_2,
            R.drawable.serie_1,
            R.drawable.cine
    };

    public String[] slide_titulos = {
            "“I am the danger”",
            "“You know nothing Jon Snow”",
             "“¡¡Wilsooon!!”"
    };

    public String[] slide_texto = {
      "En (Nombre de la aplicación) puedes seguir las series y peliculas que mas te gustan y saber cuando se estrenan.",
            "En (Nombre de la aplicación) puedes comentar que te parecio una película o serie",
            "En (Nombre de la aplicación) puedes ayudar a la comunidad y reportar comentarios indeseados"

    };

    @Override
    public int getCount() {
        return slide_texto.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view ==  o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutFlater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutFlater.inflate(R.layout.slide_layout,container,false);

        ImageView slideimagen_e = (ImageView) view.findViewById(R.id.imagen_e);
        TextView slidetitulo_e = (TextView) view.findViewById(R.id.texto_e);
        TextView slidetexto_e = (TextView) view.findViewById(R.id.titulo_e);

        slideimagen_e.setImageResource(slide_images[position]);
        slidetitulo_e.setText(slide_titulos[position]);
        slidetexto_e.setText(slide_texto[position]);
        container.addView(view);
        return view;
    }

public void destroyItem(ViewGroup conteiner,int position,Object object){
    ((ViewPager) conteiner).removeView((View) object);
}
}