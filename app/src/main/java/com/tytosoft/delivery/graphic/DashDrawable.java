package com.tytosoft.delivery.graphic;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * Created by Kos on 17.10.2016.
 */

public class DashDrawable extends Drawable {


	Paint pen=new Paint();
	float cy=1;

	public DashDrawable(int color,float dp) {
		cy=1*dp;
		pen.setStrokeWidth(1*dp);
		pen.setColor(color);
		pen.setStyle(Paint.Style.STROKE);
		pen.setPathEffect(new DashPathEffect(new float[] {10*dp,20*dp}, 0));
	}

	@Override
	public void draw(@NonNull Canvas canvas) {
		canvas.drawLine(0,cy,100,cy, pen);
	}

	@Override
	public void setAlpha(int alpha) {

	}

	@Override
	public void setColorFilter(ColorFilter colorFilter) {

	}

	@Override
	public int getIntrinsicHeight() {
		return (int) (3*cy);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.UNKNOWN;
	}
}
