/*
 * Date 	: 2015.03.10 19:23:46
 * Author	: Newpub
 */
package com.pefis.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FaceDetectionView extends View {

	private Paint mPaint = new Paint();
	private float[][] mPts;
	private float[][] mRect;

	private static final int POINTS = 4;
	private static final int COUNT = 4;
	private float mWidth;
	private float mHeight;

	private int WIDTH = 480;
	private int HEIGHT = 640;

	private float Width_rate = 0;
	private float Height_rate = 0;

	public FaceDetectionView(Context context) {
		super(context);

	}

	public void setRect(int left, int top, int right, int bottom, int p) {

		if (Height_rate == 0)
			return;
		mRect[0][0] = left * Width_rate;
		mRect[0][1] = top * Height_rate;

		mRect[1][0] = right * Width_rate;
		mRect[1][1] = top * Height_rate;

		mRect[2][0] = right * Width_rate;
		mRect[2][1] = bottom * Height_rate;

		mRect[3][0] = left * Width_rate;
		mRect[3][1] = bottom * Height_rate;
		mWidth = (right - left) * Width_rate / 8;
		mHeight = (bottom - top) * Height_rate / 8;
		buildPoints();
	}

	public void setRect(int left, int top, int width, int height) {

		if (Height_rate == 0)
			return;
		mRect[0][0] = left * Width_rate;
		mRect[0][1] = top * Height_rate;

		mRect[1][0] = (left + width) * Width_rate;
		mRect[1][1] = top * Height_rate;

		mRect[2][0] = (left + width) * Width_rate;
		mRect[2][1] = (top + height) * Height_rate;

		mRect[3][0] = left * Width_rate;
		mRect[3][1] = (top + height) * Height_rate;
		mWidth = (width) * Width_rate / 8;
		mHeight = (height) * Height_rate / 8;
		buildPoints();
	}

	public FaceDetectionView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mPts = new float[COUNT][POINTS * 2];
		mRect = new float[COUNT][2];
	}

	private void buildPoints() {
		int signX, signY;
		for (int i = 0; i < COUNT; i++) {
			if (i < 2)
				signY = 1;
			else
				signY = -1;
			if (i == 1 || (i == 2))
				signX = -1;
			else
				signX = 1;
			mPts[i][0] = mRect[i][0];
			mPts[i][1] = mRect[i][1] + signY * mHeight;
			mPts[i][2] = mRect[i][0];
			mPts[i][3] = mRect[i][1];
			mPts[i][4] = mRect[i][0];
			mPts[i][5] = mRect[i][1];
			mPts[i][6] = mRect[i][0] + signX * mWidth;
			mPts[i][7] = mRect[i][1];
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = mPaint;

		Height_rate = (float) getHeight() / HEIGHT;
		Width_rate = (float) getWidth() / WIDTH;
		paint.setColor(Color.RED);
		paint.setStrokeWidth(5);
		for (int i = 0; i < 4; i++)
			canvas.drawLines(mPts[i], paint);

		paint.setColor(Color.RED);
		for (int i = 0; i < 4; i++)
			canvas.drawPoints(mPts[i], paint);
	}

}
