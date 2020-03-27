package com.example.fyptest.bottomnav.chapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fyptest.R;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChaptersFragment extends Fragment {


    private ViewPager myViewPager;
    private ViewPagerAdapter2 pagerAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chapters, container, false);
        myViewPager = root.findViewById(R.id.myViewPager);
        pagerAdapter = new ViewPagerAdapter2(this.getActivity());
        myViewPager.setAdapter(pagerAdapter);
        myViewPager.setClipToPadding(true);

        // Create an object of page transformer
        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();

        // Enable / Disable scaling while flipping. If true, then next page will scale in (zoom in). By default, its true.
        bookFlipPageTransformer.setEnableScale(false);

        // The amount of scale the page will zoom. By default, its 5 percent.
        bookFlipPageTransformer.setScaleAmountPercent(10f);

        // Assign the page transformer to the ViewPager.
        myViewPager.setPageTransformer(true, bookFlipPageTransformer);




        return root;
    }

//    public static final class ChapterPagerAdapter extends FragmentPagerAdapter{
//        @NotNull
//        private ArrayList fragments;
//
//        public ChapterPagerAdapter(@NonNull FragmentManager fm, int behavior) {
//            super(fm, behavior);
//        }
//
//        @NotNull
//        public final ArrayList getFragments() {
//            return this.fragments;
//        }
//
//        public final void setFragments(@NotNull ArrayList var1) {
//
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public int getCount() {
//            return 0;
//        }
//    }
}