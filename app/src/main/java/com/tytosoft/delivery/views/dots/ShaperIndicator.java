/*
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tytosoft.delivery.views.dots;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


import com.tytosoft.delivery.R;
import com.tytosoft.delivery.common.helpers.ResHelper;


import java.util.ArrayList;
import java.util.List;

/**
 * Displays a set of dots to indicate the selected item in a set.
 */
public final class ShaperIndicator extends FrameLayout {
	/**
	 * Used to identify this class during debugging.
	 */
	@SuppressWarnings("unused")
	private static final String TAG = "[DotIndicator]";

	/**
	 * Default value for the {@code numberOfDots} attribute. This value is used if the attribute is
	 * not supplied.
	 */
	private static final int DEFAULT_NUMBER_OF_DOTS = 1;

	/**
	 * Default value for the {@code selectedDotIndex} attribute. This value is used if the attribute
	 * is not supplied.
	 */
	private static final int DEFAULT_SELECTED_DOT_INDEX = 0;

	/**
	 * Default value for the {@code unselectedDotDiameter} attribute. This value is used if the
	 * attribute is not supplied. This value has units of display-independent pixels.
	 */
	private static final int DEFAULT_UNSELECTED_DOT_DIAMETER_DP = 6;

	/**
	 * Default value for the {@code selectedDotDiameter} attribute. This value is used if the
	 * attribute is not supplied. This value has units of display-independent pixels.
	 */
	private static final int DEFAULT_SELECTED_DOT_DIAMETER_DP = 9;

	/**
	 * Default value for the {@code unselectedDotColor} attribute. This value is used if the
	 * attribute is not supplied. This value is an ARGB hex code.
	 */
	private static final int DEFAULT_UNSELECTED_DOT_COLOR = Color.WHITE;

	/**
	 * Default value for the {@code selectedDotColor} attribute. This value is used if the attribute
	 * is not supplied. This value is an ARGB hex code.
	 */
	private static final int DEFAULT_SELECTED_DOT_COLOR = Color.WHITE;

	/**
	 * Default value for the {@code spacingBetweenDots} attribute. This value is used if the
	 * attribute is not supplied. This value has units of display-independent pixels.
	 */
	private static final int DEFAULT_SPACING_BETWEEN_DOTS_DP = 8;

	/**
	 * Default value for the {@code dotTransitionDuration} attribute. This value is used if the
	 * attribute is not supplied. This value has units of milliseconds.
	 */
	private static final int DEFAULT_DOT_TRANSITION_DURATION_MS = 200;

	/**
	 * The number of dots shown.
	 */
	private int numberOfDots;

	/**
	 * The index of the selected dot, counting from zero.
	 */
	private int selectedDotIndex;

	/**
	 * The diameter to use for the unselected dots.
	 */
	private int unselectedDotHeight;

	/**
	 * The diameter to use for the selected dot.
	 */
	private int selectedDotHeight;

	private int unselectedDotWidth;
	private int selectedDotWidth;
	/**
	 * The color to use for the unselected dots, as an ARGB hex code.
	 */
	private int unselectedDotColor;

	/**
	 * The colour to use for the selected dot, as an ARGB hex code.
	 */
	private int selectedDotColor;

	/**
	 * The spacing between dots. The spacing is measured as the distance between the edges of
	 * consecutive dots. The spacing is applied as if all dots are unselected, and when a dot
	 * changes size to become selected, it stays fixed at its centre.
	 */
	private int spacingBetweenDotsPx;

	/**
	 * The length of time for transitioning a dot between selected and unselected, measured in
	 * milliseconds.
	 */
	private int dotTransitionDuration;


	private int dotIconHeight=0;

	/**
	 * The dots shown in this View.
	 */
	private final ArrayList<ShaperDot> dots = new ArrayList<>();

	private final List<Boolean> checkDot= new ArrayList<>();



	private OnDotClickListener dotClickListener=null;
	private OnClickListener clickListener=null;


	public OnDotClickListener getDotClickListener() {
		return dotClickListener;
	}

	public void setDotClickListener(final OnDotClickListener dotClickListener) {
		this.dotClickListener = dotClickListener;
		if (dotClickListener==null)
			clickListener=null;
		else
			clickListener=new OnClickListener() {
				@Override
				public void onClick(View v) {
					Object o=v.getTag();
					if (o instanceof Integer){
						dotClickListener.onDotClick((int)o);
					}
				}
			};
		resetDotClickListener();
	}

	private void resetDotClickListener() {
		for (int i=0;i<dots.size();i++){
			ShaperDot dot= dots.get(i);
			dot.setTag(i);
			dot.setOnClickListener(clickListener);
		}
	}

	/**
	 * Constructs a new DotIndicator instance. The following default parameters are used:
	 * <ul><li>numberOfDots: 1</li> <li>selectedDotIndex: 0</li> <li>unselectedDotDiameter: 6dp</li>
	 * <li>selectedDotDiameter: 9dp</li> <li>unselectedDotColor: opaque white (i.e. ARGB
	 * 0xFFFFFFFF)</li> <li>selectedDotColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li>
	 * <li>spacingBetweenDots: 7dp</li> <li>dotTransitionDuration: 200ms</li></ul>
	 *
	 * @param context
	 * 		the Context in which this DotIndicator is operating, not null
	 */
	public ShaperIndicator(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	/**
	 * Constructs a new DotIndicator instance. If an attribute specific to this class is not
	 * provided, the relevant default is used. The defaults are:<ul><li>numberOfDots: 1</li>
	 * <li>selectedDotIndex: 0</li> <li>unselectedDotDiameter: 6dp</li> <li>selectedDotDiameter:
	 * 9dp</li> <li>unselectedDotColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li>
	 * <li>selectedDotColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li> <li>spacingBetweenDots:
	 * 7dp</li> <li>dotTransitionDuration: 200ms</li></ul>
	 *
	 * @param context
	 * 		the Context in which this SelectionIndicator is operating, not null
	 * @param attrs
	 * 		configuration attributes, null allowed
	 */
	public ShaperIndicator(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	/**
	 * Constructs a new DotIndicator instance. If an attribute specific to this class is not
	 * provided, the relevant default is used. The defaults are:<ul><li>numberOfDots: 1</li>
	 * <li>selectedDotIndex: 0</li> <li>unselectedDotDiameter: 6dp</li> <li>selectedDotDiameter:
	 * 9dp</li> <li>unselectedDotColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li>
	 * <li>selectedDotColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li> <li>spacingBetweenDots:
	 * 7dp</li> <li>dotTransitionDuration: 200ms</li></ul>
	 *
	 * @param context
	 * 		the context in which this SelectionIndicator is operating
	 * @param attrs
	 * 		configuration attributes, null allowed
	 * @param defStyleAttr
	 * 		an attribute in the current theme which supplies default attributes, pass 0	to ignore
	 */
	public ShaperIndicator(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	/**
	 * Constructs a new DotIndicator instance. If an attribute specific to this class is not
	 * provided, the relevant default is used. The defaults are:<ul><li>numberOfDots: 1</li>
	 * <li>selectedDotIndex: 0</li> <li>unselectedDotDiameter: 6dp</li> <li>selectedDotDiameter:
	 * 9dp</li> <li>unselectedDotColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li>
	 * <li>selectedDotColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li> <li>spacingBetweenDots:
	 * 7dp</li> <li>dotTransitionDuration: 200ms</li></ul>
	 *
	 * @param context
	 * 		the context in which this SelectionIndicator is operating
	 * @param attrs
	 * 		configuration attributes, null allowed
	 * @param defStyleAttr
	 * 		an attribute in the current theme which supplies default attributes, pass 0	to ignore
	 * @param defStyleRes
	 * 		a resource which supplies default attributes, only used if {@code defStyleAttr}	is 0, pass
	 * 		0 to ignore
	 */
	@TargetApi(21)
	public ShaperIndicator(final Context context, final AttributeSet attrs, final int defStyleAttr,
						   final int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * Initialises the member variables of this DotIndicator and creates the UI. This method should
	 * only be invoked during construction.
	 *
	 * @param attrs        configuration attributes, null allowed
	 * @param defStyleAttr an attribute in the current theme which supplies default attributes, pass 0	to ignore
	 * @param defStyleRes  a resource which supplies default attributes, only used if {@code defStyleAttr}	is 0, pass
	 *                     0 to ignore
	 */
	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		// Use a TypedArray to process attrs
		final TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable
				.DotIndicator, defStyleAttr, defStyleRes);

		final float dp = ResHelper.dp(getContext());
		// Need to convert all default dimensions to px

		final int defaultSelectedDotDiameterPx = (int) (DEFAULT_SELECTED_DOT_DIAMETER_DP * dp);
		final int defaultUnselectedDotDiameterPx = (int) (DEFAULT_UNSELECTED_DOT_DIAMETER_DP * dp);
		final int defaultSpacingBetweenDotsPx = (int) (DEFAULT_SPACING_BETWEEN_DOTS_DP * dp);

		// Assign provided attributes to member variables, or use the defaults if necessary
		numberOfDots = attributes
				.getInt(R.styleable.DotIndicator_numberOfDots, DEFAULT_NUMBER_OF_DOTS);
		selectedDotIndex = attributes
				.getInt(R.styleable.DotIndicator_selectedDotIndex,
						DEFAULT_SELECTED_DOT_INDEX);
		unselectedDotHeight = attributes
				.getDimensionPixelSize(R.styleable.DotIndicator_unselectedDotDiameter,
						defaultUnselectedDotDiameterPx);
		selectedDotHeight = attributes
				.getDimensionPixelSize(R.styleable.DotIndicator_selectedDotDiameter,
						defaultSelectedDotDiameterPx);

		unselectedDotWidth = attributes
				.getDimensionPixelSize(R.styleable.DotIndicator_unselectedDotWidth,
						defaultUnselectedDotDiameterPx);
		selectedDotWidth = attributes
				.getDimensionPixelSize(R.styleable.DotIndicator_selectedDotWidth,
						defaultSelectedDotDiameterPx);

		unselectedDotColor = attributes.getColor(R.styleable.DotIndicator_unselectedDotColor,
				DEFAULT_UNSELECTED_DOT_COLOR);
		selectedDotColor = attributes
				.getColor(R.styleable.DotIndicator_selectedDotColor,
						DEFAULT_SELECTED_DOT_COLOR);
		spacingBetweenDotsPx = attributes
				.getDimensionPixelSize(R.styleable.DotIndicator_spacingBetweenDots,
						defaultSpacingBetweenDotsPx);
		dotTransitionDuration = attributes
				.getDimensionPixelSize(R.styleable.DotIndicator_dotTransitionDuration,
						DEFAULT_DOT_TRANSITION_DURATION_MS);
		dotIconHeight = attributes
				.getDimensionPixelSize(R.styleable.DotIndicator_dotIconHeight,1);
		// Attributes are no longer required
		attributes.recycle();

		// Setup UI

		reflectParametersInView();
	}



	/**
	 * Constructs and displays dots based on current member variables.
	 */
	private void reflectParametersInView() {
		// Reset the root View and the dot Collection so that the UI can be entirely recreated
		removeAllViews();
		dots.clear();

		final int spacing=spacingBetweenDotsPx;
		final int maxWidthDim = Math.max(selectedDotWidth, unselectedDotWidth)+spacing;
		final int maxHeightDim = Math.max(Math.max(selectedDotHeight, unselectedDotHeight),dotIconHeight)+spacing;
		// Create the dots incrementally from left to right
		for (int i = 0; i < numberOfDots; i++) {
			// Create a dot and set its properties
			final ShaperDot dot = new ShaperDot(getContext());

			dot.setInactiveSize(unselectedDotWidth, unselectedDotHeight)
					.setActiveSize(selectedDotWidth, selectedDotHeight)
					.setActiveColor(selectedDotColor)
					.setInactiveColor(unselectedDotColor)
					.setTransitionDuration(dotTransitionDuration);


			// Make the dot active if necessary
			if (i == selectedDotIndex) {
				dot.setActive(false);
			} else {
				dot.setInactive(false);
			}

			// Create the positioning parameters

			final int startMargin = i * (spacingBetweenDotsPx + unselectedDotWidth);


			LayoutParams params = new LayoutParams(maxWidthDim, maxHeightDim);
			params.setMargins(startMargin, 0,0, 0);


			// RTL layout support
			if (Build.VERSION.SDK_INT >= 17) {
				params.setMarginStart(startMargin);
			}

			// Apply the positioning parameters and add the dot to the UI
			dot.setLayoutParams(params);


			addView(dot);

			// Keep a record of the dot for later use
			dots.add(i, dot);
		}
		resetDotClickListener();
	}

	/**
	 * Destroys the UI and recreates it.
	 */
	public void redrawDots() {
		reflectParametersInView();
	}

	/**
	 * Sets the diameter to use for the unselected dots.
	 *
	 */
	public void setUnselectedDotSize(final int x,final int y) {
		this.unselectedDotWidth = x;
		this.unselectedDotHeight=y;
		reflectParametersInView();
	}


	/**
	 * Sets the diameter to use for the selected dot.
	 *

	 */
	public void setSelectedDotSize(final int x,final int y) {
		this.selectedDotWidth = x;
		this.selectedDotHeight=y;
		reflectParametersInView();
	}


	/**
	 * Sets the color to use for the unselected dots.
	 *
	 * @param unselectedDotColor
	 * 		the color to use, as an ARGB hex code
	 */
	public void setUnselectedDotColor(final int unselectedDotColor) {
		this.unselectedDotColor = unselectedDotColor;
		reflectParametersInView();
	}

	/**
	 * @return the current unselected dot color, as an ARGB hex code
	 */
	public int getUnselectedDotColor() {
		return unselectedDotColor;
	}

	/**
	 * Sets the color to use for the selected dot.
	 *
	 * @param selectedDotColor
	 * 		the color to use, as an ARGB hex code
	 */
	public void setSelectedDotColor(final int selectedDotColor) {
		this.selectedDotColor = selectedDotColor;
		reflectParametersInView();
	}

	/**
	 * @return the current unselected dot color, as an ARGB hex code
	 */
	public int getSelectedDotColor() {
		return selectedDotColor;
	}

	/**
	 * Sets the spacing between dots. The spacing is measured as the distance between the edges of
	 * consecutive unselected dots. The spacing is applied as if all dots are unselected, and when a
	 * dot changes size to become selected, it stays fixed at its centre.
	 *
	 * @param spacingBetweenDotsPx
	 * 		the spacing to use, measured in pixels
	 */
	public void setSpacingBetweenDotsPx(final int spacingBetweenDotsPx) {
		this.spacingBetweenDotsPx = spacingBetweenDotsPx;
		reflectParametersInView();
	}

	/**
	 * Sets the spacing between dots. The spacing is measured as the distance between the edges of
	 * consecutive unselected dots. The spacing is applied as if all dots are unselected, and when a
	 * dot changes size to become selected, it stays fixed at its centre.
	 *
	 * @param spacingBetweenDotsDp
	 * 		the spacing to use, measured in display-independent pixels
	 */
	public void setSpacingBetweenDotsDp(final int spacingBetweenDotsDp) {
		final int spacingPx = (int) ResHelper.dpToPx( getContext(),spacingBetweenDotsDp);
		setSpacingBetweenDotsPx(spacingPx);
	}

	/**
	 * Returns the current spacing between dots. The spacing is measured as the distance between the
	 * edges of consecutive unselected dots. The spacing is applied as if all dots are unselected,
	 * and when a dot changes size to become selected, it stays fixed at its centre.
	 *
	 * @return the current spacing, measured in pixels
	 */
	public int getSpacingBetweenDots() {
		return spacingBetweenDotsPx;
	}


	private void updateDot(int index) {
		if (index>=0 && index<dots.size()) {
			dots.get(index).setOk(check(index));
		}
	}

	public void setSelectedItem(final int index, final boolean animate) {
		// If there are no dots, it doesn't make sense to perform an update
		if (dots.size() > 0) {
			try {
				if (index!=selectedDotIndex){

					if (selectedDotIndex > index) {
						int end= Math.min(selectedDotIndex+1,dots.size());
						int start= Math.max(0,index+1);
						for (int i=start;i<end;i++){
							dots.get(i).setInactive(animate);
						}

					}else{
						int end= Math.min(index+1,dots.size());
						int start= Math.max(0,selectedDotIndex);
						for (int i=start;i<end;i++){
							dots.get(i).setActive(animate);
						}
					}

					//updateDot(index);
				}

			} catch (IndexOutOfBoundsException e) {
				// Catch and rethrow the exception to avoid showing the internal implementation

			}

			this.selectedDotIndex = index;
		}
	}


	public int getSelectedItemIndex() {
		return selectedDotIndex;
	}


	public void setNumberOfItems(final int numberOfItems) {
		numberOfDots = numberOfItems;
		checkDot.clear();
		for (int i=0;i<numberOfDots;i++){
			checkDot.add(false);
		}
		reflectParametersInView();
	}


	public int getNumberOfItems() {
		return numberOfDots;
	}


	public void setTransitionDuration(final int transitionDurationMs) {
		dotTransitionDuration = transitionDurationMs;
		reflectParametersInView();
	}


	public int getTransitionDuration() {
		return dotTransitionDuration;
	}


	public void setVisibility(final boolean show) {
		setVisibility(show ? VISIBLE : INVISIBLE);
	}


	public boolean isVisible() {
		return (getVisibility() == VISIBLE);
	}

	public void setSelectedPosition(int position, float positionOffset) {

	//todo: надо менять картинку
	}


	public boolean check(int index){
		if (index>=0 && index<checkDot.size()){
			return checkDot.get(index);
		}
		return false;
	}

	public void check(int index,boolean newValue){
		if (index>=0 && index<checkDot.size()){
			if (check(index)!=newValue) {
				checkDot.set(index, newValue);
				updateDot(index);
			}
		}
	}




}