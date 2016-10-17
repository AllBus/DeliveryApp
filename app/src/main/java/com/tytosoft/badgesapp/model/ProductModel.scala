package com.tytosoft.badgesapp.model

import com.kos.delivery.common.utils.LogD
import com.kos.fastuimodule.common.share.SON
import com.kos.fastuimodule.good.common.ID
import com.tytosoft.badgesapp.model.utils.SONS
import org.json.JSONObject

/**
  * Created by Kos on 13.07.2016.
  */
object ProductModel{
	lazy val NULL= new ProductModel{
		override def isNull=true
	}
}

class ProductModel extends BaseModel {



	def this(obj: JSONObject) {
		this()
		if (null != obj) baseSON(obj)
	}

	private[this] var title = ""
	private[this] var section:ID = 0L
	private[this] var sale = ""
	private[this] var preview_info = ""
	private[this] var detail_info = ""

	private[this] var imagesSmall = ""
	private[this] var imagesBig = ""

	private[this] var pricesOld = 0
	private[this] var pricesCurrent = 0
	private[this] var active = true
	private[this] var weight = ""
	private[this] var top = true


	override def baseSON(obj: JSONObject) {
		super.baseSON(obj)

	//	LogD(obj.toString(2))
		title = SONS.get(obj, "title", title)
		section = SON.get(obj, "section", section)
		preview_info = SONS.get(obj, "preview_info", preview_info)

		detail_info = SONS.get(obj, "detail_info", detail_info)

		imagesSmall = SONS.get(obj, "images", "small", imagesSmall)
		imagesBig = SONS.get(obj, "images", "big", imagesBig)

		pricesOld = SONS.get(obj, "prices", "old", pricesOld)
		pricesCurrent = SONS.get(obj, "prices", "current", pricesCurrent)

		active = SON.get(obj, "active", active)
		weight = SONS.get(obj, "weight", weight)
		top = SON.get(obj, "top", top)
		sale = SONS.get(obj, "sale", sale)
	}

	override def getName = title
	override def getImage = imagesSmall
	override def getInfo = preview_info


	def getImageSmall = imagesSmall
	def getImageBig = imagesBig
	def getPricesOld = pricesOld
	def getPricesCurrent = pricesCurrent

	def getDetail = detail_info
	def getSale = sale
	def isActive=active & !isNull
	def getWeight=weight
	def isTop=top
	def getSection=section

	def hasOldPrice: Boolean = {
		pricesOld>pricesCurrent
	}
	def hasDiscount: Boolean = sale.nonEmpty


}
