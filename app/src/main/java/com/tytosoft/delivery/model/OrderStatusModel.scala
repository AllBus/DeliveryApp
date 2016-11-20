package com.tytosoft.delivery.model

import com.kos.fastuimodule.common.share.SON
import com.tytosoft.delivery.model.utils.ISave
import org.json.JSONObject

/**
  * Created by Kos on 13.07.2016.
  */
object OrderStatusModel{
	lazy val NULL= new OrderStatusModel{

		override def isNull=true
	}
}

class OrderStatusModel extends BaseModel with ISave{
	def this(obj: JSONObject) {
		this()
		if (null != obj) baseSON(obj)
	}

	private[this] var status=0
	private[this] var title=""
	private[this] var color=""
	override def baseSON(obj: JSONObject) {
		super.baseSON(obj)
		status=SON.get(obj,"status",status)
		title=SON.get(obj,"title",title)
		color=SON.get(obj,"color",color)
	}

	def getStatus=status
	def getColor=color

	override def getName="Ожидайте звонка"//todo: Здесь должно быть title


	override def save(): JSONObject = {
		val obj=super.save()
		SON.put(obj,"status",status)
		SON.put(obj,"title",title)
		SON.put(obj,"color",color)
		obj
	}
}
