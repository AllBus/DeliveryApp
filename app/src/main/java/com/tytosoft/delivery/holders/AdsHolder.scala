package com.tytosoft.delivery.holders

import java.util

import android.support.annotation.IdRes
import android.support.v4.view.ViewPager
import android.view.View.OnClickListener
import android.view.{LayoutInflater, View}
import com.kos.delivery.net.DataStore
import com.kos.fastuimodule.good.common._
import com.tytosoft.badgesapp.views.dots.{DotIndicator, OnDotClickListener}
import com.tytosoft.delivery.R
import com.tytosoft.delivery.adapters.SamplePagerAdapter
import com.tytosoft.delivery.adapters.holders.AdsProductHolder
import com.tytosoft.delivery.model.ListModel

import scalaxy.beans._


/**
  * Created by Kos on 07.07.2016.
  */
class AdsHolder(val view:View,itemClick:OnClickListener,clickBtn:OnClickListener)
	extends OnDotClickListener

	with ViewPager.OnPageChangeListener{

	var list=ListModel.NULL

	def setList(model: ListModel): Unit = {
		if (model ne list){
			list=model
			mSectionsPagerAdapter.reset(list)

			for (i ← 0 until Math.min(MAX_ADS_COUNT,list.size)){
				pagesHolder.get(i).bind(i,DataStore.ads(list(i)))

			}

			mSectionsPagerAdapter.notifyDataSetChanged()

			indicator.set(
				//NumberOfItems=mSectionsPagerAdapter.getColumnCount(0),
				NumberOfItems=mSectionsPagerAdapter.getCount,
				DotClickListener=this
			)
		}else{
			for (i ← 0 until Math.min(MAX_ADS_COUNT,list.size)){
				pagesHolder.get(i).bind(i,DataStore.ads.get(list(i)))

			}
		}





		pager.setCurrentItem(DataStore.preferences.adsIndex)
	}

	def updateProduct(productIds:IDSet): Unit ={
		var b=false
		for (i ← 0 until Math.min(MAX_ADS_COUNT, list.size)) {
			val id = list(i)
			if (productIds.contains(id)) {
				pagesHolder.get(i).bind(i,DataStore.ads.get(list(i)))
				b=true
			}
		}
		if (b)
			mSectionsPagerAdapter.notifyDataSetChanged()
	}

	val MAX_ADS_COUNT: Int = 9

	@inline def find[T](@IdRes id: Int) = view.findViewById(id).asInstanceOf[T]

	val pager: ViewPager=find(R.id.adsList)
	val indicator:DotIndicator=find(R.id.adsIndicator)

	val pages=new util.ArrayList[View]()
	val pagesHolder= new util.ArrayList[AdsProductHolder]()

	val inflater= LayoutInflater.from(view.getContext)

	for (i ← 1 to MAX_ADS_COUNT){
		val v=inflater.inflate(R.layout.item_ads,null)
		pages.add(v)
		pagesHolder.add(new AdsProductHolder(v,clickBtn,itemClick))
	}


	val mSectionsPagerAdapter = new SamplePagerAdapter(pages)//new SectionsPagerAdapter(fm)

	indicator.set(
		//NumberOfItems=mSectionsPagerAdapter.getColumnCount(0),
		NumberOfItems=mSectionsPagerAdapter.getCount,
		DotClickListener=this
	)

	pager.set(

		Adapter=mSectionsPagerAdapter
	)
	pager.addOnPageChangeListener(this)

	override def onDotClick(index: Int): Unit = pager.setCurrentItem(index)
		//pager.setCurrentItem(0, index)

	override def onPageScrollStateChanged(state: Int): Unit = {}



	override def onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
	}

	override def onPageSelected(position: Int) {
		indicator.setSelectedItem(position, true)
		DataStore.preferences.adsIndex=position
	}


}
