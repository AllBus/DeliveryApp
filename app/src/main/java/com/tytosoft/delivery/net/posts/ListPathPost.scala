package com.tytosoft.badgesapp.net.posts

import com.kos.fastuimodule.common.net._
import com.kos.fastuimodule.good.common.core.IContexter
import com.kos.fastuimodule.good.common.system.GoodPostArguments

/**
  * Created by Kos on 17.07.2016.
  */
class ListPathPost(context: IContexter, constructor: IJSONConstructor, arguments: GoodPostArguments)
	extends GetPathPost(context,constructor,arguments,Set.empty){


	override def getArgs: TPostListNameValuePair ={
		val pair: TPostListNameValuePair = context.defaultParamNameValuePair(arguments.infoType,arguments.action)
		pair
	}

	override def predZapros: Boolean =  true
}
