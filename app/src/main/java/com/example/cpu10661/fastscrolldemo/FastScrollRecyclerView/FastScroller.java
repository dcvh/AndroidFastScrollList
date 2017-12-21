package com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.example.cpu10661.fastscrolldemo.R;
import com.example.cpu10661.fastscrolldemo.Utils;

/**
 * Created by cpu10661 on 12/5/17.
 */

public class FastScroller {

    private static final String TAG = FastScroller.class.getSimpleName();

    private static final int TRACK_SNAP_RANGE = 5;
    // below variables are all declared in pixel unit
    private static final int DEFAULT_TRACK_WIDTH = 4;
    private static final int DEFAULT_HANDLE_HEIGHT = 30;
    private static final int DEFAULT_HANDLE_TOUCH_AREA = 50;

    private FastScrollRecyclerView mRecyclerView;
    private Context mContext;
    private Rect mTrack, mTouchArea;
    private RectF mHandle;
    private int mRVPaddingTop;
    private Paint mTrackPaint, mHandlePaint, mNormalHandlePaint, mSelectedHandlePaint;
    private FastScrollPopup mPopup;
    private boolean mIsSelecting = false;           // true if we are dragging the handle

    FastScroller(Context context) {
        this.mContext = context;

        mTrackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTrackPaint.setColor(Color.GRAY);

        mNormalHandlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNormalHandlePaint.setColor(Color.BLACK);

        mSelectedHandlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectedHandlePaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));

        mHandlePaint = mNormalHandlePaint;

//        mHandlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mHandlePaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
    }

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
            updateBubbleAndHandlePosition();
        }
    };

    public interface PopupTextGetter {
        String getTextPopup(int position);
    }

    void setRecyclerView(final FastScrollRecyclerView recyclerView) {
        if (this.mRecyclerView != recyclerView) {
            if (this.mRecyclerView != null)
                this.mRecyclerView.removeOnScrollListener(onScrollListener);
            this.mRecyclerView = recyclerView;
            if (this.mRecyclerView == null)
                return;
            recyclerView.addOnScrollListener(onScrollListener);

            mPopup = new FastScrollPopup(mContext.getResources(), mRecyclerView);
        }
    }

    private void setRecyclerViewPosition(float y) {
        if (mRecyclerView != null) {
            final int itemCount = mRecyclerView.getAdapter().getItemCount();
            float proportion;
            final int trackHeight = mTrack.height();
            if (mHandle.top == 0)
                proportion = 0f;
            else if (mHandle.top + mHandle.height() >= trackHeight - TRACK_SNAP_RANGE)
                proportion = 1f;
            else
                proportion = y / (float) trackHeight;
            final int targetPos = getValueInRange(0, itemCount - 1, (int) (proportion * (float) itemCount));
            ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(targetPos, 0);
            final String bubbleText = ((PopupTextGetter) mRecyclerView.getAdapter()).getTextPopup(targetPos);
            if (mPopup != null) {
                mPopup.setSectionName(bubbleText);
            }
        }
    }

    void layoutTrack(int left, int top, int right, int bottom) {

        final int handleWidth = Utils.dpToPx(mContext, DEFAULT_TRACK_WIDTH);
        final int handleHeight = Utils.dpToPx(mContext, DEFAULT_HANDLE_HEIGHT);

        mRVPaddingTop = top;

        mTrack = new Rect(right - handleWidth, top, right, bottom);

        mHandle = new RectF(right - handleWidth, top, right, top + handleHeight);

        final int touchArea = Utils.dpToPx(mContext, DEFAULT_HANDLE_TOUCH_AREA);
        mTouchArea = new Rect(right - touchArea, top, right, bottom);
    }

    boolean handleTouchEvent(@NonNull MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // outside touch area
                if (event.getX() < mTouchArea.left) {
                    return false;
                }
                mHandlePaint = mSelectedHandlePaint;
                mPopup.animateVisibility(true);
                mIsSelecting = true;
            case MotionEvent.ACTION_MOVE:
                final float y = event.getY();
                setBubbleAndHandlePosition(y);
                setRecyclerViewPosition(y);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                mIsSelecting = false;
                mHandlePaint = mNormalHandlePaint;
                mPopup.animateVisibility(false);
                return true;
        }
        return false;
    }

    private int getValueInRange(int min, int max, int value) {
        int minimum = Math.max(min, value);
        return Math.min(minimum, max);
    }

    void draw(Canvas canvas) {

        // track
//        canvas.drawRect(mTrack, mTrackPaint);

        // handle
        canvas.drawRoundRect(mHandle, 100, 100, mHandlePaint);
        // popup
        mPopup.draw(canvas);
    }

    private void updateBubbleAndHandlePosition() {
//        if (!mPopup.isVisible())
//            return;
        if (mTrack == null || mIsSelecting) {
            return;
        }

        final int verticalScrollOffset = mRecyclerView.computeVerticalScrollOffset();
        final int verticalScrollRange = mRecyclerView.computeVerticalScrollRange();
        float proportion =
                (float) verticalScrollOffset / ((float) verticalScrollRange - mTrack.height());
        setBubbleAndHandlePosition(mTrack.height() * proportion);
    }

    private void setBubbleAndHandlePosition(float y) {
        int handleHeight = (int) mHandle.height();
        // this part assumes that mRVPaddingTop is smaller than mTrack.bottom - handleHeight,
        // cause it does not make any sense for padding to be larger than the RecyclerView itself
        mHandle.top = getValueInRange(mRVPaddingTop, mTrack.bottom - handleHeight, (int) (y - handleHeight / 2));
        mHandle.bottom = mHandle.top + handleHeight;
        // and the same assumption for this
        int popupTop = getValueInRange(mRVPaddingTop, mTrack.bottom - handleHeight / 2, (int) (y));
        mPopup.updatePopupBounds((int) mHandle.left, popupTop);

        mRecyclerView.invalidate();
    }

    boolean isInsideHandleArea(int x, int y) {
        return mTouchArea.contains(x, y) || /* to detect action_up */ mIsSelecting;
    }

    void setScrollerColor(int color) {
        mSelectedHandlePaint.setColor(color);
        mPopup.setBackgroundColor(color);
    }

    void setTextSize(@Px int size) {
        mPopup.setTextSize(size);
    }

    void setTextColor(int color) {
        mPopup.setTextColor(color);
    }

    void setTypeface(Typeface typeface) {
        mPopup.setTypeface(typeface);
    }

    void setPopupSize(@Px int size) {
        mPopup.setBackgroundSize(size);
    }
}
