package com.tytosoft.badgesapp.adapters.holders

import android.graphics.Paint
import android.view.View
import android.view.View.OnClickListener
import android.widget.{Button, TextView}
import com.kos.delivery.net.DataStore
import com.kos.delivery.net.DataStore._
import com.kos.fastuimodule.common.ui.{U, US}
import com.kos.fastuimodule.good.common.model.IEntity
import com.tytosoft.delivery.R
import com.tytosoft.delivery.adapters.holders.SimpleHolder
import com.tytosoft.delivery.model.AdsModel

/**
  * Created by Kos on 06.07.2016.
  */
class AdsProductHolder(itemView: View, clickAddToTash: OnClickListener, itemClick: OnClickListener) extends SimpleHolder(itemView, itemClick) {

	val discount = find[TextView](R.id.discount)
	val addToTash = find[Button](R.id.addToTashBtn)
	val oldPrice = find[TextView](R.id.oldPrice)
	val dataLayout= find[View](R.id.dataLayout)
	addToTash.setOnClickListener(clickAddToTash)
	val count = find[TextView](R.id.countLabel)
	oldPrice.setPaintFlags(oldPrice.getPaintFlags | Paint.STRIKE_THRU_TEXT_FLAG)
	//val backDrawable=new TicketDrawable(5,6*ResHelper.dp(itemView.getContext))

	override def bind(position: Int, elem: IEntity) {
		itemView.setTag(elem)

		if (elem.isNull){
			dataLayout.setVisibility(View.INVISIBLE)
		}else {
			dataLayout.setVisibility(View.VISIBLE)
			elem match {
				case element: AdsModel ⇒
					if (!element.isNull) {
						DataStore.products(element.getProductId)
					}
					if (discount != null)
						U.visible(discount, element.hasDiscount)

					if (oldPrice != null)
						U.visible(oldPrice, element.hasOldPrice)


					U.image(image, element.getImageBig)
					U.text(name, element.getName)

					US.text(text, element.getInfo)

					US.text(discount, element.getSale)

					if (addToTash != null) {
						addToTash.setTag(element)
						U.visible(addToTash,element.isActive)
						U.text(addToTash, preferences.price(element.getPricesCurrent))
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
