package com.tytosoft.delivery.model

import com.kos.fastuimodule.common.share.SON
import com.kos.fastuimodule.good.common.ID
import com.tytosoft.badgesapp.model.ProductModel
import org.json.JSONObject

/**
  * Created by Kos on 13.07.2016.
  */
object AdsModel{
	lazy val NULL= new AdsModel{
		override def isNull=true
	}
}

class AdsModel extends ProductModel{
	def this(obj: JSONObject) {
		this()
		if (null != obj) baseSON(obj)
	}

	private[this] var product:ID=0L

	override def baseSON(obj: JSONObject) {
		super.baseSON(obj)

		product =SON.get(obj,"product",product )

	}

	def getProductId=product

}
