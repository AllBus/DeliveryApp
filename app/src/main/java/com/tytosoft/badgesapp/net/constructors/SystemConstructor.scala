package com.tytosoft.badgesapp.net.constructors

import com.kos.delivery.common.utils.LogD
import com.kos.fastuimodule.common.net.{AllInfo, IJSONConstructor, TJSONObject}
import com.tytosoft.badgesapp.model.SystemModel

/**
  * Created by Kos on 06.07.2016.
  */
class SystemConstructor extends IJSONConstructor{
	override def construct(allInfo: AllInfo, jsonObject: TJSONObject, needSave: Boolean): Unit = {

		LogD("request system")
		if (jsonObject!=null) {
			LogD(""+jsonObject.getCode)


			val arr=jsonObject.getJsonArray
			if (arr!=null && arr.length()>0){
				val pref=new SystemModel(arr.optJSONObject(0))

				allInfo.newElement(pref)
			}

		}
	}
}
