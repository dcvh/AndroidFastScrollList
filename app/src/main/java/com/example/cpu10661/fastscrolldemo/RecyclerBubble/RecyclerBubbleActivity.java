package com.example.cpu10661.fastscrolldemo.RecyclerBubble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cpu10661.fastscrolldemo.AlphabetIndexRV.AlphabetIndexRVAdapter;
import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.MainActivity;
import com.example.cpu10661.fastscrolldemo.R;
import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

public class RecyclerBubbleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_bubble);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FastScroller fastScroller = findViewById(R.id.fast_scroller);

        // adapter
        ArrayList<Contact> contacts = getIntent() != null
                ? getIntent().<Contact>getParcelableArrayListExtra(MainActivity.ARG_CONTACTS_LIST)
                : new ArrayList<Contact>();
//        ArrayList<Contact> contacts = Utils.generateContactsList(1000);
        RecyclerBubbleAdapter adapter = new RecyclerBubbleAdapter(contacts);
        recyclerView.setAdapter(adapter);

        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(recyclerView);
    }
}
