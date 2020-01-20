package com.example.myapplication1;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList<PlaceModel> models;
    DetailFragment detailFragment = new DetailFragment();

    boolean showShimmer = true;
    int SHIMMER_ITEM_NUMBER = 7; //number of item shown while loading

    public Adapter(Context context, ArrayList<PlaceModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        if (showShimmer) {
            holder.shimmerFrameLayout.startShimmer(); //start shimmer animation
        } else{
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null); //remove shimmer overlay

            holder.place_name.setBackground(null);
            holder.place_name.setText(models.get(position).getNama());

//            holder.food_detail.setBackground(null);
//            holder.food_detail.setText(models.get(position).getDetail());

            holder.image.setBackground(null);
            Picasso.get().load(models.get(position).getImage()).into(holder.image);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle args = new Bundle();
//                    This is how you do putExtra from fragment to fragment
                    args.putString("name",models.get(position).getNama());
                    args.putString("detail",models.get(position).getDetail());
                    args.putString("image",models.get(position).getImage());
                    args.putString("location",models.get(position).getLokasi());
                    args.putString("direction",models.get(position).getDirect());
                    detailFragment.setArguments(args);

                    ((FragmentActivity)view.getContext()).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                            .replace(R.id.homeFrame,detailFragment).addToBackStack(null).commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return showShimmer?SHIMMER_ITEM_NUMBER : models.size(); //return 7 while loading, after loading models size;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ShimmerFrameLayout shimmerFrameLayout;
        ImageView image;
        TextView place_name;
        CardView cardView;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                shimmerFrameLayout = itemView.findViewById(R.id.shimmerLayout);
                image = itemView.findViewById(R.id.image);
                place_name = itemView.findViewById(R.id.place_name);
//                food_detail = itemView.findViewById(R.id.food_detail);
                cardView = itemView.findViewById(R.id.cardView);
            }
    }

}





