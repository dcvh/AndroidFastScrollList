package com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.MainActivity;
import com.example.cpu10661.fastscrolldemo.R;

import java.util.ArrayList;

public class FastScrollRVActivity extends AppCompatActivity {

    private static final String TAG = FastScrollRVActivity.class.getSimpleName();
    public static final String ARG_IS_SCROLLER_DRAWING_ENABLED = "isScrollerDrawingEnabled";
    private static final int DEFAULT_MAX_RECYCLED_VIEWS_LOWER_BOUND = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_scroll_rv);

        // basic recycler view
        FastScrollRecyclerView recyclerView = findViewById(R.id.fast_scroll_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // enable scroller or not
        boolean isScrollerDrawingEnabled = getIntent() == null
                || getIntent().getBooleanExtra(ARG_IS_SCROLLER_DRAWING_ENABLED, true);
        recyclerView.setScrollerDrawingEnabled(isScrollerDrawingEnabled);

        // get the contacts list from forwarded intent
        ArrayList<Contact> contacts = getIntent() != null
                ? getIntent().<Contact>getParcelableArrayListExtra(MainActivity.ARG_CONTACTS_LIST)
                : new ArrayList<Contact>();
//        ArrayList<Contact> contacts = Utils.generateContactsList(1000);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(String.valueOf(contacts.size()));
        }

        // adapter
        FastScrollRVAdapter adapter = new FastScrollRVAdapter(contacts);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        // reduce dropped frames problem
        int maxRecycledViews = Math.max(DEFAULT_MAX_RECYCLED_VIEWS_LOWER_BOUND,
                contacts.size() / 20);                        // 5% of number of contacts
        Log.d(TAG, "maxRecycledViews: " + maxRecycledViews);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, maxRecycledViews);
    }
}
