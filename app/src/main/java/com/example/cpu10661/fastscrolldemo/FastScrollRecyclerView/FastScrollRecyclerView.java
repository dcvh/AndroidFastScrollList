package com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.cpu10661.fastscrolldemo.R;
import com.example.cpu10661.fastscrolldemo.Utils;

import static com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView.FastScrollPopup.DEFAULT_BACKGROUND_SIZE;
import static com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView.FastScrollPopup.DEFAULT_TEXT_COLOR;
import static com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView.FastScrollPopup.DEFAULT_TEXT_SIZE;

/**
 * Created by cpu10661 on 12/7/17.
 */

public class FastScrollRecyclerView extends RecyclerView {

    private static final String TAG = FastScrollRecyclerView.class.getSimpleName();

    private FastScroller mFastScroller;
    private boolean mIsScrollerEnabled = true;

    public FastScrollRecyclerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public FastScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public FastScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyle) {
        mFastScroller = new FastScroller(getContext());
        mFastScroller.setRecyclerView(this);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.FastScrollRecyclerView, defStyle, 0);

            int accentColor = ContextCompat.getColor(context, R.color.colorAccent);
            int scrollerColor = a.getColor(R.styleable.FastScrollRecyclerView_scrollerColor, accentColor);
            setScrollerColor(scrollerColor);

            int textColor = a.getColor(R.styleable.FastScrollRecyclerView_textColor, DEFAULT_TEXT_COLOR);
            setTextColor(textColor);

            int textSize = a.getDimensionPixelSize(R.styleable.FastScrollRecyclerView_textSize,
                    Utils.spToPx(getContext(), DEFAULT_TEXT_SIZE));
            mFastScroller.setTextSize(textSize);

            int popupSize = a.getDimensionPixelSize(R.styleable.FastScrollRecyclerView_popupSize,
                    Utils.dpToPx(getContext(), DEFAULT_BACKGROUND_SIZE));
            mFastScroller.setPopupSize(popupSize);

            a.recycle();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean wasHandled = false;
        if (mFastScroller.isInsideHandleArea((int)e.getX(), (int)e.getY())) {
            wasHandled = mFastScroller.handleTouchEvent(e);
        }
        return wasHandled || super.onTouchEvent(e);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mFastScroller.layoutTrack(
                getPaddingLeft(),
                getPaddingTop(),
                (getRight() - getPaddingRight()) - getLeft(),
                (getBottom() - getPaddingBottom()) - getTop()
        );
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mIsScrollerEnabled) {
            mFastScroller.draw(canvas);
        }
    }

    public void setScrollerDrawingEnabled(boolean enabled) {
        mIsScrollerEnabled = enabled;
    }

    public void setScrollerColor(int color) {
        mFastScroller.setScrollerColor(color);
    }

    public void setTextSize(int sp) {
        mFastScroller.setTextSize(Utils.spToPx(getContext(), sp));
    }

    public void setTextColor(int color) {
        mFastScroller.setTextColor(color);
    }

    public void setTypeface(Typeface typeface) {
        mFastScroller.setTypeface(typeface);
    }

    public void setPopupSize(int dp) {
        mFastScroller.setPopupSize(Utils.dpToPx(getContext(), dp));
    }
}
