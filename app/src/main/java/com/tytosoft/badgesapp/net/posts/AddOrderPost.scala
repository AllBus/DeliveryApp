package com.tytosoft.badgesapp.net.posts

import com.kos.fastuimodule.common.net._
import com.kos.fastuimodule.good.common.core.IContexter
import com.tytosoft.badgesapp.common.utils.StringUtils
import com.tytosoft.badgesapp.model.data.UserProfile
import com.tytosoft.badgesapp.net.Program
import com.tytosoft.delivery.model.data.Korzina
import okhttp3.RequestBody

import scalaxy.loops._
/**
  * Created by Kos on 20.07.2016.
  */
class AddOrderPost(info: IContexter, constructor: IJSONConstructor, profile: UserProfile, korzina: Korzina) extends SimplePoster{


	override def generatePost(): RequestBody = {
		args.encodePost()
	}


	override def generateGet(): String = {
		args.encodeGET()
	}

	def args ={
		val builder= info.defaultParamNameValuePair("","")

		builder.add(Program.POST_DATA,Program.POST_CLIENT,"phone", profile.phone)
		builder.add(Program.POST_DATA,Program.POST_CLIENT,"name", profile.name)
		builder.add(Program.POST_DATA,Program.POST_CLIENT,"address","title", profile.address)

		for (i← (0 until korzina.size).optimized) {
			val x=korzina(i)
			builder.add(Program.POST_DATA, Program.POST_ORDER, "product_id",i.toString, x.getId.toString)
			builder.add(Program.POST_DATA, Program.POST_ORDER, "quantity"  ,i.toString,  x.count.toString)
		}
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
