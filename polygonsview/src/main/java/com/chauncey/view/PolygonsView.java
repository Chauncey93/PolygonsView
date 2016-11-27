package com.chauncey.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class PolygonsView extends View {
    private int defalutSize = 200;
    private int width, height;
    private int mCenter;
    private Paint mDiagonalsLinePaint;
    private int mVertex = 5;
    private List<Paint> mPolygonPaintList;
    private boolean mCenterLineEnable;
    private int mCount = 3;
    private boolean mProgressLineEnable;
    private Paint mProgressLinePaint;
    private List<Float> mProgressLinePercent;
    private List<String> mTextList;
    private List<Paint> mTextPaintList;


    public PolygonsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPolygonPaintList = new ArrayList<>(mVertex);
        mCenterLineEnable = true;
        mProgressLineEnable = true;
        defalutSize = dp2px(defalutSize);
        mDiagonalsLinePaint = new Paint();
        mDiagonalsLinePaint.setAntiAlias(true);
        mDiagonalsLinePaint.setColor(Color.BLACK);

        mProgressLinePaint = new Paint();
        mProgressLinePaint.setAntiAlias(true);
        mProgressLinePaint.setColor(Color.RED);
        mProgressLinePaint.setStyle(Paint.Style.STROKE);
        mProgressLinePaint.setStrokeWidth(5);

        mProgressLinePercent = new ArrayList<>();
        mTextList = new ArrayList<>();
        mTextPaintList = new ArrayList<>();

        for (int i = 0; i < mCount; i++) {
            Random random = new Random(0xFFFFFF);
            Paint paint = new Paint();
            int j = random.nextInt() * 50;
            paint.setColor(j);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);//设置实心
            paint.setStrokeWidth(5f);
            mPolygonPaintList.add(paint);
        }

        for (int i = 0; i < mVertex; i++) {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(30);
            mProgressLinePercent.add(0.5f);
            mTextList.add(i + "");
            mTextPaintList.add(paint);
        }

    }


    public void setDiagonalsLineColor(int color) {
        if (mDiagonalsLinePaint.getColor() == color) {
            return;
        }

        mDiagonalsLinePaint.setColor(color);
        invalidate();
    }

    public int getDiagonalsLineColor() {
        return mDiagonalsLinePaint.getColor();
    }


    public void setProgressLineWidth(float width) {
        if (mProgressLinePaint.getStrokeWidth() == width) {
            return;
        }
        mProgressLinePaint.setStrokeWidth(width);
        invalidate();

    }

    public float getProgressLineWidth() {
        return mProgressLinePaint.getStrokeWidth();

    }


    public void setProgressLineColor(int color) {

        if (mProgressLinePaint.getColor() == color) {
            return;
        }
        mProgressLinePaint.setColor(color);
        invalidate();
    }

    public int getProgressLineColor() {
        return mProgressLinePaint.getColor();
    }


    public void setProgressLineEnable(boolean b) {
        this.mProgressLineEnable = b;
        invalidate();
    }

    public boolean getProgressLineEnable() {
        return mProgressLineEnable;
    }


    public void setVertexs(int vertexs) {
        if (vertexs < 3 && vertexs == mVertex) {
            return;
        }


        if (vertexs > mVertex) {
            for (int i = mVertex; i < vertexs; i++) {
                mProgressLinePercent.add(i, 0.5f);
                mTextList.add(i, i + "");
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setTextSize(30);
                mTextPaintList.add(i, paint);
            }
        } else if (vertexs < mVertex) {
            for (int i = vertexs; i < mVertex; i++) {
                mProgressLinePercent.remove(i);
                mTextList.remove(i);
                mTextPaintList.remove(i);
            }
        }
        mVertex = vertexs;
        invalidate();
    }

    public int getVertexs() {
        return mVertex;
    }


    public void setPolygonCount(int count) {
        if (count <= 0 && count == mCount) return;

        if (count > mCount) {

            for (int i = mCount; i < count; i++) {
                Random random = new Random(0xFFFFFF);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                int j = random.nextInt() + random.nextInt() + random.nextInt();
                paint.setColor(j);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(5f);
                mPolygonPaintList.add(i, paint);

            }
        } else if (count < mCount) {
            for (int i = count; i < mCount; i++) {
                mPolygonPaintList.remove(i);

            }
        }
        mCount = count;
        invalidate();
    }

    public int getPolygonCount() {
        return mCount;
    }


    public void setPolygonStyle(int index, Paint.Style style) {
        if (mPolygonPaintList.get(index).getStyle() == style) {
            return;
        }
        Paint paint = mPolygonPaintList.get(index);
        paint.setStyle(style);
        mPolygonPaintList.set(index, paint);
        invalidate();
    }

    public Paint.Style getPolygonStyle(int index) {
        return mPolygonPaintList.get(index).getStyle();
    }


    public void setPolygonColor(int index, int color) {
        if (mPolygonPaintList.get(index).getColor() == color) {
            return;
        }
        Paint paint = mPolygonPaintList.get(index);
        paint.setColor(color);
        mPolygonPaintList.set(index, paint);
        invalidate();
    }

    public int getPolygonColor(int index) {
        return mPolygonPaintList.get(index).getColor();

    }


    public void setEdgeWidth(int index, float width) {
        if (mPolygonPaintList.get(index).getStrokeWidth() == width) {
            return;
        }
        Paint paint = mPolygonPaintList.get(index);
        paint.setStrokeWidth(width);
        mPolygonPaintList.set(index, paint);
        invalidate();
    }

    public float getEdgeWidth(int index) {
        return mPolygonPaintList.get(index).getStrokeWidth();

    }


    public void setProgress(int index, int value) {
        if (mProgressLinePercent.get(index) == value) {
            return;
        }
        mProgressLinePercent.set(index, value / 100f);
        invalidate();
    }

    public int getProgress(int index) {
        return (int) (mProgressLinePercent.get(index) * 100);
    }


    public void setVertexText(int index, String text) {
        if (mTextList.get(index).equals(text)) {
            return;
        }
        mTextList.set(index, text);
        invalidate();
    }

    public String getVertexText(int index) {
        return mTextList.get(index);
    }

    public void setVertexTextColor(int index, int color) {
        if (mTextPaintList.get(index).getColor() == color) {
            return;
        }
        mTextPaintList.get(index).setColor(color);
        invalidate();
    }

    public int getVertexTextColor(int index) {
        return mTextPaintList.get(index).getColor();
    }


    public void setVertexTextSize(int index, float size) {
        if (mTextPaintList.get(index).getTextSize() == size) {
            return;
        }
        mTextPaintList.get(index).setTextSize(size);
        invalidate();

    }

    public float getVertexTextSize(int index) {
        return mTextPaintList.get(index).getTextSize();
    }


    public void setCenterLineEnable(boolean b) {
        if (mCenterLineEnable == b) {
            return;
        }
        mCenterLineEnable = b;
        invalidate();
    }

    public boolean getCenterLineEnable() {
        return mCenterLineEnable;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        width = widthMode == MeasureSpec.AT_MOST ? defalutSize : width;
        height = heightMode == MeasureSpec.AT_MOST ? defalutSize : height;


        width = height = Math.min(width, height);
        mCenter = width / 2;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        onDrawEdges(canvas, mCenter, mCount);
        onDrawProgressLine(canvas);
        onDrawCenterLine(canvas);

        onDrawText(canvas);
        super.onDraw(canvas);
    }


    private void onDrawCenterLine(Canvas canvas) {
        if (!mCenterLineEnable) {
            return;
        }
        canvas.save();
        float degree = getDegree();


        for (int i = 0; i < mVertex; i++) {
            canvas.rotate(degree, mCenter, mCenter);
            canvas.drawLine(mCenter, mCenter, mCenter, (float) (0.25 * mCenter), mDiagonalsLinePaint);
        }
        canvas.restore();
    }


    private void onDrawEdges(Canvas canvas, float edgeLength, int count) {
        float degree = getDegree();

        Path path = new Path();

        path.moveTo(mCenter, mCenter - (float) 0.75 * edgeLength);
        for (int i = 1; i < mVertex; i++) {
            path.lineTo((float) (mCenter + 0.75 * edgeLength * Math.sin(Math.toRadians(degree * (i)))), (float) (mCenter - 0.75 * edgeLength * Math.cos(Math.toRadians(degree * (i)))));
        }
        path.close();
        canvas.drawPath(path, mPolygonPaintList.get(mCount - count));
        count--;

        if (count != 0) {
            onDrawEdges(canvas, mCenter * count / mCount, count);

        }
    }


    private void onDrawProgressLine(Canvas canvas) {
        if (!mProgressLineEnable) {
            return;
        }
        canvas.save();
        float degree = getDegree();
        Path path = new Path();

        path.moveTo(mCenter, mCenter - (float) (0.75 * mProgressLinePercent.get(0)) * mCenter);
//        for (int i = 0; i < mVertex; i++) {
//            path.lineTo((float) (mCenter + (0.75 * 0.5) * mCenter * Math.sin(Math.toRadians(degree * (i + 1)))), (float) (mCenter - (0.75 * 0.5) * mCenter * Math.cos(Math.toRadians(degree * (i + 1)))));
//        }
        for (int i = 1; i < mVertex; i++) {
            path.lineTo((float) (mCenter + (0.75 * mProgressLinePercent.get(i)) * mCenter * Math.sin(Math.toRadians(degree * (i)))), (float) (mCenter - (0.75 * mProgressLinePercent.get(i)) * mCenter * Math.cos(Math.toRadians(degree * (i)))));
        }
        path.close();
        canvas.drawPath(path, mProgressLinePaint);
        canvas.restore();
    }

    private void onDrawText(Canvas canvas) {
        float degree = getDegree();
        for (int i = 0; i < mVertex; i++) {
            canvas.drawText(mTextList.get(i), (float) (mCenter - mTextPaintList.get(i).measureText(mTextList.get(i)) / 2 + mCenter * 0.88 * Math.sin(Math.toRadians(degree * (i)))), (float) (mCenter * 1.05 - mCenter * 0.85 * Math.cos(Math.toRadians(degree * (i)))), mTextPaintList.get(i));
        }

    }


    private int dp2px(int values) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }


    private float getDegree() {


        if (mVertex == 7) {
            return (float) (360 / mVertex + 0.5);
        } else {
            return (float) (360 / mVertex);
        }
    }

}

