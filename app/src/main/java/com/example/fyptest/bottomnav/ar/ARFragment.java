package com.example.fyptest.bottomnav.ar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fyptest.R;
import com.example.fyptest.bottomnav.chapters.ChapterImagesAdapter;


public class ARFragment extends Fragment {

    private GridView gv;
    private Context ctx;

    public ARFragment() {
        this.ctx = ctx;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ar, container, false);
        gv = root.findViewById(R.id.grid_ar);
        gv.setAdapter(new ARAdapter(getActivity()));
        gv.setOnHoverListener((view, motionEvent) -> {
            Toast.makeText(getActivity(),
                    "grid hover works", Toast.LENGTH_SHORT).show();
            return false;
        });



        return root;
    }



}