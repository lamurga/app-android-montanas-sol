package com.example.montanasdelsol_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnNew;
    // ListView lstProduct;
    RecyclerView rvProduct;

    FirebaseDatabase database;
    DatabaseReference reference;
    private List<Product> listProduct = new ArrayList<>();
    //ArrayAdapter<Product> productArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assigneeReference();
        initializeFirebase();
        showData();
    }

    private void initializeFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    private void showData() {
        reference.child("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listProduct.clear();
                for(DataSnapshot item: snapshot.getChildren()){
                    Product p = item.getValue(Product.class);
                    listProduct.add(p);
                }
                // productArrayAdapter = new ArrayAdapter<Product>(MainActivity.this, android.R.layout.simple_list_item_1,listProduct);
                // lstProduct.setAdapter(productArrayAdapter);
                CustomAdapter adapter = new CustomAdapter(MainActivity.this, listProduct);
                rvProduct.setAdapter(adapter);
                rvProduct.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void assigneeReference() {
        btnNew = findViewById(R.id.btnNew);
        // lstProduct = findViewById(R.id.lstProduct);
        rvProduct = findViewById(R.id.rvProduct);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}