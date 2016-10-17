package com.tytosoft.delivery.graphic.shapes;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;

public class DashShape extends RectShape {

	float radius;

	Paint pen;

	public DashShape(float radius, Paint pen) {
		this.radius = radius;
		this.pen = pen;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {

		final RectF r=rect();
		canvas.drawLine(r.left,r.centerY(),r.right,r.centerY(),paint);

	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void getOutline(Outline outline) {
		final RectF rect = rect();
		outline.setRoundRect((int) Math.ceil(rect.left), (int) Math.ceil(rect.top),
				(int) Math.floor(rect.right), (int) Math.floor(rect.bottom),
				radius);

	}
}
