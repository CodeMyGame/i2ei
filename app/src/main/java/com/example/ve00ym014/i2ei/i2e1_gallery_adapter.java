package com.example.ve00ym014.i2ei;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class i2e1_gallery_adapter extends RecyclerView.Adapter<i2e1_gallery_adapter.UserViewHolder> {
    Context context;
    List<String> users;
    int width;

    i2e1_gallery_adapter(List<String> pics,Context context,int width)
    {
        this.context=context;
        this.users=pics;
        this.width=width;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_i2e1, parent, false);
        UserViewHolder bvh = new UserViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            Picasso.with(context).load("https"+users.get(position).substring(4)).resize((int)width/2, (int) (width/2.2)).placeholder(R.drawable.icons90).into(holder.gallery);

    }


    @Override
    public int getItemCount()
    {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView gallery;



        UserViewHolder(View itemView) {
            super(itemView);

            gallery=(ImageView)itemView.findViewById(R.id.gallery);


        }
    }

}
