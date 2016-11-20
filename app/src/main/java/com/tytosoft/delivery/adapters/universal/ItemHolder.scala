package com.tytosoft.delivery.adapters.universal

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.OnClickListener

/**
  * Created by Kos on 19.07.2016.
  */
class ItemHolder[U](topView:View,itemClick:OnClickListener) extends RecyclerView.ViewHolder(topView){

	@inline def find[T](@IdRes id: Int) = itemView.findViewById(id).asInstanceOf[T]

	itemView.setOnClickListener(itemClick)

	def bind(position:Int,element:U){
		itemView.setTag(element)
	}
}