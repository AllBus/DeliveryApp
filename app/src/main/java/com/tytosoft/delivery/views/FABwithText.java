package com.tytosoft.delivery.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

/**
 * Created by Kos on 06.07.2016.
 */
public class FABwithText extends FloatingActionButton {
	private String text;

	public FABwithText(Context context) {
		super(context);
		init(context);
	}

	public FABwithText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public FABwithText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}


	Paint pen=new Paint();
	Paint penBack=new Paint();
	private void init(Context context) {
		float sp=context.getResources().getDisplayMetrics().scaledDensity;
		pen.setColor(Color.BLACK);
		pen.setTextSize(12*sp);
		pen.setTextAlign(Paint.Align.CENTER);
		penBack.setAntiAlias(true);
		penBack.setColor(Color.WHITE);
		penBack.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int w=canvas.getWidth();
		int h=canvas.getHeight();
		if (text!=null) {

			float tw=pen.measureText(text);
			float tr=pen.getTextSize();
			canvas.drawRoundRect((w-tw)/2-tr/4,h/2-tr,(w+tw)/2+tr/4,h/2+tr/4,tr,tr,penBack);
			canvas.drawText(text, w / 2, h / 2, pen);
		}

	}

	public void setText(String text){
		this.text=text;
		invalidate();
	}
}
