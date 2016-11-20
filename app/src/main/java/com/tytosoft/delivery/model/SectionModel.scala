package com.tytosoft.delivery.model

import com.kos.fastuimodule.common.share.SON
import org.json.JSONObject

/**
  * Created by Kos on 13.07.2016.
  */
object SectionModel{
	lazy val NULL= new SectionModel{
		override def isNull=true
	}
}

class SectionModel extends ProductModel{

	def this(obj: JSONObject) {
		this()
		if (null != obj) baseSON(obj)
	}


	private[this] var code="" //[String]* - Символьный код раздела
	private[this] var path_type=""  //[String: section | product]
	//section - текущий раздел является родителем других разделов
		//product - в разделе уже элементы
	private[this] var parent_id=0 //[Integer | null]null - текущий раздел является корневым

	override def baseSON(obj: JSONObject) {
		super.baseSON(obj)

		code=SON.get(obj,"code",code)
		path_type=SON.get(obj,"path_type",path_type)
		parent_id=SON.get(obj,"parent_id",parent_id)
	}


	def parentId=parent_id
	def getCode=code
	override def getKind=path_type
}
