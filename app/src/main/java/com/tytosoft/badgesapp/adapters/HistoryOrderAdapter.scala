package com.tytosoft.badgesapp.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.View.OnClickListener
import com.kos.delivery.common.adapters.universal.ListAdapter
import com.kos.fastuimodule.good.common.ID
import com.tytosoft.badgesapp.adapters.holders.OrderProductHolder
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
