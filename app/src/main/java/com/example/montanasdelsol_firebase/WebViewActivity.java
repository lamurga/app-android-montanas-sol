package com.example.montanasdelsol_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class WebViewActivity extends AppCompatActivity {

    BottomNavigationView bNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView myWebView = (WebView) findViewById(R.id.webView1);
        myWebView.loadUrl("https://quierochocolate.com/blog/");
        setup();
    }

    private void setup(){
        bNavigation = findViewById(R.id.bNavigation);
        bNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_1){
                    Intent intent = new Intent(WebViewActivity.this,HomeActivity.class);
                    startActivity(intent);
                    item.setChecked(true);
                }
                if (item.getItemId() == R.id.menu_2){
                    Intent intent = new Intent(WebViewActivity.this,SearchActivity.class);
                    startActivity(intent);
                    item.setChecked(true);
                }
                if (item.getItemId() == R.id.menu_4){
                    Intent intent = new Intent(WebViewActivity.this,MapActivity.class);
                    startActivity(intent);
                    item.setChecked(true);
                }
                return false;
            }
        });
    }


}