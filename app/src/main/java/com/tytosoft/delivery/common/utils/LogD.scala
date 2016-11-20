package com.tytosoft.delivery.common.utils

import android.util.Log

import scala.annotation.elidable
import scala.annotation.elidable._

/**
  * Created by Kos on 14.07.2016.
  */
object LogD {
	@elidable(ASSERTION)
	def apply(text:String): Unit ={
		Log.d("Kos",text)
	}
	@elidable(ASSERTION)
	def apply(text:Object): Unit ={
		Log.d("Kos",""+text)
	}
}
