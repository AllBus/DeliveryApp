package com.tytosoft.delivery.net.constructors

import com.kos.delivery.common.utils.LogD
import com.kos.fastuimodule.common.net.{AllInfo, IJSONConstructor, TJSONObject}
import com.tytosoft.delivery.model.CodeData

/**
  * Created by Kos on 06.07.2016.
  */
class CodeConstructor(action:String) extends IJSONConstructor{
	override def construct(allInfo: AllInfo, jsonObject: TJSONObject, needSave: Boolean): Unit = {


		if (jsonObject!=null) {
		//	LogD("Code "+jsonObject.getCode)

			allInfo.newElement(new CodeData(action, jsonObject.getCode))
		}else{
			allInfo.newElement(new CodeData(action, 0))

		}
	}
}
