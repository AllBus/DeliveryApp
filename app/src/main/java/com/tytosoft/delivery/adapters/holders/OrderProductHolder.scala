package com.tytosoft.delivery.adapters.holders

import android.view.View.OnClickListener
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.TextView
import com.kos.fastuimodule.common.ui.U
import com.tytosoft.delivery.adapters.universal.ItemHolder
import com.tytosoft.delivery.R
import com.tytosoft.delivery.common.helpers.ResHelper
import com.tytosoft.delivery.common.utils.UU
import com.tytosoft.delivery.graphic.shapes.TicketDrawable
import com.tytosoft.delivery.model.OrderModel
import com.tytosoft.delivery.net.DataStore

/**
  * Created by Kos on 06.07.2016.
  */
class OrderProductHolder(topView: View,val itemClick:OnClickListener) extends ItemHolder[OrderModel](topView, null) {

//	val name=find[TextView](R.id.name)
//	val count=find[TextView](R.id.count)
//	val price=find[TextView](R.id.price)
//	val active=find[View](R.id.active)
	val statusText=find[TextView](R.id.statusText)
	val dostavkaPrice=find[TextView](R.id.dostavkaPrice)
	val statusDate = find[TextView](R.id.statusDate)
	val summa=find[TextView](R.id.summa)
	val productLList=find[ViewGroup](R.id.productLList)
	val nextBtn=find[View](R.id.nextBtn)
	val deliveryAddress= find[TextView](R.id.deliveryAddress)
	val itemListLabel =find[TextView](R.id.itemListLabel)

	val listHolder=new ListHolder[View](productLList,R.layout.item_ticket_product,LayoutInflater.from(itemView.getContext),
		{x ⇒ x})

	val backDrawable=new TicketDrawable(12,6*ResHelper.dp(itemView.getContext))
	itemView.setBackground(backDrawable)
	//val backDrawable=new TicketDrawable(5,6*ResHelper.dp(itemView.getContext))
	if (nextBtn!=null) {
		nextBtn.setOnClickListener(itemClick)
	}

	override def bind(position: Int, elem: OrderModel) {
		super.bind(position,elem)


		if (statusText!=null) {
			statusText.setText(elem.getStatus.getName)
			statusText.setTextColor(elem.getStatus.getColor)
		}
		if (dostavkaPrice!=null) {
			dostavkaPrice.setText(DataStore.preferences.price(elem.deliveryCost))
		}
		if (summa!=null) {
			summa.setText(DataStore.preferences.price(elem.getSumma))
		}
		if (statusDate!=null){
			statusDate.setText(elem.getDate)

		}

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
			U.gone(view.findViewById(R.id.separator),pos<elem.size-1)
		})

		if (itemListLabel!=null){
			itemListLabel.setText(elem.mkString(", "))
		}

		if (deliveryAddress!=null){
			U.text(deliveryAddress,elem.getAddress)
		}

		if (nextBtn!=null) {
			nextBtn.setEnabled(b)
			nextBtn.setTag(elem)

		}



//		U.text(name,product.getName)
//		U.text(count,String.valueOf(elem.getCount))
//		U.text(price,DataStore.preferences.price(elem.getPrice))
//		UU.visible(price,active,product.isActive)
	}
}
