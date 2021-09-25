package com.example.bookmark.category_management;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookmark.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EditMainAdapter extends FirebaseRecyclerAdapter<MainModel,EditMainAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EditMainAdapter(FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(myViewHolder holder, final int position,  MainModel model) {
        holder.title.setText(model.getTitle());
        holder.price.setText(model.getPrice());
        holder.discountPrice.setText(model.getDiscountPrice());
        holder.description.setText(model.getDescription());


        Glide.with(holder.image.getContext())
                .load(model.getImage())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.image);


        //popup
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.image.getContext())
                        .setContentHolder(new ViewHolder(R.layout.updated_popup))
                        .setExpanded(true,1200)
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText title = view.findViewById(R.id.txt1);
                EditText price = view.findViewById(R.id.txt2);
                EditText discountPrice = view.findViewById(R.id.txt3);
                EditText description = view.findViewById(R.id.txt4);
                EditText image = view.findViewById(R.id.txt5);


                Button update = view.findViewById(R.id.update);

                title.setText(model.getTitle());
                price.setText(model.getPrice());
                discountPrice.setText(model.getDiscountPrice());
                description.setText(model.getDescription());
                image.setText(model.getImage());

                dialogPlus.show();

                //update item
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("title",title.getText().toString());
                        map.put("price",price.getText().toString());
                        map.put("discountPrice",discountPrice.getText().toString());
                        map.put("description",description.getText().toString());
                        map.put("image",image.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Books")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.price.getContext(), "Book updated Successfully", Toast.LENGTH_SHORT);
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure( Exception e) {
                                        Toast.makeText(holder.price.getContext(), "Error while updating", Toast.LENGTH_SHORT);
                                        dialogPlus.dismiss();
                                    }
                                });


                    }
                });

            }
        });


        //delete item
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.title.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Books")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(holder.price.getContext(), "Cancelled", Toast.LENGTH_SHORT);
                    }
                });
                builder.show();
            }
        });
    }




    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView image;
        TextView title,price,discountPrice,description;
        Button edit,delete;


        public myViewHolder(View itemView) {
            super(itemView);

            image = (ShapeableImageView)itemView.findViewById(R.id.img2);
            title = (TextView)itemView.findViewById(R.id.nameText);
            price = (TextView)itemView.findViewById(R.id.price);
            discountPrice = (TextView)itemView.findViewById(R.id.discountPrice);
            description= (TextView)itemView.findViewById(R.id.desc);

            edit = (Button)itemView.findViewById(R.id.edit);
            delete = (Button)itemView.findViewById(R.id.delete);




        }
    }

}

