package com.example.cpu10661.fastscrolldemo.FastScroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.MainActivity;
import com.example.cpu10661.fastscrolldemo.R;

import java.util.ArrayList;

public class FastScrollerActivity extends AppCompatActivity {

    private static final String TAG = FastScrollerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_scroller);

        // basic recyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_fast_scroller);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));

        // adapter
        ArrayList<Contact> contacts = getIntent() != null
                ? getIntent().<Contact>getParcelableArrayListExtra(MainActivity.ARG_CONTACTS_LIST)
                : new ArrayList<Contact>();
//        ArrayList<Contact> contacts = Utils.generateContactsList(1000);
        FastScrollerAdapter adapter = new FastScrollerAdapter(contacts);
        recyclerView.setAdapter(adapter);

        // fast scroller
        final FastScroller fastScroller = findViewById(R.id.fast_scroller);
        fastScroller.setRecyclerView(recyclerView);
        fastScroller.setViewsToUse(R.layout.fast_scroller,
                R.id.fast_scroller_bubble, R.id.fast_scroller_handle);

        // reduce dropped frames problem
        recyclerView.setItemViewCacheSize(contacts.size());
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    }

//    private void hideScrollBarInSingleView() {
//        contactRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
//            @Override
//            public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
//                super.onLayoutChildren(recycler, state);
//                //TODO if the items are filtered, considered hiding the fast scroller here
//                final int firstVisibleItemPosition = findFirstVisibleItemPosition();
//                if (firstVisibleItemPosition != 0) {
//                    // this avoids trying to handle un-needed calls
//                    if (firstVisibleItemPosition == -1)
//                        //not initialized, or no items shown, so hide fast-scroller
//                        fastScroller.setVisibility(View.GONE);
//                    return;
//                }
//                final int lastVisibleItemPosition = findLastVisibleItemPosition();
//                int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
//                //if all items are shown, hide the fast-scroller
//                fastScroller.setVisibility(adapter.getItemCount() > itemsShown ? View.VISIBLE : View.GONE);
//            }
//        });
//    }
}
