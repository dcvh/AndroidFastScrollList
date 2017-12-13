package com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.cpu10661.fastscrolldemo.R;
import com.example.cpu10661.fastscrolldemo.Utils;

public class FastScrollRVActivity extends AppCompatActivity {

    private static final String TAG = FastScrollRVActivity.class.getSimpleName();

    private FastScrollRVAdapter mAdapter;
    private FastScrollRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_scroll_rv);

        // basic recycler view
        mRecyclerView = findViewById(R.id.fast_scroll_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        new LoadContactsTask().execute();
    }

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
            mAdapter = new FastScrollRVAdapter(Utils.getContactsList(FastScrollRVActivity.this));
//            mAdapter = new FastScrollRVAdapter(Utils.generateContactsList(1000));
            Log.d(TAG, "doInBackground: " + mAdapter.getItemCount());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mRecyclerView.setAdapter(mAdapter);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
