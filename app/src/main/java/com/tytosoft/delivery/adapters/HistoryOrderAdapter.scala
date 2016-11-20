package com.tytosoft.delivery.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.View.OnClickListener
import com.tytosoft.delivery.adapters.universal.ListAdapter
import com.tytosoft.delivery.adapters.holders.OrderProductHolder
import com.tytosoft.delivery.model.controler.OrderModelController

/**
  * Created by Kos on 21.07.2016.
  */
class HistoryOrderAdapter(context: Context,
						  @LayoutRes layoutRes: Int,
						  listController: OrderModelController,
						  itemClick: OnClickListener

						 ) extends ListAdapter(
	context,
	layoutRes,
	listController,
	new OrderProductHolder(_, itemClick)
) {
//	var orderList=Seq.empty[ID]
//
//	override def changeList(model: Seq[ID]) = {
//		if (super.changeList(model)){
//
//			notifyDataSetChanged()
//		}
//
//
//	}
}
