package com.tytosoft.delivery.adapters.holders

import android.view.{LayoutInflater, View, ViewGroup}

/**
  * Created by Kos on 10.07.2016.
  */
class ListHolder[T](
					   val layout:ViewGroup,
					   private[this] val resLayoutId: Int,
					   private[this] val layoutInflater: LayoutInflater,
					   getItemMethod:  View ⇒ T  = {	x : View ⇒ x.asInstanceOf[T]	}
				   ) {

	def check(checker: (T, Int) ⇒ Boolean):Boolean = {
		if (layout==null)
			return true
		for (i ← 0 until layout.getChildCount) {
			if (!checker(getItemMethod(layout.getChildAt(i)),i))
				return false
		}
		true
	}

	def regenerate(count: Int, binder: (T, Int) ⇒ Unit):Unit = {
		if (layout==null)
			return

		while (count>layout.getChildCount){
			val view = layoutInflater.inflate(resLayoutId,layout,false)
			layout.addView(view)
		}
		if (count<layout.getChildCount) {
			layout.removeViews(count, layout.getChildCount - count)
		}

		for (i ← 0 until layout.getChildCount) {
			binder(getItemMethod(layout.getChildAt(i)),i)
		}
	}

	def bind(position: Int, binder: (T, Int) ⇒ Unit): Unit ={
		if (layout==null)
			return

		if (position>= 0 && position<layout.getChildCount)
			binder(getItemMethod(layout.getChildAt(position)),position)

	}
}