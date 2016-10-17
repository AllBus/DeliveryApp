package com.tytosoft.badgesapp.views.dots;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.tytosoft.delivery.R;
import com.tytosoft.delivery.common.helpers.ResHelper;
import com.tytosoft.delivery.graphic.shapes.RoundShape;


/**
 * Created by Kos on 08.07.2016.
 */
public class ShaperDot extends View {
	/**
	 * Used to identify this class during debugging.
	 */
	@SuppressWarnings("unused")
	private static final String TAG = "[Dot]";

	/**
	 * Default value for the {@code inactiveDiameter} attribute. This value is used if the attribute
	 * is not supplied. This value has units of display-independent pixels.
	 */
	private static final int DEFAULT_INACTIVE_DIAMETER_DP = 6;

	/**
	 * Default value for the {@code activeDotDiameter} attribute. This value is used if the
	 * attribute is not supplied. This value has units of display-independent pixels.
	 */
	private static final int DEFAULT_ACTIVE_DIAMETER_DP = 9;

	/**
	 * Default value for the {@code inactiveColor} attribute. This value is used if the attribute is
	 * not supplied. This value is an ARGB hex code.
	 */
	private static final int DEFAULT_INACTIVE_COLOR = Color.WHITE;

	/**
	 * Default value for the {@code activeColor} attribute. This value is used if the attribute is
	 * not supplied. This value is an ARGB hex code.
	 */
	private static final int DEFAULT_ACTIVE_COLOR = Color.WHITE;

	/**
	 * Default value for the {@code transitionDuration} attribute. This value is used if the
	 * attribute is not supplied. This value is measured in milliseconds.
	 */
	private static final int DEFAULT_TRANSITION_DURATION_MS = 200;

	/**
	 * Default value for the {@code initiallyActive} attribute. This value is used if the attribute
	 * is not supplied.
	 */
	private static final boolean DEFAULT_INITIALLY_ACTIVE = false;

	/**
	 * The diameter of this Dot when inactive.
	 */
	private int inactiveDiameterPx;

	private int inactiveWidthDot;
	private int activeWidthDot;

	/**
	 * The diameter of this Dot when active.
	 */
	private int activeDiameterPx;

	/**
	 * The solid color fill of this Dot when inactive, as an ARGB hex code.
	 */
	private int inactiveColor;

	/**
	 * The solid color fill of this Dot when active, as an ARGB hex code.
	 */
	private int activeColor;

	/**
	 * The amount of time to use when animating this Dot between active and inactive, measured in
	 * milliseconds.
	 */
	private int transitionDurationMs;

	/**
	 * The current state of this Dot, in terms of active/inactive/transitioning.
	 */
	private EDotState state;

	/**
	 * The Drawable used to create the visible part of this Dot.
	 */
	private ShapeDrawable shape;

	private Drawable okDrawable;

	private boolean ok;
//	/**
//	 * Displays the drawable representing this Dot.
//	 */
//	private ImageView drawableHolder;

	/**
	 * The Animator currently acting on this Dot, null if not animating currently.
	 */
	private AnimatorSet currentAnimator = null;

	/**
	 * Constructs a new Dot instance. The following default parameters are used:<ul>
	 * <li>inactiveDiameter: 6dp</li> <li>activeDiameter: 9dp</li> <li>inactiveColor: opaque white
	 * (i.e. ARGB 0xFFFFFFFF)</li> <li>activeColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li>
	 * <li>transitionDuration: 200ms</li> <li>initiallyActive: false</li></ul>
	 *
	 * @param context
	 * 		the Context in which this Dot is operating, not null
	 */
	public ShaperDot(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	/**
	 * Constructs a new Dot instance. If an attribute specific to this class is not provided, the
	 * relevant default is used. The defaults are:<ul> <li>inactiveDiameter: 6dp</li>
	 * <li>activeDiameter: 9dp</li> <li>inactiveColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li>
	 * <li>activeColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li> <li>transitionDuration: 200ms</li>
	 * <li>initiallyActive: false</li></ul>
	 *
	 * @param context
	 * 		the Context in which this Dot is operating, not null
	 * @param attrs
	 * 		configuration attributes, null allowed
	 */
	public ShaperDot(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	/**
	 * Constructs a new Dot instance. If an attribute specific to this class is not provided, the
	 * relevant default is used. The defaults are:<ul> <li>inactiveDiameter: 6dp</li>
	 * <li>activeDiameter: 9dp</li> <li>inactiveColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li>
	 * <li>activeColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li> <li>transitionDuration: 200ms</li>
	 * <li>initiallyActive: false</li></ul>
	 *
	 * @param context
	 * 		the Context in which this Dot is operating, not null
	 * @param attrs
	 * 		configuration attributes, null allowed
	 * @param defStyleAttr
	 * 		an attribute in the current theme which supplies default attributes, pass 0	to ignore
	 */
	public ShaperDot(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	/**
	 * Constructs a new Dot instance. If an attribute specific to this class is not provided, the
	 * relevant default is used. The defaults are:<p/> <li>inactiveDiameter: 6dp</li>
	 * <li>activeDiameter: 9dp</li> <li>inactiveColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li>
	 * <li>activeColor: opaque white (i.e. ARGB 0xFFFFFFFF)</li> <li>transitionDuration: 200ms</li>
	 * <li>initiallyActive: false</li>
	 *
	 * @param context
	 * 		the Context in which this Dot is operating, not null
	 * @param attrs
	 * 		configuration attributes, null allowed
	 * @param defStyleAttr
	 * 		an attribute in the current theme which supplies default attributes, pass 0	to ignore
	 * @param defStyleRes
	 * 		a resource which supplies default attributes, only used if {@code defStyleAttr}	is 0, pass
	 * 		0 to ignore
	 */
	@TargetApi(21)
	public ShaperDot(final Context context, final AttributeSet attrs, final int defStyleAttr,
					 final int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * Initialises the member variables of this Dot and creates the UI. This method should only be
	 * invoked during construction.
	 *
	 * @param attrs
	 * 		configuration attributes, null allowed
	 * @param defStyleAttr
	 * 		an attribute in the current theme which supplies default attributes, pass 0	to ignore
	 * @param defStyleRes
	 * 		a resource which supplies default attributes, only used if {@code defStyleAttr}	is 0, pass
	 * 		0 to ignore
	 */
	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		// Use a TypedArray to process attrs
		final TypedArray attributes = getContext()
				.obtainStyledAttributes(attrs, R.styleable.Dot, defStyleAttr, defStyleRes);

		float dp= ResHelper.dp(getContext());
		// Convert default dimensions to px
		final int defaultActiveDiameterPx = (int) (DEFAULT_ACTIVE_DIAMETER_DP*dp);
		final int defaultInactiveDiameterPx = (int) (DEFAULT_INACTIVE_DIAMETER_DP*dp);

		// Assign provided attributes to member variables, or use the defaults if necessary
		inactiveDiameterPx = attributes
				.getDimensionPixelSize(R.styleable.Dot_inactiveDiameter,
						defaultInactiveDiameterPx);
		activeDiameterPx = attributes
				.getDimensionPixelSize(R.styleable.Dot_activeDiameter, defaultActiveDiameterPx);
		inactiveColor = attributes.getColor(R.styleable.Dot_inactiveColor, DEFAULT_INACTIVE_COLOR);
		activeColor = attributes.getColor(R.styleable.Dot_activeColor, DEFAULT_ACTIVE_COLOR);
		transitionDurationMs = attributes
				.getInt(R.styleable.Dot_transitionDuration, DEFAULT_TRANSITION_DURATION_MS);
		state = attributes.getBoolean(R.styleable.Dot_initiallyActive, DEFAULT_INITIALLY_ACTIVE) ?
				EDotState.ACTIVE : EDotState.INACTIVE;

		inactiveWidthDot = attributes.getDimensionPixelSize(R.styleable.Dot_inactiveWidthDot,
						defaultInactiveDiameterPx);

		activeWidthDot = attributes.getDimensionPixelSize(R.styleable.Dot_activeWidthDot,
						defaultActiveDiameterPx);


		okDrawable=ResHelper.drawable(getContext(),R.drawable.ic_galka);
		// Attributes are no longer required
		attributes.recycle();

		// Ensure the view reflects the attributes
		reflectParametersInView();
	}




	/**
	 * Recreates the UI to reflect the current values of the member variables.
	 */
	private void reflectParametersInView() {
		// Reset root View so that the UI can be entirely recreated
		//	removeAllViews();

		// Make the root View bounds big enough to encompass the maximum diameter
		final int maxYDimension = Math.max(inactiveDiameterPx, activeDiameterPx);
		final int maxXDimension = Math.max(inactiveWidthDot, activeWidthDot);
		setLayoutParams(new ViewGroup.LayoutParams(maxXDimension, maxYDimension));

		// Set the gravity to centre for simplicity
		//	setGravity(Gravity.CENTER);

		// Create the drawable based on the current member variables
		final int diameter = (state == EDotState.ACTIVE) ? activeDiameterPx : inactiveDiameterPx;
		final int width = (state == EDotState.ACTIVE) ? activeWidthDot : inactiveWidthDot;
		final int color = (state == EDotState.ACTIVE) ? activeColor : inactiveColor;

		Paint shaperPen=new Paint();
		shaperPen.setAntiAlias(true);
		shaperPen.setStyle(Paint.Style.STROKE);
		shaperPen.setColor(activeColor);
		shaperPen.setStrokeWidth(1*ResHelper.dp(getContext()));

		shape = new ShapeDrawable(new RoundShape(activeDiameterPx,shaperPen));
		shape.setIntrinsicWidth(width);
		shape.setIntrinsicHeight(diameter);
		shape.getPaint().setColor(color);

		// Add the drawable to the drawable holder
//		drawableHolder = new ImageView(getContext());
//		drawableHolder.setImageDrawable(null); // Forces redraw
//		drawableHolder.setImageDrawable(shape);
//
//		// Add the drawable holder to root View
//		addView(drawableHolder);
	}

	/**
	 * Plays animations to transition the size and color of this Dot.
	 *
	 * @param startSizeX
	 * 		the width and height of this Dot at the start of the animation, measured in pixels
	 * @param endSizeX
	 * 		the width and height of this Dot at the end of the animation, measured in pixels
	 * @param startColor
	 * 		the colour of this Dot at the start of the animation, as an ARGB hex code
	 * @param endColor
	 * 		the colour of this Dot at the end of the animation, as an ARGB hex code
	 * @param duration
	 * 		the duration of the animation, measured in milliseconds
	 * @throws IllegalArgumentException
	 * 		if startSize, endSize or duration are less than 0
	 */
	private void animateDotChange(final int startSizeX, final int startSizeY,
								  final int endSizeX,  final int endSizeY,
								  final int startColor,
								  final int endColor, final int duration) {
		if (startSizeX < 0 || endSizeX<0) {
			throw new IllegalArgumentException("startSize cannot be less than 0");
		} else if (endSizeX < 0 || endSizeY<0) {
			throw new IllegalArgumentException("endSize cannot be less than 0");
		} else if (duration < 0) {
			throw new IllegalArgumentException("duration cannot be less than 0");
		}

		// To avoid conflicting animations, cancel any existing animation
		if (currentAnimator != null) {
			currentAnimator.cancel();
		}

		// Use an animator set to coordinate shape and color change animations
		currentAnimator = new AnimatorSet();
		currentAnimator.setDuration(duration);
		currentAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				// The state must be updated to reflect the transition
				if (state == EDotState.INACTIVE) {
					state = EDotState.TRANSITIONING_TO_ACTIVE;
				} else if (state == EDotState.ACTIVE) {
					state = EDotState.TRANSITIONING_TO_INACTIVE;
				}
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// Make sure state is stable (i.e. unchanging) at the end of the animation
				if (!state.isStable()) {
					state = state.transitioningTo();
				}

				// Make sure the properties are correct
				changeSize(endSizeX,endSizeY);
				changeColor(endColor);
				invalidate();
				// Declare the animation finished
				currentAnimator = null;
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// Make sure state is stable (i.e. unchanging) at the end of the animation
				if (!state.isStable()) {
					state = state.transitioningFrom();
				}

				// Make sure the properties are correct
				changeSize(startSizeX,startSizeY);
				changeColor(startColor);
				invalidate();
				// Declare the animation finished
				currentAnimator = null;
			}
		});

		ValueAnimator transitionSize = ValueAnimator.ofFloat(0f, 1f);
		transitionSize.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float size = (Float) animation.getAnimatedValue();
				int sizeX= (int) (startSizeX+(endSizeX-startSizeX)*size);
				int sizeY= (int) (startSizeY+(endSizeY-startSizeY)*size);

				changeSize(sizeX,sizeY);
				changeColor(ResHelper.blendColors(startColor, endColor, 1.0f-size));
				invalidate();
			}
		});

		currentAnimator.play(transitionSize);
		currentAnimator.start();
	}

	/**
	 * Utility for updating the size of the Dot and reflecting the change in the UI.
	 *

	 */
	private void changeSize(final int newWidth,final int newHeight) {
		shape.setIntrinsicWidth(newWidth);
		shape.setIntrinsicHeight(newHeight);
//		drawableHolder.setImageDrawable(null); // Forces ImageView to update drawable
//		drawableHolder.setImageDrawable(shape);
	}

	/**
	 * Utility for updating the color of the Dot and reflecting the change in the UI.
	 *
	 * @param newColor
	 * 		the desired color, as an ARGB hex code
	 */
	private void changeColor(final int newColor) {
		shape.getPaint().setColor(newColor);
	}

	/**
	 * Sets the inactive diameter of this Dot and updates the UI to reflect the changes. The update
	 * is instantaneous and does not trigger any animations.
	 *
	 * @param inactiveDiameterPx
	 * 		the diameter to use for this Dot when inactive, measured in pixels, not less than 0
	 * @return this Dot
	 * @throws IllegalArgumentException
	 * 		if {@code inactiveDiameterPx} is less than 0
	 */
	public ShaperDot setInactiveDiameterPx(final int inactiveDiameterPx) {
		if (inactiveDiameterPx < 0) {
			throw new IllegalArgumentException("inactiveDiameterPx cannot be less than 0");
		}

		this.inactiveDiameterPx = inactiveDiameterPx;
		reflectParametersInView();
		return this;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//super.onDraw(canvas);


		canvas.save();
		float w2=canvas.getWidth()/2;
		float h2=canvas.getHeight()/2;
		int sw2=shape.getIntrinsicWidth()/2;
		int sh2=shape.getIntrinsicHeight()/2;
		canvas.translate(w2,h2);
		shape.setBounds(-sw2,-sh2,sw2,sh2);
		shape.draw(canvas);

		if (ok){
			sw2=okDrawable.getIntrinsicWidth()/2;
			sh2=okDrawable.getIntrinsicHeight()/2;
			okDrawable.setBounds(-sw2,-sh2,sw2,sh2);
			okDrawable.draw(canvas);
		}
		//	shape.draw(canvas);
		canvas.restore();

	}

	/**
	 * @return the inactive diameter, measured in pixels
	 */
	public int getInactiveDiameter() {
		return inactiveDiameterPx;
	}

	/**
	 * Sets the active diameter of this Dot and updates the UI to reflect the changes. The update is
	 * instantaneous and does not trigger any animations.
	 *
	 * @param activeDiameterPx
	 * 		the diameter to use for this Dot when active, measured in pixels, not less than 0
	 * @return this Dot
	 * @throws IllegalArgumentException
	 * 		if {@code activeDiameterPx} is less than 0
	 */
	public ShaperDot setActiveDiameterPx(final int activeDiameterPx) {
		if (activeDiameterPx < 0) {
			throw new IllegalArgumentException("activeDiameterPx cannot be less than 0");
		}

		this.activeDiameterPx = activeDiameterPx;
		reflectParametersInView();
		return this;
	}

	public ShaperDot setActiveSize(final int x,final int y) {
		if (x < 0 || y<0) {
			throw new IllegalArgumentException("activeDiameterPx cannot be less than 0");
		}

		this.activeWidthDot = x;
		this.activeDiameterPx=y;
		reflectParametersInView();
		return this;
	}

	public ShaperDot setInactiveSize(final int x,final int y) {
		if (x < 0 || y<0) {
			throw new IllegalArgumentException("activeDiameterPx cannot be less than 0");
		}

		this.inactiveWidthDot = x;
		this.inactiveDiameterPx=y;
		reflectParametersInView();
		return this;
	}

	/**
	 * Sets the active diameter of this Dot and updates the UI to reflect the changes. The update is
	 * instantaneous and does not trigger any animations.
	 *
	 * @param activeDiameterDp
	 * 		the diameter to use for this Dot when active, measured in display-independent pixels, not
	 * 		less than 0
	 * @return this Dot
	 * @throws IllegalArgumentException
	 * 		if {@code activeDiameterDp} is less than 0
	 */
	public ShaperDot setActiveDiameterDp(final int activeDiameterDp) {
		if (activeDiameterDp < 0) {
			throw new IllegalArgumentException("activeDiameterDp cannot be less than 0");
		}

		setActiveDiameterPx(activeDiameterDp);
		return this;
	}

	/**
	 * @return the active diameter, measured in pixels
	 */
	public int getActiveDiameter() {
		return activeDiameterPx;
	}

	/**
	 * Sets the inactive color of this Dot and updates the UI to reflect the changes. The update is
	 * instantaneous and does not trigger any animations.
	 *
	 * @param inactiveColor
	 * 		the color to use for this Dot when inactive, as an ARGB hex code
	 * @return this Dot
	 */
	public ShaperDot setInactiveColor(final int inactiveColor) {
		this.inactiveColor = inactiveColor;
		reflectParametersInView();
		return this;
	}

	/**
	 * @return the inactive color, as an ARGB hex code
	 */
	public int getInactiveColor() {
		return inactiveColor;
	}

	/**
	 * Sets the active color of this Dot and updates the UI to reflect the changes. The update is
	 * instantaneous and does not trigger any animations.
	 *
	 * @param activeColor
	 * 		the color to use for this Dot when active, as an ARGB hex code
	 * @return this Dot
	 */
	public ShaperDot setActiveColor(final int activeColor) {
		this.activeColor = activeColor;
		reflectParametersInView();
		return this;
	}

	/**
	 * @return the active color, as an ARGB hex code
	 */
	public int getActiveColor() {
		return activeColor;
	}

	/**
	 * Sets the length of time to use for animations between active and inactive.
	 *
	 * @param transitionDurationMs
	 * 		the length to use for the animations, measured in milliseconds, not less than 0
	 * @return this Dot
	 * @throws IllegalArgumentException
	 * 		if {@code transitionDurationMs} is less than 0
	 */
	public ShaperDot setTransitionDuration(final int transitionDurationMs) {
		if (transitionDurationMs < 0) {
			throw new IllegalArgumentException("transitionDurationMs cannot be less than 0");
		}

		this.transitionDurationMs = transitionDurationMs;
		return this;
	}

	/**
	 * @return the length of time to use for animations between active and inactive, measured in
	 * milliseconds
	 */
	public int getTransitionDuration() {
		return transitionDurationMs;
	}

	/**
	 * Toggles the state of this Dot between active and inactive.
	 *
	 * @param animate
	 * 		whether or not the transition should be animated
	 */
	public void toggleState(final boolean animate) {
		if (currentAnimator != null) {
			currentAnimator.cancel();
		}

		if (state != EDotState.ACTIVE) {
			setActive(animate);
		} else if (state != EDotState.INACTIVE) {
			setInactive(animate);
		} else {
			Log.e(TAG, "[Animation trying to start from illegal state]");
		}
	}

	/**
	 * Transitions this Dot to inactive. Animations are shown if the Dot is not already inactive.
	 *
	 * @param animate
	 * 		whether or not the transition should be animated
	 */
	public void setInactive(final boolean animate) {
		// Any existing animation will conflict with this animations and must be cancelled
		if (currentAnimator != null) {
			currentAnimator.cancel();
		}

		// Animate only if the animation is requested, is necessary, and will actually display
		final boolean shouldAnimate =
				animate && (state != EDotState.INACTIVE) && (transitionDurationMs > 0);

		if (shouldAnimate) {
			animateDotChange(activeWidthDot, activeDiameterPx,inactiveWidthDot, inactiveDiameterPx, activeColor, inactiveColor,
					transitionDurationMs);
		} else {
			// The UI must still be changed, just without animations
			changeSize(inactiveWidthDot, inactiveDiameterPx);
			changeColor(inactiveColor);
			state = EDotState.INACTIVE;
		}
	}

	/**
	 * Transitions this Dot to active. Animations are shown if the Dot is not already active.
	 *
	 * @param animate
	 * 		whether or not the transition should be animated
	 */
	public void setActive(final boolean animate) {
		// Any existing animation will conflict with this animations and must be cancelled
		if (currentAnimator != null) {
			currentAnimator.cancel();
		}

		// Animate only if the animation is requested, is necessary, and will actually display
		final boolean shouldAnimate =
				animate && (state != EDotState.ACTIVE) && (transitionDurationMs > 0);


		if (shouldAnimate) {
			animateDotChange(inactiveWidthDot, inactiveDiameterPx,activeWidthDot, activeDiameterPx, inactiveColor, activeColor,
					transitionDurationMs);
		} else {
			// The UI must still be changed, just without animations
			changeSize(activeWidthDot, activeDiameterPx);
			changeColor(activeColor);
			state = EDotState.ACTIVE;
		}
	}

	/**
	 * Returns the current state of this Dot. This method exists for testing purposes only.
	 *
	 * @return the current state
	 */
	protected EDotState getCurrentState() {
		return state;
	}

	/**
	 * Returns the current diameter of this Dot. This method exists for testing purposes only.
	 * Results will be inconsistent if this Dot is currently transitioning between active and
	 * inactive.
	 *
	 * @return the current diameter, measured in pixels
	 */
	protected int getCurrentDiameter() {
		return shape.getIntrinsicHeight();
	}

	/**
	 * Returns the current color of this Dot. This method exists for testing purposes only. Results
	 * will be inconsistent if this Dot is currently transitioning between active and inactive.
	 *
	 * @return the current color, as an ARGB hex code
	 */
	protected int getCurrentColor() {
		return shape.getPaint().getColor();
	}

	/**
	 * Returns the default inactive diameter. This method exists for testing purposes only.
	 *
	 * @return the default inactive diameter, measured in display-independent pixels
	 */
	protected int getDefaultInactiveDiameterDp() {
		return DEFAULT_INACTIVE_DIAMETER_DP;
	}

	/**
	 * Returns the default active diameter. This method exists for testing purposes only.
	 *
	 * @return the default active diameter, measured in display-independent pixels
	 */
	protected int getDefaultActiveDiameterDp() {
		return DEFAULT_ACTIVE_DIAMETER_DP;
	}

	/**
	 * Returns the default inactive color. This method exists for testing purposes only.
	 *
	 * @return the default inactive color, as an ARGB hex code
	 */
	protected int getDefaultInactiveColor() {
		return DEFAULT_INACTIVE_COLOR;
	}

	/**
	 * Returns the default active color. This method exists for testing purposes only.
	 *
	 * @return the default active color, as an ARGB hex code
	 */
	protected int getDefaultActiveColor() {
		return DEFAULT_ACTIVE_COLOR;
	}

	/**
	 * Returns the default transition duration. This method exists for testing purposes only.
	 *
	 * @return the default transition duration, measured in milliseconds
	 */
	protected int getDefaultTransitionDuration() {
		return DEFAULT_TRANSITION_DURATION_MS;
	}

	/**
	 * Returns whether or not Dots are active by default. This method exists for testing purposes
	 * only.
	 *
	 * @return true if Dots are active by default default, false otherwise
	 */
	protected boolean getDefaultInitiallyActive() {
		return DEFAULT_INITIALLY_ACTIVE;
	}


	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
		invalidate();
	}
}
