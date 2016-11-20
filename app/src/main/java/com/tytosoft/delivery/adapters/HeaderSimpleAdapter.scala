package com.tytosoft.delivery.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, View, ViewGroup}
import com.kos.delivery.net.DataStore
import com.kos.fastuimodule.good.common.ID
import com.kos.fastuimodule.good.common.model.IEntity
import com.tytosoft.badgesapp.model.BaseModel
import com.tytosoft.delivery.adapters.holders.SimpleHolder
import com.tytosoft.delivery.model.ListModel

/**
  * Created by Kos on 06.07.2016.
  */
class HeaderSimpleAdapter(
										 context:Context,
										 @LayoutRes layoutResFull:Int,
										 @LayoutRes layoutRes:Int,
										 @LayoutRes headerLayoutRes:Int,
										 constructor: (View) ⇒ SimpleHolder,
										 constructorHeader: (View) ⇒ SimpleHolder
										 ) extends RecyclerView.Adapter[SimpleHolder]{

	private[this] var list=ListModel.NULL
	private[this] var adsList=ListModel.NULL

	private[this] var isSection=false


	def getList= list

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
				notifyItemChanged(y+1)
		}
	}

	def updateHeader(model: ListModel): Unit = {
		if (model ne adsList){
			adsList=model
			//DataStore.loadFirstItems(adsList.getId)
			notifyItemChanged(0)
		}
	}

	def notifyHeader(): Unit ={
		notifyItemChanged(0)
	}



	val infalter=LayoutInflater.from(context)

	var header:SimpleHolder=_

	val event=new BaseModel()

	override def getItemCount: Int = {
		1+list.size
	}

	def getItem(position: Int):IEntity = {
		if (position<0)
			adsList
		else
		if (isSection)
			DataStore.sections(list(position))
		else
			DataStore.products(list(position))
	}

	override def onBindViewHolder(holder: SimpleHolder, position: Int): Unit = {
		holder.bind(position-1,getItem(position-1))
	}

	override def onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder = {
		if (viewType==0)
			constructor( infalter.inflate(if (isSection) layoutRes else layoutResFull,parent,false))
		else {
			if (header==null)
				header=constructorHeader( infalter.inflate( headerLayoutRes ,parent,false))
			header
		}
	}


	override def getItemViewType(position: Int): Int = {
		if (position==0)
			1
		else
			0
	}
}
