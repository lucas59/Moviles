package com.example.obligatoriomoviles.Clases.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.obligatoriomoviles.Clases.Comentario;
import com.example.obligatoriomoviles.Clases.Notificaciones;
import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Notificaciones_adapter extends RecyclerView.Adapter<Notificaciones_adapter.ProductViewHolder> implements View.OnClickListener {


    private Context mCtx;
    private View.OnClickListener listener;

    private List<Comentario> productList;

    public Notificaciones_adapter(Context mCtx, List<Comentario> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.lista_notificacion, null);
        view.setOnClickListener(this);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Comentario product = productList.get(position);
        String fondo = "https://image.tmdb.org/t/p/w500" + product.getFoto();
        holder.textViewTitulo.setText(product.getUsuario_correo());
        holder.textViewID.setText(product.getTexto());
        Picasso.get().load(fondo).fit().centerCrop().into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setOnClickListener(View.OnClickListener listener2) {
        this.listener = listener2;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitulo, textViewID;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.txtFecha);
            imageView = itemView.findViewById(R.id.Foto);
            textViewID = itemView.findViewById(R.id.txtAsunto);
        }
    }
}