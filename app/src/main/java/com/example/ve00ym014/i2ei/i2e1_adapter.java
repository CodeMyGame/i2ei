package com.example.ve00ym014.i2ei;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

public class i2e1_adapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<user> users;
    int width;
    i2e1_adapter(List<user> users,Context context,int width)
    {
        this.context=context;
      this.users=users;
       this.width=width;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v;
            if(viewType==0) {
                 v = LayoutInflater.from(parent.getContext()).inflate(R.layout.i2e1_even_row, parent, false);
                UserViewHolder bvh = new UserViewHolder(v);
                return bvh;
            }
            else {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.i2e1_odd_row, parent, false);
                UserViewHolder1 bvh = new UserViewHolder1(v);
                return bvh;
            }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        if (itemType==0) {
            UserViewHolder uvh=(UserViewHolder)holder;
            uvh.userName.setText(users.get(position).getUserName());
            Log.d("image", users.get(position).getDp_url());
            Picasso.with(context).load("https"+users.get(position).getDp_url().substring(4)).resize(120, 120).transform(new CircleTransform()).placeholder(R.drawable.icons30).into(uvh.dp);
            GridLayoutManager llm = new GridLayoutManager(context, 2);
            uvh.pics.setLayoutManager(llm);
            List<String> a = new ArrayList<>();
            a = users.get(position).getPics_urls();
            uvh.pics.setAdapter(new i2e1_gallery_adapter(a, context, width));
        }
        else {
            UserViewHolder1 uvh=(UserViewHolder1)holder;
            uvh.userName.setText(users.get(position).getUserName());
            Log.d("image", "https"+users.get(position).getDp_url().substring(4));
            String u="https"+users.get(position).getDp_url().substring(4);
            Picasso.with(context).load(u).resize(120, 120).transform(new CircleTransform()).placeholder(R.drawable.icons30).into(uvh.dp);
            Picasso.with(context).load("https"+users.get(position).getPics_urls().get(0).substring(4)).resize(width, (int) (width/2.2)).placeholder(R.drawable.icons90).into(uvh.pics1);
            GridLayoutManager llm = new GridLayoutManager(context, 2);
            uvh.pics.setLayoutManager(llm);
            List<String> a = new ArrayList<>();
            List<String> b = new ArrayList<>();
            a = users.get(position).getPics_urls();
            for(int i=1;i<a.size();i++)
                b.add(a.get(i));
            uvh.pics.setAdapter(new i2e1_gallery_adapter(b, context, width));
        }
    }




    @Override
    public int getItemCount()
    {
        return users.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (users.get(position).getPics_urls().size()%2==0) {
            return 0;
        } else {
            return 1;
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView dp;
        RecyclerView pics;


        UserViewHolder(View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.username);
            dp=(ImageView)itemView.findViewById(R.id.dp);
            pics=(RecyclerView)itemView.findViewById(R.id.pics);

        }
    }
    public static class UserViewHolder1 extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView dp,pics1;
        RecyclerView pics;


        UserViewHolder1(View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.username);
            dp=(ImageView)itemView.findViewById(R.id.dp);
            pics=(RecyclerView)itemView.findViewById(R.id.pics);
            pics1=(ImageView)itemView.findViewById(R.id.pic1);


        }
    }
    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

}
