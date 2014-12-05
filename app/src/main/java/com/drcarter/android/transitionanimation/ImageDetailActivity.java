package com.drcarter.android.transitionanimation;

import android.animation.TimeInterpolator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.drcarter.android.transitionanimation.data.ImageData;

public class ImageDetailActivity extends ActionBarActivity {

    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();

    private static final int ANIM_DURATION = 1000;

    private ImageView mImageDetail = null;
    private TextView mTextMessage = null;

    private ImageData mImageData = null;

    int mLeftDelta;
    int mTopDelta;
    float mWidthScale;
    float mHeightScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        Bundle extra = getIntent().getExtras();
        this.mImageData = extra.getParcelable("data");
        final int left = extra.getInt("left");
        final int top = extra.getInt("top");
        final int width = extra.getInt("width");
        final int height = extra.getInt("height");

        this.mImageDetail = (ImageView) findViewById(R.id.image_detail);
        this.mImageDetail.setImageResource(this.mImageData.getImageID());
        this.mTextMessage = (TextView) findViewById(R.id.text_message);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), this.mImageData.getImageID());
        if (bitmap != null) {
            if (bitmap.getWidth() < metrics.widthPixels) {
                float scale = (float) metrics.widthPixels / (float) bitmap.getWidth();
                Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * scale), (int) (bitmap.getHeight() * scale), true);
                if (resizeBitmap != null) {
                    this.mImageDetail.setImageBitmap(resizeBitmap);
                } else {
                    this.mImageDetail.setImageBitmap(bitmap);
                }
            } else {
                this.mImageDetail.setImageBitmap(bitmap);
            }
        }

        this.mTextMessage.setText(this.mImageData.getMessage());
        this.mTextMessage.setVisibility(View.GONE);

        this.mImageDetail.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                mImageDetail.getViewTreeObserver().removeOnPreDrawListener(this);
                int[] screenLocation = new int[2];
                mImageDetail.getLocationOnScreen(screenLocation);
                mLeftDelta = left - screenLocation[0];
                mTopDelta = top - screenLocation[1];

                mWidthScale = (float) width / mImageDetail.getWidth();
                mHeightScale = (float) height / mImageDetail.getHeight();

                runEnterAnimation();

                return true;
            }
        });
    }

    private void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);

        mImageDetail.setPivotX(0);
        mImageDetail.setPivotY(0);
        mImageDetail.setScaleX(mWidthScale);
        mImageDetail.setScaleY(mHeightScale);
        mImageDetail.setTranslationX(mLeftDelta);
        mImageDetail.setTranslationY(mTopDelta);

        mImageDetail.animate().setDuration(duration).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator).
                withEndAction(new Runnable() {
                    public void run() {
                        mTextMessage.setVisibility(View.VISIBLE);
                    }
                });
    }
}
