package com.example.wisienka.mobileapplication.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.wisienka.mobileapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wisienka on 2018-04-22.
 */

public class MapTabFragment extends Fragment implements OnMapReadyCallback {
    private static final String LOGTAG = "MapFragment";
    protected FloatingActionButton fab;
    private GoogleMap map;
    protected MapTabMode mode;
    private static Map<MapTabMode,MapTabMode> ModeMap;
    private static Map<MapTabMode,Integer> IconMap;
    private List<LatLng> currentlyDrawnPolygon;
    private List<Polygon> drawnPolygons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        //mContext = getActivity();
        mode = MapTabMode.idleMode;
        return inflater.inflate(R.layout.map_tab_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.map_container, mapFragment, "mapFragment");
            ft.commit();
            fm.executePendingTransactions();

            mapFragment.getMapAsync(this);

            FrameLayout map_container = view.findViewById(R.id.map_layout);
            map_container.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mode == MapTabMode.idleMode)
                        return false;

                    float x = event.getX();
                    float y = event.getY();

                    int x_co = Math.round(x);
                    int y_co = Math.round(y);

                    Projection projection = map.getProjection();
                    Point x_y_points = new Point(x_co, y_co);

                    LatLng latLng = projection.fromScreenLocation(x_y_points);
                    double latitude = latLng.latitude;

                    double longitude = latLng.longitude;

                    int eventaction = event.getAction();
                    switch (eventaction) {
                        case MotionEvent.ACTION_DOWN:
                            // finger touches the screen
                            currentlyDrawnPolygon.add(new LatLng(latitude, longitude));
                            break;
                        case MotionEvent.ACTION_MOVE:
                            // finger moves on the screen
                            currentlyDrawnPolygon.add(new LatLng(latitude, longitude));
                            break;
                        case MotionEvent.ACTION_UP:
                            // finger leaves the screen
                            v.performClick();
                            Draw_Map();
                            break;
                    }

                    return true;

                }
            });
        }

        currentlyDrawnPolygon = new ArrayList<LatLng>();
        drawnPolygons = new ArrayList<Polygon>();

        if (ModeMap == null || IconMap == null){
            ModeMap = new HashMap<MapTabMode,MapTabMode>();
            IconMap = new HashMap<MapTabMode,Integer>();
            ModeMap.put(MapTabMode.idleMode, MapTabMode.drawingAreaMode);
            ModeMap.put(MapTabMode.drawingAreaMode, MapTabMode.idleMode);
            IconMap.put(MapTabMode.idleMode, R.drawable.create_icon);
            IconMap.put(MapTabMode.drawingAreaMode, R.drawable.done_icon);
        }
        if (fab == null){
            fab = view.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mode = ModeMap.get(mode);
                    int icon_id = IconMap.get(mode);
                    fab.setImageDrawable(ContextCompat.getDrawable(getContext(), icon_id));
                }
            });
            fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.create_icon));
        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
//        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
        //map.setMyLocationEnabled(true);


        Log.v(LOGTAG, "map is ready");

        LatLng warsaw = new LatLng(52.237049,21.017532);
        map.addMarker(new MarkerOptions().position(warsaw).title("Marker in Warsaw"));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(warsaw)             // Sets the center of the map to Mountain View
                .zoom(12)                    // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        Log.v(LOGTAG, "Added Warsaw");
    }


    private void Draw_Map() {
        PolygonOptions rectOptions = new PolygonOptions();
        rectOptions.addAll(currentlyDrawnPolygon);
        rectOptions.strokeColor(Color.BLUE);
        rectOptions.strokeWidth(7);
        rectOptions.fillColor(Color.argb(70, 0, 255, 255));
        currentlyDrawnPolygon.clear();
        Polygon polygon = map.addPolygon(rectOptions);
        drawnPolygons.add(polygon);
    }

    public enum MapTabMode{
        idleMode,
        drawingAreaMode
    }
}
