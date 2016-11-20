package com.tytosoft.delivery.adapters.holders

import android.graphics.Paint
import android.view.View
import android.view.View.OnClickListener
import android.widget.{Button, TextView}
import com.kos.fastuimodule.good.common.model.IEntity
import com.tytosoft.badgesapp.graphic.shapes.TicketDrawable
import com.tytosoft.badgesapp.model.OrderModel
import com.tytosoft.delivery.R
import com.tytosoft.delivery.common.helpers.ResHelper

/**
  * Created by Kos on 06.07.2016.
  */
class ZakazHolder(itemView: View, clickAddToTash: OnClickListener, itemClick: OnClickListener) extends SimpleHolder(itemView, itemClick) {

	val discount = find[TextView](R.id.discount)
	val addToTash = find[Button](R.id.addToTashBtn)
	val oldPrice = find[TextView](R.id.oldPrice)

	addToTash.setOnClickListener(clickAddToTash)
	val count = find[TextView](R.id.countLabel)
	oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG)

	val backDrawable=new TicketDrawable(12,6*ResHelper.dp(itemView.getContext))
	itemView.setBackground(backDrawable)

	override def bind(position: Int, elem: IEntity) {
		super.bind(position, elem)
		elem match {
			case element: OrderModel ⇒



			case _ ⇒
		}
		//image.setBackground(backDrawable)
	}
}
