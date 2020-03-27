package com.example.fyptest.bottomnav.map;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class SingleRecyclerViewLocation {

    private String name;
    private int chapter;
    private String description;
    private LatLng locationCoordinates;

    public int getChapter() { return chapter; }

    public void setChapter(int chapter) { this.chapter = chapter; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocationCoordinates() {
        return locationCoordinates;
    }

    public void setLocationCoordinates(LatLng locationCoordinates) { this.locationCoordinates = locationCoordinates; }
}
