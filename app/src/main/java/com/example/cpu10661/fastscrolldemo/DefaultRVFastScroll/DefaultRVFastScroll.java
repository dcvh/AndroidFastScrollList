package com.example.cpu10661.fastscrolldemo.DefaultRVFastScroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.ContactAdapter;
import com.example.cpu10661.fastscrolldemo.MainActivity;
import com.example.cpu10661.fastscrolldemo.R;

import java.util.ArrayList;

public class DefaultRVFastScroll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_rv_fast_scroll);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adapter
        ArrayList<Contact> contacts = getIntent() != null
                ? getIntent().<Contact>getParcelableArrayListExtra(MainActivity.ARG_CONTACTS_LIST)
                : new ArrayList<Contact>();
//        ArrayList<Contact> contacts = Utils.generateContactsList(1000);
        ContactAdapter adapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(adapter);

        // reduce dropped frames problem
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, contacts.size());
//        recyclerView.setItemViewCacheSize(contacts.size());
//        recyclerView.setDrawingCacheEnabled(true);
//        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }
}
