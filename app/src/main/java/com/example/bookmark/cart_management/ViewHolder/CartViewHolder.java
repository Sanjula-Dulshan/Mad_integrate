package com.example.bookmark.cart_management.ViewHolder;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmark.R;
import com.example.bookmark.cart_management.ItemClickListner;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "TAG";
    public TextView txtProductName, txtProductPrice, txtProductQuantity;


    private ItemClickListner itemClickListner;

    public CartViewHolder(View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);

        Log.d(TAG, "CartViewHolder: "+txtProductName+""+txtProductPrice+""+txtProductQuantity+"");

    }

    public void onClick(View view){

        itemClickListner.onClick(view,getAdapterPosition(), false);
    }

    public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.itemClickListner = itemClickListner;
    }
}
