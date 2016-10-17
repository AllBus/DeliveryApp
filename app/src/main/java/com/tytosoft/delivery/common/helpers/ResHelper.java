package com.tytosoft.delivery.common.helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import com.tytosoft.delivery.R;

/**
 * Несколько функций для быстрого получения значений рсурсов
 * Created by Kos on 08.07.2016.
 */
public class ResHelper {
	/**
	 * Blends two colours to produce a single output colour. No check is done to ensure the
	 * provided colors are valid ARGB hex codes. Providing invalid codes will result in an
	 * undefined result.
	 *
	 * @param color1
	 * 		ARGB hex code for the first colour to blend
	 * @param color2
	 * 		ARGB hex code for the second colour to blend
	 * @param ratio
	 * 		the proportion of {@code color1} to use in the blended result, between 0 and 1
	 * 		(inclusive)
	 * @return the ARGB code for the blended colour
	 */

	public static int blendColors(final int color1, final int color2, float ratio) {
		if (ratio < 0) ratio = 0;
		else
			if (ratio > 1)
				ratio=1;

		final float a = (Color.alpha(color1) * ratio) + (Color.alpha(color2) * (1f - ratio));
		final float r = (Color.red(color1) * ratio) + (Color.red(color2) * (1f - ratio));
		final float g = (Color.green(color1) * ratio) + (Color.green(color2) * (1f - ratio));
		final float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * (1f - ratio));

		return Color.argb((int) a, (int) r, (int) g, (int) b);
	}

	/**
	 * Extracts the primary color from a theme.
	 *
	 * @param context
	 * 		the context to get the theme of
	 * @param defaultColor
	 * 		the color to return if no primary color is found, as an ARGB hex code
	 * @return the primary color or the default if not found
	 */
	public static int getPrimaryColor(Context context, int defaultColor) {
		return getColor(context, defaultColor, R.attr.colorPrimary);
	}

	/**
	 * Extracts the primary dark color from a theme.
	 *
	 * @param context
	 * 		the context to get the theme of
	 * @param defaultColor
	 * 		the color to return if no primary dark color is found, as an ARGB hex code
	 * @return the primary dark color or the default if not found
	 */
	public static int getPrimaryDarkColor(Context context, int defaultColor) {
		return getColor(context, defaultColor, R.attr.colorPrimaryDark);
	}

	/**
	 * Extracts the accent color from a theme.
	 *
	 * @param context
	 * 		the context to get the theme of
	 * @param defaultColor
	 * 		the color to return if no accent color is found, as an ARGB hex code
	 * @return the accent color or the default if not found
	 */
	public static int getAccentColor(Context context, int defaultColor) {
		return getColor(context, defaultColor, R.attr.colorAccent);
	}

	private static int getColor(Context context, int defaultColor, int colorAttr) {
		final TypedValue v = new TypedValue();
		final TypedArray a = context.obtainStyledAttributes(v.data, new int[]{colorAttr});
		final int color = a.getColor(0, defaultColor);
		a.recycle();
		return color;
	}

	//============================

	public static float px(final Context context, @DimenRes final int dimenId){
		return context.getResources().getDimension(dimenId);
	}

	public static float dp( final Context context){
		return context.getResources().getDisplayMetrics().density;
	}

	public static float sp( final Context context){
		return context.getResources().getDisplayMetrics().scaledDensity;
	}

	/**
	 * Converts a dimension from display-independent pixels (dp) to pixels (px).
	 *
	 * @param dpValue
	 * 		the dimension to convert, measured in display-independent pixels
	 * @param context
	 * 		the context of the conversion
	 * @return the supplied dimension converted to pixels
	 */
	public static float dpToPx(final Context context, final int dpValue) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * Converts a dimension from scaled pixels (sp) to pixels (px).
	 *
	 * @param spValue
	 * 		the dimension to convert, measured in scaled pixels
	 * @param context
	 * 		the context of the conversion
	 * @return the supplied dimension converted to pixels
	 */
	public static float spToPx(final Context context, final float spValue) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * Converts a dimension from inches (in) to pixels (px).
	 *
	 * @param inValue
	 * 		the dimension to convert, measured in inches
	 * @param context
	 * 		the context of the conversion
	 * @return the supplied dimension converted to pixels
	 */
	public static float inToPx(final Context context, final float inValue) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, inValue,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * Converts a dimension from millimetres (mm) to pixels (px).
	 *
	 * @param mmValue
	 * 		the dimension to convert, measured in millimetres
	 * @param context
	 * 		the context of the conversion
	 * @return the supplied dimension converted to millimetres
	 */
	public static float mmToPx(final Context context, final float mmValue) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mmValue,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * Converts a dimension from points (pt) to pixels (px).
	 *
	 * @param ptValue
	 * 		the dimension to convert, measured in points
	 * @param context
	 * 		the context of the conversion
	 * @return the supplied dimension converted to points
	 */
	public static float ptToPx(final Context context, final float ptValue) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, ptValue,
				context.getResources().getDisplayMetrics());
	}

	public static Drawable drawable(Context context, @DrawableRes int drawableId) {

		if (Build.VERSION.SDK_INT >= 21){
			return context.getDrawable(drawableId);
		}else {
			return context.getResources().getDrawable(drawableId);
		}
	}

	public static void tintingMenu(Menu menu,int tintColor){
		for (int i=0;i<menu.size();i++){
			MenuItem item= menu.getItem(i);
			Drawable drawable = DrawableCompat.wrap(item.getIcon());
			DrawableCompat.setTint(drawable,tintColor);
			item.setIcon(drawable);
		}
	}
}
