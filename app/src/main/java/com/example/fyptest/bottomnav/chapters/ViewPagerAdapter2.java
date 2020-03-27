package com.example.fyptest.bottomnav.chapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.fyptest.R;

public class ViewPagerAdapter2 extends PagerAdapter {

    Context mCtx;
    String[] nameArray;
    LayoutInflater mInflater;

    public ViewPagerAdapter2(Context ctx){
        this.mCtx = ctx;
    }

    public int[] images = {
            R.mipmap.bridge,
            R.mipmap.cannon,
            R.mipmap.knight,
            R.mipmap.cannon,
            R.mipmap.lighthouse,
    };
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;

    }


    public View instantiateItem(ViewGroup cont, int pos){
        nameArray = mCtx.getResources().getStringArray(R.array.name_array);

        mInflater = (LayoutInflater) mCtx.getSystemService(mCtx.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.intro_swiper_layout,cont,false);
        ImageView mImage = view.findViewById(R.id.swiper_image);
        TextView mDesc = view.findViewById(R.id.swiper_description);
        mImage.setImageResource(images[pos]);
        mDesc.setText(nameArray[pos]);
        mDesc.setTextColor(Color.BLACK);
        cont.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup cont, int pos, Object o){
        cont.removeView((RelativeLayout) o);
    }
}
