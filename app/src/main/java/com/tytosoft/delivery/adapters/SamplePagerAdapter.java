package com.tytosoft.delivery.adapters;


import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import com.tytosoft.delivery.model.ListModel;

import java.util.List;

public class SamplePagerAdapter extends PagerAdapter {


//	ListModel list=ListModel.NULL();
	int listSize=0;

	List<View> pages = null;

	public SamplePagerAdapter(List<View> pages){
		this.pages = pages;
	}

	@Override
	public Object instantiateItem(ViewGroup collection, int position){
		View v = pages.get(position);
		collection.addView(v, 0);
		return v;
	}

	@Override
	public void destroyItem(ViewGroup collection, int position, Object view){
		collection.removeView((View) view);
	}

	@Override
	public int getCount(){	return Math.min(pages.size(),listSize);
	}

	@Override
	public boolean isViewFromObject(View view, Object object){
		return view.equals(object);
	}

	@Override
	public void finishUpdate(ViewGroup container){
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1){
	}

	@Override
	public Parcelable saveState(){
		return null;
	}

	@Override
	public void startUpdate(ViewGroup container){
	}
	public void reset(ListModel model) {
		listSize=model.size();

	}

}