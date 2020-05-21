package com.example.fyptest.bottomnav.map;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyptest.R;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.List;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<SingleRecyclerViewLocation> locationList;
    private MapboxMap map;

    public RecyclerViewAdapter(List<SingleRecyclerViewLocation> locationList, MapboxMap map) {
        this.locationList = locationList;
        this.map = map;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_symbol_layer, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SingleRecyclerViewLocation singleRecyclerViewLocation = locationList.get(position);
        holder.chapter.setText("Chapter " + singleRecyclerViewLocation.getChapter());
        holder.name.setText(singleRecyclerViewLocation.getName());
        holder.description.setText(singleRecyclerViewLocation.getDescription());
        holder.setIsRecyclable(false);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                LatLng selectedLocationLatLng = locationList.get(position).getLocationCoordinates();
                CameraPosition newCameraPosition = new CameraPosition.Builder()
                        .target(selectedLocationLatLng)
                        .build();
                map.easeCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition));
            }


        });

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }





    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView chapter;
        TextView name;
        TextView description;
        CardView singleCard;
        Button rvButton;
        ItemClickListener clickListener;


        MyViewHolder(View view){
            super(view);
            chapter = view.findViewById(R.id.textview_chapter);
            name = view.findViewById(R.id.textview_name);
            description = view.findViewById(R.id.textview_description);
            rvButton = view.findViewById(R.id.button_start);
            rvButton.setOnClickListener(view1 -> {
                Navigation.findNavController(view1).navigate(R.id.navigation_dashboard);

            });
            singleCard = view.findViewById(R.id.single_location_cardview);
            singleCard.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getLayoutPosition());
        }





    }
}