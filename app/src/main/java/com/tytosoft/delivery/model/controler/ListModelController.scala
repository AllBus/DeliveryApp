package com.tytosoft.delivery.model.controler

import com.kos.fastuimodule.good.common.ID
import com.tytosoft.badgesapp.common.adapters.universal.IListModel
import com.tytosoft.delivery.model.ListModel

/**
  * Created by Kos on 19.07.2016.
  */
class ListModelController[T](finder: ID ⇒ T) extends IListModel[ID,T,ListModel]{

	override def apply(index: Int) = finder(list(index))
	override def indexOf(id: ID): Int = list.indexOf(id)

	override def find(id: ID) = finder(id)
	override def size: Int = list.size

	override protected var list: ListModel = ListModel.NULL
}
