package com.example.montanasdelsol_firebase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    EditText txtName, txtCategory, txtDescription, txtPrice;
    Button btnRegister;

    FirebaseDatabase database;
    DatabaseReference reference;

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        assigneeReference();
        initializeFirebase();
    }

    private void initializeFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    private void assigneeReference() {
        txtName = findViewById(R.id.txtName);
        txtCategory = findViewById(R.id.txtCategory);
        txtDescription = findViewById(R.id.txtDescription);
        txtPrice = findViewById(R.id.txtPrice);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (captureData()){
                    reference.child("Product").child(product.getId()).setValue(product);
                    AlertDialog.Builder window = new AlertDialog.Builder(RegisterActivity.this);
                    window.setTitle("Mensaje Informativo");
                    window.setMessage("Se inserto el producto");
                    window.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    window.create().show();
                }
            }
        });
    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }
    private boolean captureData() {
        String name = txtName.getText().toString();
        String category = txtCategory.getText().toString();
        String description = txtDescription.getText().toString();
        double price = ParseDouble(txtPrice.getText().toString());
        boolean valid = true;
        try{
            if (name.equals("")){
                txtName.setError("Nombre es obligatorio");
                valid = false;
            }
            if (category.equals("")){
                txtCategory.setError("Categoria es obligatorio");
                valid = false;
            }
            if (description.equals("")){
                txtDescription.setError("Descripcion es obligatorio");
                valid = false;
            }
            if (price < 1) {
                txtPrice.setError("Precio es obligatorio");
                valid = false;
            }
        }catch (Exception e){
            valid = false;
        }

        if (valid){
            product = new Product();
            product.setId(UUID.randomUUID().toString());
            product.setName(name);
            product.setCategory(category);
            product.setPresentation(description);
            product.setPrice(price);
        }
        return valid;
    }
}