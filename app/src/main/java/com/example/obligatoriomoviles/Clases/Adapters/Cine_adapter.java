package com.example.obligatoriomoviles.Clases.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.obligatoriomoviles.Clases.Cine;
import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Cine_adapter extends RecyclerView.Adapter<Cine_adapter.ProductViewHolder> implements View.OnClickListener {


    private Context mCtx;
    private View.OnClickListener listener;

    private List<Cine> productList;

    public Cine_adapter(Context mCtx, List<Cine> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.lista_calendario, null);
        view.setOnClickListener(this);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Cine product = productList.get(position);
        String fondo = "https://image.tmdb.org/t/p/w500" + product.getPoster_path();
        if (product.getOriginal_name() != null) {
            holder.textViewTitulo.setText(product.getOriginal_name());
        } else {
            holder.textViewTitulo.setText(product.getOriginal_title());
        }
        holder.textViewID.setText(product.getId());
        holder.textViewFecha.setText(product.getFecha());
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

        TextView textViewTitulo, textViewID, textViewFecha;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.Titulo);
            imageView = itemView.findViewById(R.id.Poster);
            textViewID = itemView.findViewById(R.id.id);
            textViewFecha = itemView.findViewById(R.id.Fecha);
            textViewID.setVisibility(View.INVISIBLE);
        }
    }
}