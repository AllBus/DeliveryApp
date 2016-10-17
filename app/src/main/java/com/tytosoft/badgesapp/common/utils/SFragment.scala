package com.tytosoft.badgesapp.common.utils

import android.app.Activity
import android.content.Intent
import android.support.annotation.IdRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.kos.fastuimodule.good.common._

/**
  * Created by Kos on 07.07.2016.
  */
trait SFragment {
	self: Fragment =>

	@inline def find[T](view:View,@IdRes id: Int) = view.findViewById(id).asInstanceOf[T]

	@inline def snack(view: View, text: CharSequence) = Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()

	@inline def toast(text: CharSequence) = Toast.makeText(context,text, Toast.LENGTH_SHORT).show()

	def context ={
		getContext
//		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//			getContext()
//
//		}
//		else {
//			getResources.getDrawable(id)
//
//		}
	}

	def show(activityClass: Class[_]) = startActivity(new Intent(this.context,activityClass))
	def show(activityClass: Class[_],id:ID) ={

		val intent=new Intent(this.getActivity,activityClass)
		intent.putExtra(SActivity.KEY_ID,id)
		startActivity(intent)
	}
}
