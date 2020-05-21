package com.example.fyptest.bottomnav.ar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fyptest.R;


public class ARAdapter extends BaseAdapter {
    private final Context mCtx;

    public ARAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }


    public int[] ar_images = {
            R.mipmap.cannon2,
            R.mipmap.defenses2,
            R.mipmap.defenses,
            R.mipmap.naval

    };
    public String[] ar_titles = {
            "Blast Off",
            "March in Time",
            "The Coast is Clear",
            "Battleships"
    };

    @Override
    public int getCount() {
        return ar_images.length;
    }

    @Override
    public Object getItem(int i) {
        return ar_images[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
            view = layoutInflater.inflate(R.layout.ar_grid_format, null);
        }
        ImageView iv = view.findViewById(R.id.image_ar);
        TextView nameTextView = view.findViewById(R.id.text_ar);

        iv.setImageResource(ar_images[i]);
        iv.setMaxHeight(390);
        nameTextView.setText(ar_titles[i]);
        nameTextView.setTextSize(20);


        return view;
    }
}


