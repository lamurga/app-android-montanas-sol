package com.example.montanasdelsol_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView rvProduct;

    FirebaseDatabase database;
    DatabaseReference reference;
    private List<Product> listProduct = new ArrayList<>();
    BottomNavigationView bNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeFirebase();
        showData();
        setup();
    }

    private void initializeFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    private void setup(){
        bNavigation = findViewById(R.id.bNavigation);
        bNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_1){
                    Intent intent = new Intent(SearchActivity.this,HomeActivity.class);
                    startActivity(intent);
                    item.setChecked(true);
                }
                if (item.getItemId() == R.id.menu_4){
                    Intent intent = new Intent(SearchActivity.this,MapActivity.class);
                    startActivity(intent);
                    item.setChecked(true);
                }
                if (item.getItemId() == R.id.menu_5){
                    Intent intent = new Intent(SearchActivity.this,WebViewActivity.class);
                    startActivity(intent);
                    item.setChecked(true);
                }
                return false;
            }
        });
    }

    private void showData() {
        rvProduct = findViewById(R.id.rvProductSearch);
        reference.child("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listProduct.clear();
                for(DataSnapshot item: snapshot.getChildren()){
                    Product p = item.getValue(Product.class);
                    listProduct.add(p);
                }
                CustomAdapter adapter = new CustomAdapter(SearchActivity.this, listProduct);
                rvProduct.setAdapter(adapter);
                rvProduct.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}