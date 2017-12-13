/*
 * Copyright (c) 2016 Tim Malseed
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Keep;
import android.support.annotation.Px;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;

import com.example.cpu10661.fastscrolldemo.R;
import com.example.cpu10661.fastscrolldemo.Utils;

public class FastScrollPopup {

    private static final String TAG = FastScrollPopup.class.getSimpleName();

    static final int DEFAULT_TEXT_SIZE = 22;
    static final int DEFAULT_BACKGROUND_SIZE = DEFAULT_TEXT_SIZE * 2;
    static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_POPUP_TRACK_SPACE = 10;

    private FastScrollRecyclerView mRecyclerView;

    private Resources mRes;

    private int mBackgroundSize;
    private int mCornerRadius;

    private Path mBackgroundPath = new Path();
    private RectF mBackgroundRect = new RectF();
    private Paint mBackgroundPaint;
    private int mBackgroundColor = 0xff000000;

    private String mSectionName;

    private Paint mTextPaint;
    private Rect mTextBounds = new Rect();

    private Rect mBgBounds = new Rect();

    private float mAlpha = 1;

    private ObjectAnimator mAlphaAnimator;
    private boolean mVisible;

    public FastScrollPopup(Resources resources, FastScrollRecyclerView recyclerView) {

        mRes = resources;

        mRecyclerView = recyclerView;

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setAlpha(0);

        mTextPaint.setColor(DEFAULT_TEXT_COLOR);
        setBackgroundColor(ResourcesCompat.getColor(mRes, R.color.colorAccent, null));

        setTextSize(Utils.spToPx(mRes, DEFAULT_TEXT_SIZE));
        setBackgroundSize(Utils.dpToPx(mRes, DEFAULT_BACKGROUND_SIZE));
    }

    void setBackgroundColor(int color) {
        mBackgroundColor = color;
        mBackgroundPaint.setColor(color);
    }

    void setTextColor(int color) {
        mTextPaint.setColor(color);
    }

    void setTextSize(@Px int size) {
        mTextPaint.setTextSize(size);
    }

    void setBackgroundSize(@Px int size) {
        mBackgroundSize = size;
        mCornerRadius = mBackgroundSize / 2;
    }

    void setTypeface(Typeface typeface) {
        mTextPaint.setTypeface(typeface);
    }

    /**
     * Animates the visibility of the fast scroller popup.
     */
    void animateVisibility(boolean visible) {
        if (mVisible != visible) {
            mVisible = visible;
            if (mAlphaAnimator != null) {
                mAlphaAnimator.cancel();
            }
            mAlphaAnimator = ObjectAnimator.ofFloat(this, "alpha", visible ? 1f : 0f);
            mAlphaAnimator.setDuration(visible ? 200 : 150);
            mAlphaAnimator.start();
        }
    }

    @Keep
    public void setAlpha(float alpha) {
        mAlpha = alpha;
        mRecyclerView.invalidate();
    }

    @Keep
    public float getAlpha() {
        return mAlpha;
    }

    private float[] createRadii() {
        if (Utils.isRtl(mRes)) {
            return new float[]{mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, 0, 0};
        } else {
            return new float[]{mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, 0, 0, mCornerRadius, mCornerRadius};
        }
    }

    void updatePopupBounds(int left, int top) {
        mBgBounds.top = top - mBackgroundSize;
        if (mBgBounds.top < 0) {
            mBgBounds.top = 0;
        }

        mBgBounds.left = left - mBackgroundSize - Utils.dpToPx(mRes, DEFAULT_POPUP_TRACK_SPACE);

        mBgBounds.bottom = mBgBounds.top + mBackgroundSize;
        mBgBounds.right = mBgBounds.left + mBackgroundSize;
    }

    void draw(Canvas canvas) {
        if (isVisible()) {

            int restoreCount = canvas.save(Canvas.MATRIX_SAVE_FLAG);
            canvas.translate(mBgBounds.left, mBgBounds.top);

            mBackgroundPath.reset();
            mBackgroundRect.set(mBgBounds);
            mBackgroundRect.offsetTo(0, 0);

            float[] radii = createRadii();

            mBackgroundPath.addRoundRect(mBackgroundRect, radii, Path.Direction.CW);

            mBackgroundPaint.setAlpha((int) (Color.alpha(mBackgroundColor) * mAlpha));
            mTextPaint.setAlpha((int) (mAlpha * 255));
            canvas.drawPath(mBackgroundPath, mBackgroundPaint);
            canvas.drawText(mSectionName, (mBgBounds.width() - mTextBounds.width()) / 2,
                    mBgBounds.height() - (mBgBounds.height() - mTextBounds.height()) / 2,
                    mTextPaint);
            canvas.restoreToCount(restoreCount);
        }
    }

    void setSectionName(String sectionName) {
        if (!sectionName.equals(mSectionName)) {
            mSectionName = sectionName;
            mTextPaint.getTextBounds(sectionName, 0, sectionName.length(), mTextBounds);
            // Update the width to use measureText since that is more accurate
            mTextBounds.right = (int) (mTextBounds.left + mTextPaint.measureText(sectionName));
        }
    }

    boolean isVisible() {
        return (mAlpha > 0f) && (!TextUtils.isEmpty(mSectionName));
    }
}