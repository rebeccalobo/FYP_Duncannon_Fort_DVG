package com.example.fyptest.bottomnav.map;
import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import com.example.fyptest.R;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.GeometryCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mapbox.mapboxsdk.offline.OfflineRegionError;
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;
import static com.mapbox.mapboxsdk.plugins.offline.ui.RegionSelectionFragment.TAG;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;

public class MapFragment extends Fragment {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private LatLngBounds latLngBounds;
    private OfflineManager offlineManager;
    private String JSON_CHARSET = "UTF-8";
    private static final String visitor_centre = "information-15";
    private static final String entrance = "entrance-15";
    private String JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME";
    private static final String hotspot = "MARKER_IMAGE_ID";
    private String UNIQUE_STYLE_JSON = "style.json";
    private static final String SYMBOL_ICON_ID = "SYMBOL_ICON_ID";
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_SELECTED = "selected";
    private FeatureCollection featureCollection;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter locationAdapter;
    private List<SingleRecyclerViewLocation> locationList;
    private static final String PROPERTY_NAME = "name";
    private List<Feature> featureList;

    private HashMap<Integer, LatLng> hotspot_coordinates = new HashMap<Integer, LatLng>()
    {{

        put(1, new LatLng(52.220981660099426, -6.936063118323176));
        put(2, new LatLng(52.22118175829205, -6.93624326952629));
        put(3, new LatLng(52.22078164956724, -6.936723769022336));
        put(4, new LatLng(52.2206369355016, -6.936650790165885));
        put(5, new LatLng(52.220407509797525, -6.937396931973126));

    }};
    private LinearLayoutManager layout_manager;

    private void initFeatureCollection() {
        featureCollection = FeatureCollection.fromFeatures(new Feature[] {});
        featureList = new ArrayList<>();
        if (featureCollection != null) {
            // Obtaining an iterator for the entry set
            Set entrySet = hotspot_coordinates.entrySet();
            Iterator it = entrySet.iterator();

            // Iterate through HashMap entries(Key-Value pairs)
            System.out.println("HashMap Key-Value Pairs : ");
            while(it.hasNext()){
                Map.Entry me = (Map.Entry)it.next();
                System.out.println("Key is: "+me.getKey() +
                        " & " +
                        " value is: "+me.getValue());
                LatLng latLng = (LatLng)me.getValue();
                featureList.add(Feature.fromGeometry(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude())));
            }
            featureCollection = FeatureCollection.fromFeatures(featureList);
        }
    }
    private void initMarkerIcons(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage(SYMBOL_ICON_ID, BitmapFactory.decodeResource(
                this.getResources(), R.drawable.red_marker));
        loadedMapStyle.addSource(new GeoJsonSource(SOURCE_ID, featureCollection));
        loadedMapStyle.addLayer(new SymbolLayer(LAYER_ID, SOURCE_ID).withProperties(
                iconImage(SYMBOL_ICON_ID),
                iconAllowOverlap(true),
                iconSize(.3f),
                iconOffset(new Float[] {0f, -4f})
        ));
    }

    private void initRecyclerView() {
        layout_manager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layout_manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(locationAdapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    private List<SingleRecyclerViewLocation> createRecyclerViewLocations() {
        ArrayList<SingleRecyclerViewLocation> locationList = new ArrayList<>();
        String[] nameArray = getResources().getStringArray(R.array.name_array);
        String[] descArray = getResources().getStringArray(R.array.desc_array);

        Set entrySet = hotspot_coordinates.entrySet();
        Iterator it = entrySet.iterator();
        int x = 0;
        // Iterate through HashMap entries(Key-Value pairs)
        System.out.println("HashMap Key-Value Pairs : ");
        while(it.hasNext()){
            Map.Entry me = (Map.Entry)it.next();
            SingleRecyclerViewLocation singleLocation = new SingleRecyclerViewLocation();
            singleLocation.setChapter((Integer) me.getKey());
            singleLocation.setName(nameArray[x]);
            singleLocation.setDescription(descArray[x]);
            singleLocation.setLocationCoordinates((LatLng) me.getValue());
            locationList.add(singleLocation);
            x++;
        }
        locationList.sort(Comparator.comparing(SingleRecyclerViewLocation::getChapter));
        return locationList;
    }

    private void setUpImage(@NonNull Style loadedStyle) {
        loadedStyle.addImage(hotspot, BitmapFactory.decodeResource(
                this.getResources(), R.drawable.red_marker));
    }

    @SuppressLint("LogNotTimber")
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Mapbox.getInstance(getActivity(), getString(R.string.access_token));
        // MapViewModel mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        // This contains the MapView in XML and needs to be called after the access token is configured.
        mapView = root.findViewById(R.id.mapView);
        recyclerView = root.findViewById(R.id.rv_on_map);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.OUTDOORS, style -> {

            this.mapboxMap = mapboxMap;
            mapboxMap.addOnMapClickListener(point -> {
                PointF screenPoint = mapboxMap.getProjection().toScreenLocation(point);
                Log.i("Point","(" + screenPoint.x + "," + screenPoint.y + ")");

                Set entrySet = hotspot_coordinates.entrySet();
                Iterator it = entrySet.iterator();
                int x = 0;
                // Iterate through HashMap entries(Key-Value pairs)
                while(it.hasNext()){
                    Map.Entry me = (Map.Entry)it.next();
                    LatLng hotspot = (LatLng) me.getValue();
                    PointF hotspotpoint = mapboxMap.getProjection().toScreenLocation(hotspot);
                    Double start_x = screenPoint.x - 30.0;
                    Double end_x = screenPoint.x + 30.0;
                    Double start_y = screenPoint.y - 50.0;
                    Double end_y = screenPoint.y + 50.0;
                    Float hotspotpoint_x = hotspotpoint.x;
                    Float hotspotpoint_y = hotspotpoint.y;
                    if(hotspotpoint_x <= end_x && hotspotpoint_x >= start_x && hotspotpoint_y <= end_y && hotspotpoint_y >= start_y){
                        Toast.makeText(getActivity(),
                                "Successfully clicked icon", Toast.LENGTH_SHORT).show();
                        SingleRecyclerViewLocation singleLocation = new SingleRecyclerViewLocation();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        Double lat_hotspot = ((LatLng) me.getValue()).getLatitude();
                        Double long_hotspot = ((LatLng) me.getValue()).getLongitude();
                        LatLng selectedLocationLatLng = (LatLng) me.getValue();
                        CameraPosition newCameraPosition = new CameraPosition.Builder()
                                .target(selectedLocationLatLng)
                                .build();
                        mapboxMap.easeCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition));
                        layout_manager.scrollToPosition((Integer) me.getKey()-1);


                    }
                }

                return true;
            });

            locationAdapter = new RecyclerViewAdapter(createRecyclerViewLocations(), mapboxMap);
            initFeatureCollection();
            initMarkerIcons(style);
            initRecyclerView();
            setUpImage(style);
            SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style);
            symbolManager.addClickListener(symbol -> {
                symbolManager.update(symbol);
                Toast.makeText(getActivity(),
                        getString(R.string.clicked_symbol_toast), Toast.LENGTH_SHORT).show();
            });
            symbolManager.addLongClickListener(symbol -> {
                Toast.makeText(getActivity(),
                        getString(R.string.long_clicked_symbol_toast), Toast.LENGTH_SHORT).show();
            });

            // set non-data-driven properties, such as:
            symbolManager.setIconAllowOverlap(true);
            symbolManager.setTextAllowOverlap(true);

            // Add symbols at specified lat/lon
            Symbol visitor_cent = symbolManager.create(new SymbolOptions()
                    .withLatLng(new LatLng(52.221097, -6.936182))
                    .withIconImage(visitor_centre)
                    .withIconSize(1.0f)
                    .withIconAnchor("bottom")
                    .withTextField("Welcome Centre")
                    .withTextFont(new String[]{"DIN Offc Pro Regular", "Arial Unicode MS Regular"})
                    .withTextSize(11.0f)
                    .withTextAnchor("top")
            );

            Symbol stairs= symbolManager.create(new SymbolOptions()
                    .withLatLng(new LatLng(52.221082, -6.936182))
                    .withIconImage(entrance)
                    .withIconSize(1.5f)
                    .withIconAnchor("bottom")
                    .withTextField("Stairs")
                    .withTextFont(new String[]{"DIN Offc Pro Regular", "Arial Unicode MS Regular"})
                    .withTextSize(11.0f)
                    .withTextAnchor("top")
            );

            BuildingPlugin buildingPlugin = new BuildingPlugin(mapView, mapboxMap, style);
            buildingPlugin.setVisibility(true);
            buildingPlugin.setColor(getResources().getColor(R.color.colorHighlight));
            buildingPlugin.setOpacity(0.2f);

            latLngBounds = new LatLngBounds.Builder()
                    .include(new LatLng(52.2214, -6.9354)) // Northeast
                    .include(new LatLng(52.2204, -6.9382)) // Southwest
                    .build();

            mapboxMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0));
            mapboxMap.setLatLngBoundsForCameraTarget(latLngBounds);
            mapboxMap.setMinZoomPreference(16);
            mapboxMap.setMaxZoomPreference(20);
            mapboxMap.getUiSettings().setCompassEnabled(true);
            mapboxMap.getUiSettings().setLogoEnabled(false);
            mapboxMap.getUiSettings().setAttributionEnabled(false);
            mapboxMap.getUiSettings().setCompassFadeFacingNorth(false);
            mapboxMap.getUiSettings().setTiltGesturesEnabled(false);

            offlineManager = OfflineManager.getInstance(getActivity());
            OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
                    Style.MAPBOX_STREETS,
                    latLngBounds,
                    mapboxMap.getMinZoomLevel(),
                    mapboxMap.getMaxZoomLevel(),
                    MapFragment.this.getResources().getDisplayMetrics().density);

            byte[] metadata;
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Duncannon Fort", JSON_FIELD_REGION_NAME);
                String json = jsonObject.toString();
                metadata = json.getBytes(JSON_CHARSET);
            } catch (Exception exception) {
                Log.e(TAG, "Failed to encode metadata: " + exception.getMessage());
                metadata = null;
            }

            // Create the region asynchronously
            offlineManager.createOfflineRegion(definition, metadata,
                    new OfflineManager.CreateOfflineRegionCallback() {
                        @Override
                        public void onCreate(OfflineRegion offlineRegion) {
                            offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE);

                            // Monitor the download progress using setObserver
                            offlineRegion.setObserver(new OfflineRegion.OfflineRegionObserver() {
                                @Override
                                public void onStatusChanged(OfflineRegionStatus status) {

                                    // Calculate the download percentage
                                    double percentage = status.getRequiredResourceCount() >= 0
                                            ? (100.0 * status.getCompletedResourceCount() / status.getRequiredResourceCount()) :
                                            0.0;

                                    if (status.isComplete()) {
                                        // Download complete
                                        Log.d(TAG, "Region downloaded successfully.");
                                    } else if (status.isRequiredResourceCountPrecise()) {
                                        Log.d(TAG, String.valueOf(percentage));
                                    }
                                }

                                @Override
                                public void onError(OfflineRegionError error) {
                                    // If an error occurs, print to logcat
                                    Log.e(TAG, "onError reason: " + error.getReason());
                                    Log.e(TAG, "onError message: " + error.getMessage());
                                }

                                @Override
                                public void mapboxTileCountLimitExceeded(long limit) {
                                    // Notify if offline region exceeds maximum tile count
                                    Log.e(TAG, "Mapbox tile count limit exceeded: " + limit);
                                }
                            });
                        }

                        @Override
                        public void onError(String error) {
                            Log.e(TAG, "Error: " + error);
                        }
                    });

        }));

        return root;
    }
}
