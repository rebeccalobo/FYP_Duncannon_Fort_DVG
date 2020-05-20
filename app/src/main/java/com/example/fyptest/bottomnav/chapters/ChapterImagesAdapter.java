package com.example.fyptest.bottomnav.chapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.fyptest.R;

public class ChapterImagesAdapter extends BaseAdapter {

    private final Context mCtx;


    public ChapterImagesAdapter(Context ctx){
        this.mCtx = ctx;
    }

    public int[] chapter_bridge = {
            R.mipmap.cannon3
    };
    public int[] chapter_cannon = {
            R.mipmap.cannon2,
            R.mipmap.cannon3
    };
    public int[] chapter_courtyard = {
            R.mipmap.court,
            R.mipmap.court2
    };
    public int[] chapter_naval = {
            R.mipmap.defenses,
            R.mipmap.defenses2,
            R.mipmap.naval,
            R.mipmap.naval2,
            R.mipmap.naval3,
            R.mipmap.naval4,
            R.mipmap.naval5

    };
    public int[] chapter_lighthouse = {
            R.mipmap.lighthouse_image,
            R.mipmap.lighthouse_image_2,
            R.mipmap.lighthouse3
    };

    public ChapterImagesAdapter(ChaptersViewPagerAdapter mCtx, Context mCtx1) {

        this.mCtx = mCtx1;
    }

    @Override
    public int getCount() {
        return chapter_naval.length;
    }

    @Override
    public Object getItem(int i) {
        return chapter_naval[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView iv = new ImageView(mCtx);

//        ViewPager myViewPager = viewGroup.findViewById(R.id.myViewPager);
//
//        int page = myViewPager.getCurrentItem();
//
//        if(page == 0){
//            iv.setImageResource(chapter_bridge[i]);
//        }
//        else{
            iv.setImageResource(chapter_naval[i]);
//        }

        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new GridView.LayoutParams(250, 250));

        return iv;
    }


}
