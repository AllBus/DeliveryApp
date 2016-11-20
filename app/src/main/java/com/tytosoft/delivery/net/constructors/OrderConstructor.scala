package com.tytosoft.delivery.net.constructors

import com.kos.fastuimodule.common.net.{AllInfo, IJSONConstructor, TJSONObject}
import com.tytosoft.delivery.common.utils.LogD
import com.tytosoft.delivery.model.CodeData

/**
  * Created by Kos on 06.07.2016.
  */
class OrderConstructor(action:String) extends IJSONConstructor{
	override def construct(allInfo: AllInfo, jsonObject: TJSONObject, needSave: Boolean): Unit = {


		if (jsonObject!=null) {
			LogD("Code "+jsonObject.getCode)
			LogD("json "+jsonObject.getJsonObject.toString(2))

			allInfo.newElement(new CodeData(action, jsonObject.getCode))
		}else{
			allInfo.newElement(new CodeData(action, 0))

		}
	}
}
