package com.kos.delivery.common.adapters.universal

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, View, ViewGroup}
import com.tytosoft.badgesapp.common.adapters.universal.{ItemHolder, IListModel}

/**
  * Created by Kos on 19.07.2016.
  */
class ListAdapter[@specialized ListID,U,T<: ItemHolder[U],W<:AnyRef](
										 context:Context,
										 @LayoutRes layoutRes:Int,
										 private[this] val list: IListModel[ListID,U,W] ,
										constructor:  View ⇒T
										 ) extends RecyclerView.Adapter[T]{

	val inflater=LayoutInflater.from(context)


	def getList = list.getList

	override def getItemCount: Int = {
		list.size
	}

	def getItem(position: Int):U ={
		list(position)
	}

	override def onBindViewHolder(holder: T, position: Int): Unit = {
		holder.bind(position,getItem(position))
	}

	override def onCreateViewHolder(parent: ViewGroup, viewType: Int): T = {
		constructor( inflater.inflate(layoutRes, parent,false))
	}

	def changeList(model: W) :Boolean= {
		if (list.change(model)) {
			notifyDataSetChanged()
			true
		}else
			false
	}

	def updateItems(id: Seq[ListID]): Unit = {
		id.foreach{x ⇒
			val y=list.indexOf(x)
			if (y>=0)
				notifyItemChanged(y)
		}
	}

	def update(position:Int): Unit ={
		notifyItemChanged(position)
	}


}
