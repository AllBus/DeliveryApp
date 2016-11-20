package com.tytosoft.delivery.views.sviews

import android.support.v7.widget.RecyclerView
import com.kos.fastuimodule.good.common.model.IEntity

/**
 * Created by Kos on 24.09.2015.
  *
 */

abstract class IConferenceAdapter[T <: RecyclerView.ViewHolder] extends RecyclerView.Adapter[T] {

	def loadIsEmpty(): Boolean //this is abstract method

	def reload(): Unit //this is abstract method

	//def refresh(id: Seq[Int]): Unit //this is abstract method

	def changeArray(): Unit //this is abstract method

	def getItem(position: Int):IEntity
}
