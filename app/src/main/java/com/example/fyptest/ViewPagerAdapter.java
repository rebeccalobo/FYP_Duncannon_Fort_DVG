package com.example.fyptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    Context mCtx;
    LayoutInflater mInflater;

    public ViewPagerAdapter(Context ctx){
        this.mCtx = ctx;
    }

    public int[] images = {
            R.mipmap.compass,
            R.mipmap.history,
            R.mipmap.fort,
    };

    public int[] descriptions = {
            R.string.Compass,
           R.string.Fort,
           R.string.History,

    };
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    public Object instantiateItem(ViewGroup cont, int pos){
        mInflater = (LayoutInflater) mCtx.getSystemService(mCtx.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.intro_swiper_layout,cont,false);
        ImageView mImage = view.findViewById(R.id.swiper_image);
        TextView mDesc = view.findViewById(R.id.swiper_description);
        mImage.setImageResource(images[pos]);
        mDesc.setText(descriptions[pos]);
        cont.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup cont, int pos, Object o){
        cont.removeView((RelativeLayout)o);
    }
}
