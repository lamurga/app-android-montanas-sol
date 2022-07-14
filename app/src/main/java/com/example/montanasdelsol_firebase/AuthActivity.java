package com.example.montanasdelsol_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AuthActivity extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    Button btnRegisterUser, btnLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();

        setup();
    }

    private void setup() {
        txtEmail = findViewById(R.id.textEmail);
        txtPassword = findViewById(R.id.textPassword);
        btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnLogin = findViewById(R.id.btnLogin);

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()){
                    mAuth.createUserWithEmailAndPassword(
                            txtEmail.getText().toString(),
                            txtPassword.getText().toString()
                    ).addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null){
                                    showMain(user.getEmail());
                                }
                            }else{
                                showAlert("El usuario ya existe y/o no se pudo registrar");
                            }
                        }
                    });
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()){
                    mAuth.signInWithEmailAndPassword(
                            txtEmail.getText().toString(),
                            txtPassword.getText().toString()
                    ).addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null){
                                    showMain(user.getEmail());
                                }
                            }else{
                                showAlert("No se pudo ingresar");
                            }
                        }
                    });
                }
            }
        });

    }

    private void showMain(String email){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("provider", ProviderType.BASIC.name());
        startActivity(intent);
    }

    private void showAlert(String message){
        AlertDialog.Builder window = new AlertDialog.Builder(AuthActivity.this);
        window.setTitle("Mensaje Informativo");
        window.setMessage(message);
        window.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        window.create().show();
    }

    private boolean validateData() {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        boolean valid = true;
        try{
            if (email.equals("")){
                txtEmail.setError("Es obligatorio");
                valid = false;
            }
            if (password.equals("")){
                txtPassword.setError("Es obligatorio");
                valid = false;
            }
        }catch (Exception e){
            valid = false;
        }

        return valid;
    }
}