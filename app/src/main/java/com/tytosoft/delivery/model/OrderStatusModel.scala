package com.tytosoft.delivery.model

import android.graphics.Color
import com.kos.fastuimodule.common.share.SON
import com.tytosoft.delivery.common.utils.LogD
import com.tytosoft.delivery.model.utils.ISave
import org.json.JSONObject

import scala.util.Try

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
	private[this] var color=Color.BLACK

	override def baseSON(obj: JSONObject):Unit = {
		super.baseSON(obj)
		status=SON.get(obj,"status",status)
		title=SON.get(obj,"title",title)
		var textColor=""
		textColor=SON.get(obj,"color",textColor)

		color= textColor match {
			case "positive" ⇒ 0xFF7BAE37
			case "negative" ⇒ 0xffca170a
			case s ⇒
				Try(Color.parseColor(s)).getOrElse(Color.BLACK)
		}
	//	LogD(s"status obj ${obj.toString(2)}")
	//	LogD(s"Status $status:$title:$textColor:$color")
	}

	def getStatus=status
	def getColor:Int=color

	override def getName=title


	override def save(): JSONObject = {
		val obj=super.save()
		SON.put(obj,"status",status)
		SON.put(obj,"title",title)

		SON.put(obj,"color",color)
		obj
	}
}
