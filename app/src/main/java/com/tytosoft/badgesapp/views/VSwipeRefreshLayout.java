package com.tytosoft.badgesapp.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by Kos on 07.12.2015.
 */
public class VSwipeRefreshLayout extends SwipeRefreshLayout {
	public VSwipeRefreshLayout(Context context) {
		super(context);
	}

	public VSwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private boolean mMeasured = false;
	private boolean mPreMeasureRefreshing = false;

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!mMeasured) {
			mMeasured = true;
			setRefreshing(mPreMeasureRefreshing);
		}
	}


	@Override
	public void setRefreshing(boolean refreshing) {
		if (mMeasured) {
			super.setRefreshing(refreshing);
		} else {
			mPreMeasureRefreshing = refreshing;
		}
	}

}
