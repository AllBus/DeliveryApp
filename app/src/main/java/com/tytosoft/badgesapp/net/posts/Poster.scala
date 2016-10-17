package com.tytosoft.badgesapp.net.posts

import com.kos.fastuimodule.common.net._
import com.kos.fastuimodule.good.common._
import com.kos.fastuimodule.good.common.core.IContexter

import com.tytosoft.badgesapp.common.utils.StringUtils
import com.tytosoft.badgesapp.net.Program
import okhttp3.RequestBody
;
/**
  * Created by Kos on 06.07.2016.
  */
class Poster(val allInfo:IContexter,val constructor:IJSONConstructor, ids: IDSet) extends SimplePoster{

	override def generatePost(): RequestBody = {
		args.encodePost()
	}

	override def generateGet(): String = {
		args.encodeGET()
	}

	def args ={
		val builder= allInfo.defaultParamNameValuePair("","")
		ids.foreach{ x ⇒ builder.add(Program.POST_DATA, "id","", x.toString)	}
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
