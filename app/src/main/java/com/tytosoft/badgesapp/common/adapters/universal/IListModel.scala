package com.tytosoft.badgesapp.common.adapters.universal

/**
  * Трейт для списка который используется в адаптере.
  * list должен быть неизменным
  * Created by Kos on 19.07.2016.
  */
trait IListModel[@specialized ListID,T,W<:AnyRef] {

	protected var list:W

	/**
	  * элемент по указанному индексу
	  * @param index
	  * @return элемент по указанному индексу
	  */
	def apply(index:Int):T

	/**
	  * элемент по указанному id
	  * @param id
	  * @return элемент по указанному id
	  */
	def find(id:ListID):T

	/**
	  * номер элемента в списке
	  * @param id
	  * @return номер элемента в списке
	  */
	def indexOf(id: ListID):Int

	/**
	  * размер списка
	  * @return  размер списка
	  */
	def size:Int

	/**
	  * заменить список новым списком
	  * @param newList новый список
	  * @return true если список заменён, false иначе
	  */
	def change(newList: W):Boolean ={
		if (list ne newList){
			list=newList
			true
		}
		else
			false
	}

	def getList=list
}
