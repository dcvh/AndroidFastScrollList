package com.example.cpu10661.fastscrolldemo.RecyclerFastScroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.ContactAdapter;
import com.example.cpu10661.fastscrolldemo.MainActivity;
import com.example.cpu10661.fastscrolldemo.R;
import com.example.cpu10661.fastscrolldemo.RecyclerBubble.RecyclerBubbleAdapter;
import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.pluscubed.recyclerfastscroll.RecyclerFastScroller;

import java.util.ArrayList;

public class RecyclerFastScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_fast_scroll);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerFastScroller fastScroller = findViewById(R.id.fast_scroller);

        // adapter
        ArrayList<Contact> contacts = getIntent() != null
                ? getIntent().<Contact>getParcelableArrayListExtra(MainActivity.ARG_CONTACTS_LIST)
                : new ArrayList<Contact>();
//        ArrayList<Contact> contacts = Utils.generateContactsList(1000);
        ContactAdapter adapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(adapter);

        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.attachRecyclerView(recyclerView);
    }
}
