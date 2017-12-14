package com.example.cpu10661.fastscrolldemo.AlphabetIndexRV;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.MainActivity;
import com.example.cpu10661.fastscrolldemo.R;

import java.util.ArrayList;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

public class AlphabetIndexRVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet_index_rv);

        IndexFastScrollRecyclerView recyclerView = findViewById(R.id.index_fast_scroll_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adapter
        ArrayList<Contact> contacts = getIntent() != null
                ? getIntent().<Contact>getParcelableArrayListExtra(MainActivity.ARG_CONTACTS_LIST)
                : new ArrayList<Contact>();
//        ArrayList<Contact> contacts = Utils.generateContactsList(1000);
        AlphabetIndexRVAdapter adapter = new AlphabetIndexRVAdapter(contacts);
        recyclerView.setAdapter(adapter);
    }
}
