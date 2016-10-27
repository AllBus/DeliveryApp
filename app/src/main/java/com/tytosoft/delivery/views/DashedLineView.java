package com.tytosoft.delivery.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.tytosoft.delivery.R;

public class DashedLineView extends View
{
private float dp;
private Paint paint;
private Path path;
private PathEffect effects;

public DashedLineView(Context context)
{
    super(context);
    init(context);
}

public DashedLineView(Context context, AttributeSet attrs)
{
    super(context, attrs);
    init(context);
}

public DashedLineView(Context context, AttributeSet attrs, int defStyle)
{
    super(context, attrs, defStyle);
    init(context);
}

private void init(Context context)
{
    dp = context.getResources().getDisplayMetrics().density;
    paint = new Paint();
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(dp * 1);
    //set your own color
    paint.setColor(context.getResources().getColor(R.color.dashDivider));
    path = new Path();
    //array is ON and OFF distances in px (4px line then 2px space)
    effects = new DashPathEffect(new float[] { 6*dp ,2*dp}, 0);

}

@Override
protected void onDraw(Canvas canvas)
{
    super.onDraw(canvas);
    paint.setPathEffect(effects);
    int measuredHeight = getMeasuredHeight();
    int measuredWidth = getMeasuredWidth();
    if (measuredHeight <= measuredWidth)
    {
        // horizontal
        path.moveTo(0, dp);
        path.lineTo(measuredWidth, dp);
        canvas.drawPath(path, paint);
    }
    else
    {
        // vertical
        path.moveTo(0, 0);
        path.lineTo(0, measuredHeight);
        canvas.drawPath(path, paint);
    }

}
}