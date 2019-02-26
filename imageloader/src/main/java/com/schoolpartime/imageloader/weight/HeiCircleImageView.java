package com.schoolpartime.imageloader.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class HeiCircleImageView extends ImageView {

    private Paint mPaint;
    private int mRadius;
    private float mScale;

    public HeiCircleImageView(Context context) {
        super(context);
    }

    public HeiCircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeiCircleImageView(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredHeight(),getMeasuredWidth());
        mRadius = size/2;
        setMeasuredDimension(size,size);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint = new Paint();

        Drawable drawable = getDrawable();

        if(drawable != null) {

            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

            BitmapShader bitmapShader = new BitmapShader(bitmap,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP);

            mScale = mRadius*2.0f/Math.min(bitmap.getHeight(),bitmap.getWidth());

            Matrix matrix = new Matrix();
            matrix.setScale(mScale,mScale);
            bitmapShader.setLocalMatrix(matrix);
            mPaint.setShader(bitmapShader);
            canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);

        }else {
            super.onDraw(canvas);
        }

    }
}
