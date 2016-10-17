package com.tytosoft.badgesapp.adapters.holders

import android.view.View.OnClickListener
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{Button, TextView}
import com.kos.delivery.common.holders.ListHolder
import com.kos.delivery.net.DataStore
import com.kos.fastuimodule.common.ui.U
import com.tytosoft.badgesapp.common.adapters.universal.ItemHolder
import com.tytosoft.badgesapp.graphic.shapes.TicketDrawable
import com.tytosoft.badgesapp.model.OrderModel
import com.tytosoft.delivery.R
import com.tytosoft.delivery.common.helpers.ResHelper
import com.tytosoft.delivery.common.utils.UU

/**
  * Created by Kos on 06.07.2016.
  */
class OrderProductHolder(itemView: View,val itemClick:OnClickListener) extends ItemHolder[OrderModel](itemView, null) {

//	val name=find[TextView](R.id.name)
//	val count=find[TextView](R.id.count)
//	val price=find[TextView](R.id.price)
//	val active=find[View](R.id.active)
	val statusText=find[TextView](R.id.statusText)
	val dostavkaPrice=find[TextView](R.id.dostavkaPrice)
	val summa=find[TextView](R.id.summa)
	val productLList=find[ViewGroup](R.id.productLList)
	val retryBtn=find[Button](R.id.retryBtn)

	val listHolder=new ListHolder[View](productLList,R.layout.item_ticket_product,LayoutInflater.from(itemView.getContext),
		{x ⇒ x})

	val backDrawable=new TicketDrawable(12,6*ResHelper.dp(itemView.getContext))
	itemView.setBackground(backDrawable)
	//val backDrawable=new TicketDrawable(5,6*ResHelper.dp(itemView.getContext))
	retryBtn.setOnClickListener(itemClick)

	override def bind(position: Int, elem: OrderModel) {
		super.bind(position,elem)


		statusText.setText(elem.getStatus.getName)
		dostavkaPrice.setText( DataStore.preferences.price(elem.deliveryCost))
		summa.setText(DataStore.preferences.price(elem.getSumma))

		var b=true
		listHolder.regenerate(elem.size,(view,pos)⇒{
			val productHolder=elem.products(pos)
			val product=productHolder.product
			U.text(view,R.id.name,product.getName)
			U.text(view,R.id.count,String.valueOf(productHolder.getCount))
			U.text(view,R.id.price,DataStore.preferences.price(productHolder.getPrice))
			if (!product.isActive)
				b=false
			UU.visible(view.findViewById(R.id.price),view.findViewById(R.id.active),product.isActive)
			view.setOnClickListener(itemClick)
			view.setTag(product)
		})

		retryBtn.setEnabled(b)
		retryBtn.setTag(elem)


//		U.text(name,product.getName)
//		U.text(count,String.valueOf(elem.getCount))
//		U.text(price,DataStore.preferences.price(elem.getPrice))
//		UU.visible(price,active,product.isActive)
	}
}
