package com.tytosoft.delivery.model

import com.kos.fastuimodule.common.share.{JSONModel, SON}
import com.kos.fastuimodule.good.common.ID
import com.tytosoft.delivery.data.AppPrefererences
import com.tytosoft.delivery.model.data.Korzina
import com.tytosoft.delivery.model.utils.{ISave, ModeLUtils, SONS}
import org.json.JSONObject
import scala.collection.JavaConverters._

/**
  * Created by Kos on 13.07.2016.
  */
object OrderModel{
	lazy val NULL= new OrderModel{
		override def isNull=true
	}
}

class OrderModel extends BaseModel with ISave{

	override def getDate: String = ModeLUtils.getDateTimeText(date)

	def this(obj: JSONObject) {
		this()
		if (null != obj) baseSON(obj)
	}

	def this(id :ID,korzina: Korzina,preferences: AppPrefererences){
		this()
		this.id=id
		summa=korzina.fullCost()
		delivery_cost=korzina.deliveryCost(preferences)
		product=korzina.list.asScala.toSeq
		date=System.currentTimeMillis()
	}

	def addToKorzina(korzina: Korzina): Unit ={
		product.foreach{case x if x.isActive ⇒
			val order=korzina.find(x.getId)
			if (order.isNull)
				korzina.add(x.copy())
			else{
				order+=x.count
			}
		}
		korzina.fullCost()
	}

	private[this] var product=Seq[OrderProductModel]()
	private[this] var summa=0
	private[this] var status=OrderStatusModel.NULL
	private[this] var delivery_cost=0 //Если заказ производился с оплатой доставки. В случае бесплатной доставки: 0.

	private[this] var address=""

	override def baseSON(obj: JSONObject):Unit = {
		super.baseSON(obj)
		address=SONS.get(obj,"address","title",address)
		summa=SON.get(obj,"summa",summa)
		delivery_cost=SON.get(obj,"delivery_cost",delivery_cost)
		product=JSONModel.get(obj,"product",new OrderProductModel(_))
		status=SONS.get(obj,"status",new OrderStatusModel(_),status)
	}

	override def save(): JSONObject ={
		val obj=super.save()
		SON.put(obj,"summa",summa)
		SON.put(obj,"delivery_cost",delivery_cost)
		SONS.put(obj,"product",product)
		SONS.put(obj,"address","title",address)
		SONS.put(obj,"status",status)

		//Log.i("GSON",obj.toString(2))
		obj
	}



	def setDate(date:Long)={
		this.date=date
	}

	def getDateValue=date

	def getStatus=status
	def deliveryCost=delivery_cost
	def products=product
	def getSumma=summa
	def getAddress=address
	def size=product.size

	def mkString(sep: String) = {
		product.mkString(sep)
	}
}
