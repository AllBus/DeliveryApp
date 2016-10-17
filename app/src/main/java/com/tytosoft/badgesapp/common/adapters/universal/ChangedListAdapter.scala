package com.tytosoft.badgesapp.common.adapters.universal

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.View
import com.kos.delivery.common.adapters.universal.{IChangedListModel, ListAdapter}

/**
  * Created by Kos on 19.07.2016.
  */
class ChangedListAdapter[ListID,U,T<: ItemHolder[U],W<:AnyRef](
		context:Context,
		@LayoutRes layoutRes:Int,
		list: IChangedListModel[ListID,U,W],
		constructor:  View ⇒T
	) extends ListAdapter[ListID,U,T,W](
	context,
	layoutRes,
	list,
	constructor
	){

	def remove(position:Int): Unit ={
		if (this.list.remove(position)) {
			notifyItemRemoved(position)
		}
	}

	def add(position:Int,element: U): Unit ={
		if (this.list.add(position,element)) {
			notifyItemInserted(position)
		}
	}

	def add(element: U): Unit ={
		if (this.list.add(element)) {
			notifyDataSetChanged()
		}
	}



}
