package com.tytosoft.delivery.model

import com.kos.fastuimodule.common.net.IData
import com.tytosoft.delivery.net.DataStore

/**
  * Created by Kos on 20.07.2016.
  */
class CodeData(val action:String,val code:Int) extends IData{
	override def handle(): Unit = {
		DataStore.updateCode(this)
	}
}
