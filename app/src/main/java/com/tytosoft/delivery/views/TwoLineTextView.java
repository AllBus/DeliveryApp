package com.tytosoft.delivery.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.tytosoft.delivery.R;
import com.tytosoft.delivery.common.helpers.ResHelper;


/**
 * Вьюшка с картинкой и двумя строками текста
 * Created by Kos on 10.07.2016.
 */
public class TwoLineTextView extends View {


	private Drawable drawable;
	private String firstText = "text";
	private String secondText = "second";
	private Paint firstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	;
	private Paint secondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int tintColor = 0xFFFFFFFF;

//	private float firstTextWidth=1;
//	private float secondTextWidth=1;

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
		if (drawable != null) {
			drawable.setColorFilter(filter);
		}
	}

	public String getFirstText() {
		return firstText;
	}

	public void setFirstText(String firstText) {
		this.firstText = firstText;
		//	firstTextWidth=firstPaint.measureText(firstText);
		invalidate();
	}

	public String getSecondText() {
		return secondText;
	}

	public void setSecondText(String secondText) {
		this.secondText = secondText;
		//	secondTextWidth=secondPaint.measureText(secondText);
		invalidate();
	}

	public float getTextSizeFirstLine() {
		return firstPaint.getTextSize();
	}

	public void setTextSizeFirstLine(float textSizeFirstLine) {
		firstPaint.setTextSize(textSizeFirstLine);
		//	firstTextWidth=firstPaint.measureText(firstText);
		invalidate();
	}

	public float getTextSizeSecondLine() {
		return secondPaint.getTextSize();
	}

	public void setTextSizeSecondLine(float textSizeSecondLine) {
		this.secondPaint.setTextSize(textSizeSecondLine);
		//	secondTextWidth=secondPaint.measureText(secondText);
		invalidate();
	}


	public TwoLineTextView(Context context) {
		this(context, null);
	}

	public TwoLineTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TwoLineTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs,defStyle);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		float sp = ResHelper.sp(context);
		float dp = ResHelper.dp(getContext());

		firstPaint.setColor(Color.BLACK);
		secondPaint.setColor(Color.BLACK);

		firstPaint.setTextAlign(Paint.Align.CENTER);
		secondPaint.setTextAlign(Paint.Align.CENTER);

		firstPaint.setTextSize(14 * sp);
		secondPaint.setTextSize(10 * sp);

		firstPaint.setColorFilter(filter);
		secondPaint.setColorFilter(filter);


		if (attrs != null) {
			final TypedArray attributes = getContext()
					.obtainStyledAttributes(attrs, R.styleable.TwoLineTextView);


			// Assign provided attributes to member variables, or use the defaults if necessary
			firstPaint.setTextSize(attributes
					.getDimensionPixelSize(R.styleable.TwoLineTextView_firstLineTextSize,
							(int) (14 * sp)));
			secondPaint.setTextSize(attributes
					.getDimensionPixelSize(R.styleable.TwoLineTextView_secondLineTextSize,
							(int) (10 * sp)));
			firstText = attributes.getString(R.styleable.TwoLineTextView_firstLineText);
			secondText = attributes.getString(R.styleable.TwoLineTextView_secondLineText);

			drawable = attributes.getDrawable(R.styleable.TwoLineTextView_topDrawable);

			// Attributes are no longer required
			attributes.recycle();
		}


		if (drawable != null)
			drawable.setColorFilter(filter);


	}


	@Override
	protected void onDraw(Canvas canvas) {

		int w2 = canvas.getWidth() / 2;
		float hs = getPaddingTop();
		int h2 = canvas.getHeight();

		canvas.save();

		canvas.translate(w2, 0);


		if (drawable != null) {
			int dw = drawable.getIntrinsicWidth();
			int dh = drawable.getIntrinsicHeight();

			drawable.setBounds(-dw / 2, (int) hs, dw / 2, dh);
			drawable.draw(canvas);
			hs += dh;
		}

		hs += firstPaint.getTextSize();
		if (firstText != null)
			canvas.drawText(firstText, 0, hs, firstPaint);

		hs = h2 - secondPaint.getTextSize() - getPaddingBottom();

		if (secondText != null)
			canvas.drawText(secondText, 0, hs, secondPaint);


		canvas.restore();


	}


	static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;

	private PorterDuff.Mode mCurrentMode = DEFAULT_TINT_MODE;

	private PorterDuffColorFilter filter = new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN);

	public int getTintColor() {
		return tintColor;
	}

	public void setTintColor(int tintColor) {
		this.tintColor = tintColor;
		filter = new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN);

		firstPaint.setColorFilter(filter);
		secondPaint.setColorFilter(filter);
		if (drawable != null)
			drawable.setColorFilter(filter);
		invalidate();
	}
}
