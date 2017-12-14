package com.example.cpu10661.fastscrolldemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cpu10661.fastscrolldemo.AlphabetIndexRV.AlphabetIndexRVActivity;
import com.example.cpu10661.fastscrolldemo.DefaultRVFastScroll.DefaultRVFastScroll;
import com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView.FastScrollRVActivity;
import com.example.cpu10661.fastscrolldemo.FastScroller.FastScrollerActivity;
import com.example.cpu10661.fastscrolldemo.RVFastScrollLib.RVFastScrollLibActivity;
import com.example.cpu10661.fastscrolldemo.RecyclerBubble.RecyclerBubbleActivity;
import com.example.cpu10661.fastscrolldemo.RecyclerFastScroll.RecyclerFastScrollActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_READ_CONTACTS_PERMISSION = 1;
    public static final String ARG_CONTACTS_LIST = "contactsList";

    private Button mFastScrollerButton;
    private Button mFastScrollRVButton;
    private Button mDefaultFastScrollButton;
    private Button mRVFastScrollLibButton;
    private Button mAlphabetIndexRVButton;
    private Button mRecyclerBubbleButton;
    private Button mRecyclerFastScrollButton;
    private CheckBox mIsScrollerEnabledCheckBox;
    private Intent mNextActivityIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // fast scroller
        mFastScrollerButton = findViewById(R.id.btn_fast_scroller);
        mFastScrollerButton.setOnClickListener(this);

        // fast-scroll recyclerView
        mFastScrollRVButton = findViewById(R.id.btn_fast_scroll_rv);
        mFastScrollRVButton.setOnClickListener(this);
        mIsScrollerEnabledCheckBox = findViewById(R.id.cb_scroller_enabled);

        // default RV's fast scroll (without bubble)
        mDefaultFastScrollButton = findViewById(R.id.btn_default_fast_scroll);
        mDefaultFastScrollButton.setOnClickListener(this);

        // RecyclerView-FastScroll library
        mRVFastScrollLibButton = findViewById(R.id.btn_rv_fast_scroll_lib);
        mRVFastScrollLibButton.setOnClickListener(this);

        // alphabetIndex RV
        mAlphabetIndexRVButton = findViewById(R.id.btn_alphabet_index);
        mAlphabetIndexRVButton.setOnClickListener(this);

        // Recycler Bubble
        mRecyclerBubbleButton = findViewById(R.id.btn_recycler_bubble);
        mRecyclerBubbleButton.setOnClickListener(this);

        // Recycler Fast Scroll
        mRecyclerFastScrollButton = findViewById(R.id.btn_recycler_fast_scroll);
        mRecyclerFastScrollButton.setOnClickListener(this);
    }

    private void startCorrespondingApproach() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    RC_READ_CONTACTS_PERMISSION);
        } else {
            setButtonsEnabled(false);
            mNextActivityIntent.putExtra(FastScrollRVActivity.ARG_IS_SCROLLER_DRAWING_ENABLED,
                    mIsScrollerEnabledCheckBox.isChecked());
            new LoadContactsTask().execute();
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        mFastScrollerButton.setEnabled(enabled);
        mFastScrollRVButton.setEnabled(enabled);
        mDefaultFastScrollButton.setEnabled(enabled);
        mRVFastScrollLibButton.setEnabled(enabled);
        mAlphabetIndexRVButton.setEnabled(enabled);
        mRecyclerBubbleButton.setEnabled(enabled);
        mRecyclerFastScrollButton.setEnabled(enabled);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_READ_CONTACTS_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCorrespondingApproach();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View view) {
        Class activityClass;
        switch (view.getId()) {
            case R.id.btn_fast_scroller:
                activityClass = FastScrollerActivity.class;
                break;
            case R.id.btn_fast_scroll_rv:
                activityClass = FastScrollRVActivity.class;
                break;
            case R.id.btn_default_fast_scroll:
                activityClass = DefaultRVFastScroll.class;
                break;
            case R.id.btn_rv_fast_scroll_lib:
                activityClass = RVFastScrollLibActivity.class;
                break;
            case R.id.btn_alphabet_index:
                activityClass = AlphabetIndexRVActivity.class;
                break;
            case R.id.btn_recycler_bubble:
                activityClass = RecyclerBubbleActivity.class;
                break;
            case R.id.btn_recycler_fast_scroll:
                activityClass = RecyclerFastScrollActivity.class;
                break;
            default:
                throw new UnsupportedOperationException("Unknown button");
        }
        mNextActivityIntent = new Intent(this, activityClass);
        startCorrespondingApproach();
    }

    class LoadContactsTask extends AsyncTask<Void, Void, Void> {

        ProgressBar mProgressBar;
        TextView mProgressTextView;
        ArrayList<Contact> mContacts = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar = findViewById(R.id.pb_indeterminate);
            mProgressTextView = findViewById(R.id.tv_progress_info);

            setProgressVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mContacts =  Utils.getContactsList(MainActivity.this);
            Log.d(TAG, "doInBackground: " + mContacts.size());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mNextActivityIntent.putParcelableArrayListExtra(ARG_CONTACTS_LIST, mContacts);
            startActivity(mNextActivityIntent);

            setButtonsEnabled(true);
            setProgressVisibility(View.INVISIBLE);
        }

        private void setProgressVisibility(int visibility) {
            mProgressBar.setVisibility(visibility);
            mProgressTextView.setVisibility(visibility);
        }
    }
}
