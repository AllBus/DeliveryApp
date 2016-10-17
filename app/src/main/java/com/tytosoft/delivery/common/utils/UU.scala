package com.tytosoft.delivery.common.utils

import android.view.View

/**
  * Created by Kos on 19.07.2016.
  */
object UU {
	def visible(trueView:View,falseView:View,isVisible:Boolean): Unit ={
		if (isVisible) {
			trueView.setVisibility(View.VISIBLE)
			falseView.setVisibility(View.INVISIBLE)
		}else{
			trueView.setVisibility(View.INVISIBLE)
			falseView.setVisibility(View.VISIBLE)
		}
	}
}
