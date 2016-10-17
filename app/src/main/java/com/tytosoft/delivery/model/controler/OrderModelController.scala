package com.tytosoft.delivery.model.controler

import com.kos.delivery.net.DataStore
import com.kos.fastuimodule.good.common._
import com.tytosoft.badgesapp.common.adapters.universal.IListModel
import com.tytosoft.badgesapp.model.{ProductModel, OrderModel}

/**
  * Created by Kos on 21.07.2016.
  */
class OrderModelController(finder: ID ⇒ OrderModel) extends IListModel[ID,OrderModel,Seq[ID]]{


	override def apply(index: Int) = finder(list(index))
	override def indexOf(id: ID): Int = list.indexOf(id)

	override def find(id: ID) = finder(id)
	override def size: Int = list.size

	override protected var list: Seq[ID] = Seq.empty
	protected var noOrderedList: Seq[ID] = Seq.empty
	private[this] var updates:Seq[ID]=Seq.empty

	override def change(newList: Seq[ID]):Boolean ={
		if (noOrderedList ne newList){
			noOrderedList=newList

			val pred=noOrderedList.view.map(find).sortBy(- _.getDateValue)

			list = pred.map(_.getId).force

			updates=pred.flatMap(_.products.map(_.getId)).force
			true
		}
		else
			false
	}

	def hasUpdates(ids: Seq[ID]):Boolean={
		updates.intersect(ids).nonEmpty
	}
//	override def getItemsForUpdate(ids: Seq[ID]): Unit ={
//
//		updates.foreach{ u ⇒
//			val f =finder(u)
//			if (!f.isNull){
//				f.products.
//			}
//		}
//	}
}
