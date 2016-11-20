package com.tytosoft.delivery.adapters.holders

import android.view.View
import android.view.View.OnClickListener
import com.kos.fastuimodule.good.common.model.IEntity
import com.tytosoft.delivery.R
import com.tytosoft.delivery.holders.AdsHolder
import com.tytosoft.delivery.model.ListModel

/**
  * Created by Kos on 06.07.2016.
  */
class AdsItemHolder(itemView:View, itemClick:OnClickListener,btnClick:OnClickListener) extends SimpleHolder(itemView,itemClick){



	val adsHolder:AdsHolder=new AdsHolder(find(R.id.adsLayout),itemClick,btnClick)
	override def bind(position:Int,element:IEntity){
		element match {
			case e : ListModel ⇒
				adsHolder.setList( e)
			case _ ⇒
		}

	}
}
