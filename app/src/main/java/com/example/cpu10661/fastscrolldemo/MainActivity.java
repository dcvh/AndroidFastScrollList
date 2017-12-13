package com.example.cpu10661.fastscrolldemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView.FastScrollRVActivity;
import com.example.cpu10661.fastscrolldemo.FastScroller.FastScrollerActivity;

public class MainActivity extends AppCompatActivity {

    private static final int RC_READ_CONTACTS_PERMISSION = 1;
    Intent mNextActivityIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fastScrollerButton = findViewById(R.id.btn_fast_scroller);
        fastScrollerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNextActivityIntent = new Intent(MainActivity.this, FastScrollerActivity.class);
                requestContactPermission();
            }
        });

        Button fastScrollRVButton = findViewById(R.id.btn_fast_scroll_rv);
        fastScrollRVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNextActivityIntent = new Intent(MainActivity.this, FastScrollRVActivity.class);
                requestContactPermission();
            }
        });
    }

    private void requestContactPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    RC_READ_CONTACTS_PERMISSION);
        } else {
            startActivity(mNextActivityIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_READ_CONTACTS_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(mNextActivityIntent);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
