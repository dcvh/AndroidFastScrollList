package com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.MainActivity;
import com.example.cpu10661.fastscrolldemo.R;

import java.util.ArrayList;

public class FastScrollRVActivity extends AppCompatActivity {

    private static final String TAG = FastScrollRVActivity.class.getSimpleName();
    public static final String ARG_IS_SCROLLER_DRAWING_ENABLED = "isScrollerDrawingEnabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_scroll_rv);

        // basic recycler view
        FastScrollRecyclerView recyclerView = findViewById(R.id.fast_scroll_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        // enable scroller or not
        boolean isScrollerDrawingEnabled =
                getIntent() == null || getIntent().getBooleanExtra(ARG_IS_SCROLLER_DRAWING_ENABLED, true);
        recyclerView.setScrollerDrawingEnabled(isScrollerDrawingEnabled);

        // adapter
        ArrayList<Contact> contacts = getIntent() != null
                ? getIntent().<Contact>getParcelableArrayListExtra(MainActivity.ARG_CONTACTS_LIST)
                : new ArrayList<Contact>();
//        ArrayList<Contact> contacts = Utils.generateContactsList(1000);
        FastScrollRVAdapter adapter = new FastScrollRVAdapter(contacts);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        // reduce dropped frames problem
        recyclerView.setItemViewCacheSize(contacts.size());
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }
}
