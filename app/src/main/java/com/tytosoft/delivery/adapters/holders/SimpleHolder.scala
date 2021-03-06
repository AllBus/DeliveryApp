package com.tytosoft.delivery.adapters.holders

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.OnClickListener
import android.widget.{ImageView, TextView}
import com.kos.fastuimodule.common.ui.{U, US}
import com.kos.fastuimodule.good.common.model.IEntity
import com.tytosoft.delivery.R

/**
  * Created by Kos on 06.07.2016.
  */
class SimpleHolder(topView:View,itemClick:OnClickListener) extends RecyclerView.ViewHolder(topView){

	@inline def find[T](@IdRes id: Int) = itemView.findViewById(id).asInstanceOf[T]

	val image=find[ImageView](R.id.image)
	val name=find[TextView](R.id.name)
	val text=find[TextView](R.id.text)

	itemView.setOnClickListener(itemClick)

	def bind(position:Int,element:IEntity):Unit ={
		itemView.setTag(element)
		if (image!=null)
			U.image(image,element.getImage)

		US.text(name,element.getName)
		US.text(text,element.getInfo)


	}
}
