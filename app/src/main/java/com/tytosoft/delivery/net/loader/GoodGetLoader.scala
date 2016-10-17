package com.tytosoft.delivery.net.loader

import com.kos.delivery.net.EventSaver
import com.kos.delivery.net.posts.SectionPathPost
import com.kos.fastuimodule.common.net.{IJSONConstructor, JSONRequest, TJSONObject}
import com.kos.fastuimodule.good.common.core.IContexter
import com.kos.fastuimodule.good.common.system.post.GoodPathPost
import com.kos.fastuimodule.good.common.system.{EConstructType, GoodPostArguments}
import com.kos.fastuimodule.good.common.{ID, IDSet}
import com.tytosoft.badgesapp.net.posts.{ListPathPost, GetPathPost}
import org.json.JSONObject

/**
  * Created by Kos on 17.07.2016.
  */
object GoodGetLoader {

	/**
	  *
	  * @param info           обработчик полченных данных
	  * @param url            адрес для запроса
	  * @param constructor    конструктор
	  * @param arguments      аргументы для запроса
	  * @param defearDownload true если нужно загрузить с сервера  в любом случае
	  * @return были ли данные получены из кэша
	  */
	def background(info: IContexter,
				   url: String,
				   constructor: IJSONConstructor,
				   arguments: GoodPostArguments,
				   onlyServer: Boolean = false,
				   defearDownload: Boolean = false,
				   onlyBase: Boolean = false
				  ): Boolean = {

		import EventSaver._
		import com.kos.fastuimodule.good.common.CommonEventSaver._
		//		Thread.sleep(5000)//todo: test sleep

		if (onlyServer) {
			if (arguments.ids.nonEmpty)
				return asynh(constructor, info, url, arguments, arguments.ids)
		}
		else {
			val obj: (JSONObject, Seq[(Long, ID)]) =
				if (arguments.constructType==EConstructType.action)
					getAll(info,info.tableName(arguments.infoType))
				else
					loadSons(info.tableName(arguments.infoType))(info, arguments.ids)

			constructor.construct(info.info, new TJSONObject(obj._1), false)

			if (!onlyBase) {
				val needUpdate =
					if (defearDownload) arguments.ids
					else arguments.ids -- obj._2.filterNot(x ⇒ info.isOld(x._1)).map(_._2)
				if (needUpdate.nonEmpty) {
					return asynh(constructor, info, url, arguments, needUpdate) // needUpdate.map(x ⇒ x._2))
				}
			}
		}
		false
	}

	private def asynh(constructor: IJSONConstructor, info: IContexter,
					  url: String, arguments: GoodPostArguments, ids: IDSet) = {

		arguments.constructType match {
			case EConstructType.idType ⇒ JSONRequest.asynh(info.info, url, new GetPathPost(info, constructor, arguments, ids))
			case EConstructType.action ⇒ JSONRequest.asynh(info.info, url, new ListPathPost(info, constructor, arguments))
			case EConstructType.arrayIdType ⇒ JSONRequest.asynh(info.info, url, new SectionPathPost(info, constructor, arguments, ids))
			case _ ⇒ JSONRequest.asynh(info.info, url, new GoodPathPost(info, constructor, arguments, ids))
		}
		//
		//		JSONRequest.asynh(info.info, url,
		//			if (arguments.constructType == EConstructType.action)
		//				new GoodListPathPost(info, constructor, arguments)
		//			else
		//				new GoodPathPost(info, constructor, arguments, ids)
		//		)
	}

}

