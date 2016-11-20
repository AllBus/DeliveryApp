package com.tytosoft.badgesapp.net.posts

import com.kos.fastuimodule.common.net._
import com.kos.fastuimodule.good.common.core.IContexter
import com.tytosoft.delivery.model.data.UserProfile
import com.tytosoft.delivery.model.data.Korzina
import com.tytosoft.delivery.net.Program
import okhttp3.RequestBody

import scalaxy.loops._

/**
  * Created by Kos on 20.07.2016.
  */
class OrderPost(info: IContexter, constructor: IJSONConstructor) extends SimplePoster{


	override def generatePost(): RequestBody = {
		args.encodePost()
	}


	override def generateGet(): String = {
		args.encodeGET()
	}

	def args ={
		val builder= info.defaultParamNameValuePair("","")

		builder
	}

	override def predZapros(): Boolean = {
		true
	}
	override def postZapros(): Unit = {

	}
	override def cancelZapros(): Unit = {
	}

	override def construct(tjsonObject: TJSONObject, allInfo: AllInfo): Unit = {
		constructor.construct(allInfo,tjsonObject,true)
	}

	override def getConstructor: IJSONConstructor = constructor

	override def getMethod: EMethod = EMethod.get


}
