package com.example.fyptest.bottomnav.chapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.fyptest.R;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;


public class ChaptersFragment extends Fragment {


    private ViewPager myViewPager;
    private ChaptersViewPagerAdapter pagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chapters, container, false);
        myViewPager = root.findViewById(R.id.myViewPager);
        pagerAdapter = new ChaptersViewPagerAdapter(this.getActivity());
        myViewPager.setAdapter(pagerAdapter);
        myViewPager.setClipToPadding(true);

        // Create an object of page transformer
        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
        // Enable / Disable scaling while flipping. If true, then next page will scale in (zoom in). By default, its true.
        bookFlipPageTransformer.setEnableScale(true);
        // The amount of scale the page will zoom. By default, its 5 percent.
        bookFlipPageTransformer.setScaleAmountPercent(10f);
        // Assign the page transformer to the ViewPager.
        myViewPager.setPageTransformer(true, bookFlipPageTransformer);
        return root;
    }
}

