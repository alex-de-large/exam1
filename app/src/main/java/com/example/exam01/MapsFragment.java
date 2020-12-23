package com.example.exam01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exam01.api.Data;
import com.example.exam01.model.Car;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {

    private GoogleMap map;
    private List<Car> cars;
    private ArrayList<Marker> carMarkers;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng pos = new LatLng(56.248944, 43.979419);
            googleMap.addMarker(new MarkerOptions().position(pos));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

            map = googleMap;

            cars = Data.get().getCars();
            carMarkers = new ArrayList<>();
            if (cars != null) {
                for (Car car : cars) {
                    Marker marker = googleMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_yellow))
                            .anchor(0.5f, 1)
                            .position(new LatLng(Double.parseDouble(car.getLat()), Double.parseDouble(car.getLon()))));
                    carMarkers.add(marker);
                }
            }

            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    for (int i = 0; i < carMarkers.size(); i++) {
                        Marker m = carMarkers.get(i);
                        if (m.equals(marker)) {
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car_purple));
                        } else {
                            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car_yellow));
                        }
                    }
                    return true;
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}