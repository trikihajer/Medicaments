package com.tuteur.projet.medicaments.Model.essai.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tuteur.projet.medicaments.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView deleteProduct;
    public ImageView editProduct;
    public ImageView imageProduit;

    public ProductViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.product_name);
        deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
        editProduct = (ImageView)itemView.findViewById(R.id.edit_product);
        imageProduit = (ImageView)itemView.findViewById(R.id.imageListProd);

    }
}
