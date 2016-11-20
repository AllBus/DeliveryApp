package com.tytosoft.badgesapp.net.posts

import com.kos.fastuimodule.common.net._
import com.kos.fastuimodule.good.common.IDSet
import com.kos.fastuimodule.good.common.core.IContexter
import com.kos.fastuimodule.good.common.system.GoodPostArguments
import com.tytosoft.delivery.net.Program
import okhttp3.RequestBody

/**
  * Created by Kos on 17.07.2016.
  */
class GetPathPost(context: IContexter, constructor: IJSONConstructor, arguments: GoodPostArguments, ids: IDSet) extends SimplePoster{
	override def cancelZapros(): Unit = {

	}

	override def predZapros(): Boolean =  ids.nonEmpty

	override def generatePost(): RequestBody = {
		getArgs.encodePost()
	}

	override def generateGet(): String = {
		getArgs.encodeGET()
	}

	override def postZapros(): Unit = {
		arguments.endLoad()
	}

	override def construct(json: TJSONObject, allInfo: AllInfo): Unit = {
		if (json != null) {
			if (this.constructor != null) {
				this.constructor.construct(context, json,true)
			}
		}
	}

	def getArgs: TPostListNameValuePair ={
		val pair: TPostListNameValuePair = context.defaultParamNameValuePair(arguments.infoType,arguments.action)
		ids.foreach{ x ⇒ pair.add(Program.POST_DATA, "id","", x.toString)	}
		pair
	}

	override def getConstructor: IJSONConstructor = constructor

	override def getMethod: EMethod = EMethod.get
}
