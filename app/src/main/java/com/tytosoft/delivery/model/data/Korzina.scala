package com.tytosoft.delivery.model.data

import java.util

import com.kos.fastuimodule.core.JTypes._
import com.kos.fastuimodule.good.common.ID
import com.kos.fastuimodule.good.common.bus.BusProvider
import com.tytosoft.delivery.adapters.universal.IChangedListModel
import com.tytosoft.delivery.data.AppPrefererences
import com.tytosoft.delivery.model.OrderProductModel
import com.tytosoft.delivery.net.BusConstants

import scalaxy.loops._

/**
  * Created by Kos on 09.07.2016.
  */
class Korzina() extends IChangedListModel[ID, OrderProductModel, util.ArrayList[OrderProductModel]] {

	var summa = 0

	var list = new util.ArrayList[OrderProductModel]()

	def add(productId: ID, productPrice: Int, productCount: Int): Unit = {

		for (i ← (0 until list.size()).optimized) {
			val p = list.get(i)
			if (p.getId == productId) {
				summa -= p.cost
				p.count += 1
				summa += p.cost
				return
			}
		}

		val item = new OrderProductModel(productId, productPrice, productCount)
		list.add(item)
		summa += item.cost

	}

	def clear(): Unit = {
		summa = 0
		list.clear()

		BusProvider.post(BusConstants.clearKorzina)
	}

	def getCost = {
		summa.toString
	}

	def fullCost() = {
		var sum = 0
		list.foreach { x ⇒
			sum += x.cost
		}
		summa=sum
		sum
	}

	def getCost(preferences: AppPrefererences) = {
		var sum = fullCost()

		if (sum < preferences.minFreeValue) {
			sum += preferences.dostavkaPriceValue
		}
		sum.toString + preferences.currency
	}

	def deliveryCost(preferences: AppPrefererences): Int = {
		if (isFree(preferences))
			0
		else
			preferences.dostavkaPriceValue

	}

	def isComplete(preferences: AppPrefererences): Boolean = {
		val sum = fullCost()
		isActiveList && sum >= preferences.minCostValue
	}

	def isActiveList: Boolean ={
		if (list.size()==0)
			return false

		for (i ← (0 until list.size()).optimized) {
			if (!list.get(i).isActive)
				return false
		}

		true
	}


	def isFree(preferences: AppPrefererences): Boolean = {
		val sum = fullCost()
		sum >= preferences.minFreeValue
	}

	def apply(index: Int): OrderProductModel = {
		if (index >= 0 && index < list.size()) {
			list.get(index)
		} else
			OrderProductModel.NULL
	}

	/**
	  * элемент по указанному id
	  *
	  * @param id
	  * @return элемент по указанному id
	  */
	override def find(id: ID): OrderProductModel = {
		for (i ← (0 until list.size()).optimized) {
			if (list.get(i).getId == id)
				return list.get(i)
		}
		OrderProductModel.NULL
	}

	/**
	  * номер элемента в списке
	  *
	  * @param id
	  * @return номер элемента в списке
	  */
	override def indexOf(id: ID): Int = {
		for (i ← (0 until list.size()).optimized) {
			if (list.get(i).getId == id)
				return i
		}
		-1
	}

	/**
	  * размер списка
	  *
	  * @return размер списка
	  */
	override def size: Int = list.size()

	override def add(element: OrderProductModel): Boolean = {
		list.add(element)
		summa += element.cost
		true
	}


	override def add(index: Int, element: OrderProductModel): Boolean = {
		if (index >= 0 && index < list.size()) {
			list.add(index, element)
			summa += element.cost
			true
		} else
			false

	}

	override def remove(index: Int): Boolean = {
		if (index >= 0 && index < list.size()) {
			list.remove(index)
			true
		} else
			false
	}


}
