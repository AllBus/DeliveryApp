package com.tytosoft.delivery.adapters.universal

/**
  * Трейт для списка который используется в адаптере.
  * list должен быть неизменным
  * Created by Kos on 19.07.2016.
  */
trait IChangedListModel[@specialized ListID,T,W<:AnyRef] extends IListModel[ListID,T,W]{

	def remove(index:Int):Boolean
	def add(index:Int,element: T):Boolean
	def add(element: T):Boolean
}
