package com.example.bookmark.category_management;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookmark.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(myViewHolder holder, int position,  MainModel model) {
        holder.title.setText(model.getTitle());
        holder.price.setText(model.getPrice());
        holder.discountPrice.setText(model.getDiscountPrice());

       Glide.with(holder.image.getContext())
               .load(model.getImage())
               .placeholder(R.drawable.common_google_signin_btn_icon_dark)
               .error(R.drawable.ic_baseline_add_photo_alternate_24)
               .into(holder.image);

       holder.image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent  = new Intent(v.getContext(),ItemDetails.class);
               intent.putExtra("itemKey",getRef(position).getKey());
               v.getContext().startActivity(intent);
           }
       });

    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView image;
        TextView title,price,discountPrice;


        public myViewHolder(View itemView) {
            super(itemView);

            image = (ShapeableImageView)itemView.findViewById(R.id.img2);
            title = (TextView)itemView.findViewById(R.id.nameText);
            price = (TextView)itemView.findViewById(R.id.price);
            discountPrice = (TextView)itemView.findViewById(R.id.discountPrice);

        }
    }

}
