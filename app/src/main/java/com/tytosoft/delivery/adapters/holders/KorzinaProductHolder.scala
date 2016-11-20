package com.tytosoft.delivery.adapters.holders

import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import com.kos.fastuimodule.common.ui.U
import com.tytosoft.delivery.adapters.universal.ItemHolder
import com.tytosoft.delivery.R
import com.tytosoft.delivery.common.utils.UU
import com.tytosoft.delivery.model.OrderProductModel
import com.tytosoft.delivery.net.DataStore

/**
  * Created by Kos on 06.07.2016.
  */
class KorzinaProductHolder(itemView: View,itemClick:OnClickListener,plusClick:OnClickListener,minusClick:OnClickListener) extends ItemHolder[OrderProductModel](itemView, itemClick) {

	val name=find[TextView](R.id.name)
	val count=find[TextView](R.id.count)
	val price=find[TextView](R.id.price)
	val active=find[View](R.id.active)
	val plusBtn=find[View](R.id.plusBtn)
	val minusBtn=find[View](R.id.minusBtn)

	if (plusBtn!=null) {
		plusBtn.setTag(itemView)
		plusBtn.setOnClickListener(plusClick)
	}
	if (minusBtn!=null) {
		minusBtn.setTag(itemView)
		minusBtn.setOnClickListener(minusClick)
	}
	//val backDrawable=new TicketDrawable(5,6*ResHelper.dp(itemView.getContext))

	override def bind(position: Int, elem: OrderProductModel) {
		super.bind(position,elem)

		val product=elem.product



		U.text(name,product.getName)
		U.text(count,String.valueOf(elem.getCount))
		U.text(price,DataStore.preferences.price(elem.getPrice))
		UU.visible(price,active,product.isActive)
	}
}
