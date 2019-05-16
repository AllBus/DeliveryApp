package com.tytosoft.delivery.model

import com.kos.fastuimodule.common.share.SON
import com.kos.fastuimodule.good.common._
import com.tytosoft.delivery.net.DataStore
import org.json.JSONObject

/**
  * Тип хранения продуктов в заказе<br/>
  * !!!Не сохраняемый как отдельная сущность (так как не имеет уникального id)!!!<br/>
  * Created by Kos on 13.07.2016.
  */
object OrderProductModel{
	lazy val NULL= new OrderProductModel{
		override def isNull=true
	}
}

class OrderProductModel extends BaseModel  with Cloneable{
	def copy(): OrderProductModel = {
		this.clone().asInstanceOf[OrderProductModel]
	}


	def this(obj: JSONObject) {
		this()
		if (null != obj) baseSON(obj)
	}

	def this( id:ID, price:Int, count:Int){
		this()
		this.id=id
		this.count=count
		this.price=price
	}

	var count=0
	private[this] var price=0

	override def baseSON(obj: JSONObject):Unit = {
		super.baseSON(obj)

		count=SON.get(obj,"count",count)
		price=SON.get(obj,"price",price)
	}

	def getCount=count
	def getPrice=price


	def product=DataStore.products(getId)//todo: надо придумать лучше

	override def getName=product.getName
	override def getImage=product.getImage
	def isActive=product.isActive

	def cost=count*price

	def +=(addCount:Int): Unit ={
		count+=addCount
	}

	def -=(addCount:Int): Unit ={
		count-=addCount
		if (count<0)
			count=0
	}

	override def save(): JSONObject = {
		val obj=super.save()

		SON.put(obj,"count",count)
		SON.put(obj,"price",price)
		obj
	}

	override def toString = s"$getName × $count"
}
