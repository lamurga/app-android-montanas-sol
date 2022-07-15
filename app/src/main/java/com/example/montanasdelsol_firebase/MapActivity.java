package com.example.montanasdelsol_firebase;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    BottomNavigationView bNavigation;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        setup();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);

        LatLng company = new LatLng(-35.4784524, -69.5856184);
        map.addMarker(new MarkerOptions().position(company).title("Chocolates"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(company, 16));
    }

    private void setup(){
        bNavigation = findViewById(R.id.bNavigation);
        bNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_1){
                    Intent intent = new Intent(MapActivity.this,HomeActivity.class);
                    startActivity(intent);
                    item.setChecked(true);
                    return true;
                }
                if (item.getItemId() == R.id.menu_2){
                    Intent intent = new Intent(MapActivity.this,SearchActivity.class);
                    startActivity(intent);
                    item.setChecked(true);
                    return true;
                }
                if (item.getItemId() == R.id.menu_5){
                    Intent intent = new Intent(MapActivity.this,WebViewActivity.class);
                    startActivity(intent);
                    item.setChecked(true);
                    return true;
                }
                return false;
            }
        });
    }
}