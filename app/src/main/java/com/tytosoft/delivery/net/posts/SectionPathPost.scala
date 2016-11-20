package com.tytosoft.delivery.net.posts

import com.kos.fastuimodule.common.net._
import com.kos.fastuimodule.good.common.IDSet
import com.kos.fastuimodule.good.common.core.IContexter
import com.kos.fastuimodule.good.common.system.GoodPostArguments
import com.tytosoft.badgesapp.net.posts.GetPathPost
import com.tytosoft.delivery.net.Program

/**
  * Created by Kos on 17.07.2016.
  */
class SectionPathPost(context: IContexter, constructor: IJSONConstructor, arguments: GoodPostArguments,sections:IDSet)
	extends GetPathPost(context,constructor,arguments,sections){


	override def getArgs: TPostListNameValuePair ={
		val pair: TPostListNameValuePair = context.defaultParamNameValuePair(arguments.infoType,arguments.action)
		sections.foreach{ x ⇒ pair.add(Program.POST_DATA, "section_id","", x.toString)	}
		pair
	}

	override def predZapros: Boolean =  true
}
