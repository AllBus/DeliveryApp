package com.tytosoft.badgesapp.net.constructors

import com.kos.delivery.common.utils.LogD
import com.kos.fastuimodule.common.net.{AllInfo, IJSONConstructor, TJSONObject}

/**
  * Created by Kos on 06.07.2016.
  */
class Constructor extends IJSONConstructor{
	override def construct(allInfo: AllInfo, jsonObject: TJSONObject, needSave: Boolean): Unit = {

		LogD("request ")
		if (jsonObject!=null) {
			LogD(""+jsonObject.getCode)
			if (jsonObject.getJsonObject!=null)
				LogD(jsonObject.getJsonObject.toString(2))
		}
	}
}
