package com.example.cpu10661.fastscrolldemo.FastScroller;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.cpu10661.fastscrolldemo.R;
import com.example.cpu10661.fastscrolldemo.Utils;

public class FastScrollerActivity extends AppCompatActivity {

    private static final String TAG = FastScrollerActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private FastScrollerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_scroller);

        // basic recyclerView
        mRecyclerView = findViewById(R.id.rv_fast_scroller);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));

        // fast scroller
        final FastScroller fastScroller = findViewById(R.id.fast_scroller);
        fastScroller.setRecyclerView(mRecyclerView);
        fastScroller.setViewsToUse(R.layout.fast_scroller,
                R.id.fast_scroller_bubble, R.id.fast_scroller_handle);

        new LoadContactsTask().execute();
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

    class LoadContactsTask extends AsyncTask<Void, Void, Void> {

        ProgressBar mProgressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar = findViewById(R.id.pb_indeterminate);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // adapter
            mAdapter = new FastScrollerAdapter(Utils.getContactsList(FastScrollerActivity.this));
//            mAdapter = new FastScrollerAdapter(Utils.generateContactsList(1000));
            Log.d(TAG, "doInBackground: " + mAdapter.getItemCount());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mRecyclerView.setAdapter(mAdapter);
            mProgressBar.setVisibility(View.GONE);
            findViewById(R.id.cl_recycler_view).setVisibility(View.VISIBLE);
        }
    }
}
