package com.example.fyptest.bottomnav.chapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.example.fyptest.HomeActivity;
import com.example.fyptest.R;
import com.google.zxing.integration.android.IntentIntegrator;

public class ChaptersViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

    Context mCtx;
    String[] nameArray, descArray, contentArray;
    LayoutInflater mInflater;

    public ChaptersViewPagerAdapter(Context ctx){
        this.mCtx = ctx;
    }

    public int[] images = {
            R.mipmap.bridge,
            R.mipmap.cannon,
            R.mipmap.knight,
            R.mipmap.transport,
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


    @RequiresApi(api = Build.VERSION_CODES.P)
    public Object instantiateItem(ViewGroup cont, int pos){
        nameArray = mCtx.getResources().getStringArray(R.array.name_array);
        descArray = mCtx.getResources().getStringArray(R.array.desc_array);
        contentArray = mCtx.getResources().getStringArray(R.array.content_array);




        mInflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.chapters_swiper_layout,cont,false);
        view.setBackgroundColor(Color.WHITE);

        ImageView mImage = view.findViewById(R.id.swiper_image);
        GridView gv = view.findViewById(R.id.grid);
        TextView mName = view.findViewById(R.id.swiper_name);
        TextView mDesc = view.findViewById(R.id.swiper_desc);
        TextView mContent = view.findViewById(R.id.swiper_content);
        Button btnScanBarcode = view.findViewById(R.id.btnScanBarcode);

        mImage.setImageResource(images[pos]);
        gv.setAdapter(new ChapterImagesAdapter(mCtx));
        mName.setText(nameArray[pos]);
        mDesc.setText(descArray[pos]);
        mContent.setText(contentArray[pos]);

        mName.setTextColor(Color.BLACK);
        mDesc.setTextColor(Color.BLACK);
        mContent.setTextColor(Color.BLACK);

        cont.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup cont, int pos, Object o){
        cont.removeView((RelativeLayout) o);
    }
    @Override
    public void onClick(View v) {
        new IntentIntegrator(new Activity()).initiateScan();

    }
}

