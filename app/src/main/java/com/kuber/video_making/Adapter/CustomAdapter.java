package com.kuber.video_making.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kuber.video_making.Model.Images;
import com.kuber.video_making.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Images> images;

    public CustomAdapter(Context context, ArrayList<Images> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            //INFLATE CUSTOM LAYOUT
            view = LayoutInflater.from(context).inflate(R.layout.row_for_grid, viewGroup, false);
        }

        final Images s = (Images) this.getItem(i);

        TextView nameTxt = view.findViewById(R.id.nameTxt);
        ImageView img = view.findViewById(R.id.spacecraftImg);

        //BIND DATA
        nameTxt.setText(s.getName());
        Picasso.get().load(s.getUri()).placeholder(R.drawable.ic_launcher_background).into(img);


        //VIEW ITEM CLICK
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, s.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}