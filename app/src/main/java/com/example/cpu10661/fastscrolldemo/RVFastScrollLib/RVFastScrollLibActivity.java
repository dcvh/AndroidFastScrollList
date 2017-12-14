package com.example.cpu10661.fastscrolldemo.RVFastScrollLib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.MainActivity;
import com.example.cpu10661.fastscrolldemo.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;

public class RVFastScrollLibActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_fast_scroll_lib);

        FastScrollRecyclerView recyclerView = findViewById(R.id.fast_scroll_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adapter
        ArrayList<Contact> contacts = getIntent() != null
                ? getIntent().<Contact>getParcelableArrayListExtra(MainActivity.ARG_CONTACTS_LIST)
                : new ArrayList<Contact>();
//        ArrayList<Contact> contacts = Utils.generateContactsList(1000);
        RVFastScrollLibAdapter adapter = new RVFastScrollLibAdapter(contacts);
        recyclerView.setAdapter(adapter);
    }
}
