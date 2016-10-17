package com.tytosoft.delivery.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, View, ViewGroup}

import com.kos.delivery.net.DataStore
import com.kos.fastuimodule.good.common._
import com.kos.fastuimodule.good.common.model.IEntity
import com.tytosoft.delivery.adapters.holders.SimpleHolder
import com.tytosoft.delivery.model.ListModel

/**
  * Created by Kos on 06.07.2016.
  */
class SimpleAdapter[T<: SimpleHolder](
										 context:Context,
										 @LayoutRes layoutResFull:Int,
										 @LayoutRes layoutRes:Int,
										 constructor: (View) ⇒ T
										 ) extends RecyclerView.Adapter[T]{



	val infalter=LayoutInflater.from(context)
	private[this] var isSection=false
	private[this] var list=ListModel.NULL


	def getList = list

	override def getItemCount: Int = {
		list.size
	}

	def getItem(position: Int):IEntity = {
		if (isSection)
			DataStore.sections(list(position))
		else
			DataStore.products(list(position))

	}

	override def onBindViewHolder(holder: T, position: Int): Unit = {
		holder.bind(position,getItem(position))
	}

	override def onCreateViewHolder(parent: ViewGroup, viewType: Int): T = {
		constructor( infalter.inflate(if (isSection) layoutRes else layoutResFull,parent,false))
	}



	def changeList(model: ListModel) = {
		if (model ne list){
			list=model
			isSection= (list.isSection)
			notifyDataSetChanged()
		}
	}

	def updateItems(id: Seq[ID]): Unit = {
		id.foreach{x ⇒
			val y=list.indexOf(x)
			if (y>=0)
				notifyItemChanged(y)
		}
	}
}
