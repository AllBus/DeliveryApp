package com.tytosoft.delivery.adapters.holders

import android.graphics.Paint
import android.view.View
import android.view.View.OnClickListener
import android.widget.{Button, TextView}
import com.kos.delivery.net.DataStore
import com.kos.fastuimodule.common.ui.{U, US}
import com.kos.fastuimodule.good.common.model.IEntity
import com.tytosoft.badgesapp.model.ProductModel
import com.tytosoft.delivery.R

/**
  * Created by Kos on 06.07.2016.
  */
class FullProductHolder(itemView: View, clickAddToTash: OnClickListener, itemClick: OnClickListener) extends SimpleHolder(itemView, itemClick) {

	import DataStore._

	val dataLayout= find[View](R.id.dataLayout)
	val discount = find[TextView](R.id.discount)
	val addToTash = find[TextView](R.id.addToTashBtn)
	val oldPrice = find[TextView](R.id.oldPrice)
	val priceLabel = find[TextView](R.id.priceLabel)
	if (addToTash!=null)
		addToTash.setOnClickListener(clickAddToTash)

	val count = find[TextView](R.id.countLabel)

	if (oldPrice!=null)
		oldPrice.setPaintFlags(oldPrice.getPaintFlags | Paint.STRIKE_THRU_TEXT_FLAG)
	//val backDrawable=new TicketDrawable(5,6*ResHelper.dp(itemView.getContext))

	override def bind(position: Int, elem: IEntity) {
		super.bind(position, elem)
		if (elem.isNull){
			dataLayout.setVisibility(View.INVISIBLE)
		}else {
			dataLayout.setVisibility(View.VISIBLE)
			elem match {
				case element: ProductModel ⇒

					if (discount != null)
						U.visible(discount, element.hasDiscount)

					if (oldPrice != null)
						U.visible(oldPrice, element.hasOldPrice)

					US.text(discount, element.getSale)

					if (addToTash != null) {
						addToTash.setTag(element)
						U.visible(addToTash,element.isActive)
						if (priceLabel==null)
							U.text(addToTash, preferences.price(element.getPricesCurrent))
						else{
							U.text(priceLabel, preferences.price(element.getPricesCurrent))

						}
					}

					if (element.hasOldPrice) {
						US.text(oldPrice, preferences.price(element.getPricesOld))
					}
					US.text(count, element.getWeight)
				case _ ⇒
			}
		}
		//image.setBackground(backDrawable)
	}
}
