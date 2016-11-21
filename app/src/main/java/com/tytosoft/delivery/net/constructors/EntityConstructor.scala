package com.tytosoft.delivery.net.constructors

import com.kos.fastuimodule.common.net.{AllInfo, IJSONConstructor, TJSONObject}
import com.kos.fastuimodule.good.common.ID
import com.kos.fastuimodule.good.common.core.IContexter
import com.kos.fastuimodule.good.common.model.IEntity
import com.kos.fastuimodule.good.common.system.collection.ListLoadingData
import com.kos.fastuimodule.good.common.system.{EConstructType, GoodPostArguments}
import com.tytosoft.delivery.common.utils.LogD
import com.tytosoft.delivery.net.EventSaver
import org.json.{JSONArray, JSONObject}

import scala.collection.mutable.ArrayBuffer


/**
  * Created by picom on 21.09.2015.
  */
class EntityConstructor[T <: IEntity](val context:IContexter,
									  val arguments: GoodPostArguments,
									  val listData: ListLoadingData[T]
									  ) extends IJSONConstructor {
	override def construct(allInfo: AllInfo, tjsonObject: TJSONObject,needSave: Boolean): Unit = {

		def loadJArray[U >: T](saver: Seq[(ID, JSONObject)] ⇒ Unit) =
			loadArray(allInfo, tjsonObject.getJsonArray, listData.constructor, saver,needSave)

		if (tjsonObject == null)
			return


//		LogD("EntityConstructor "+arguments.ids+" "+arguments.infoType)
//		LogD("Type "+arguments.constructType)
//		LogD("OBJ "+tjsonObject.getCode)
//		println("Выведи текст")
//		val obj=tjsonObject.getJsonObject
//		if (obj!=null){
//			LogD(obj.toString(2))
//		}
//		val arr=tjsonObject.getJsonArray
//		if (arr!=null){
//			LogD("Array ")
//			for (i ← 0 until arr.length) {
//				LogD(arr.optJSONObject(i))
//			}
//		}


		loadJArray(
			{items ⇒
				if (arguments.constructType==EConstructType.action)
					EventSaver.deleteAll(context,context.tableName(arguments.infoType))
				EventSaver.saveSonMulti(context.tableName(arguments.infoType))(context, items)
			}
		)

		//todo: нужно обработать alert и code
//		tjsonObject.getCode match {
//			case _ ⇒
//		}

	}

	def loadArray(allInfo: AllInfo,
				  arr: JSONArray,
				  constructor: JSONObject ⇒ T,
				  saver: Seq[(ID, JSONObject)] ⇒ Unit,needSave: Boolean): Unit = {

		if (arr != null) {
			val builder = new ArrayBuffer[(ID, JSONObject)]()
			val items = new ArrayBuffer[T]()
			var b = arguments.constructType==EConstructType.action


			for (i ← 0 until arr.length) {
				val obj = arr.optJSONObject(i)
				if (obj != null) {
					val event = constructor(obj)
					items += event
					builder += event.getId → obj
					b = b | arguments.ids.contains(event.getId)
				}
			}

			if (items.nonEmpty) {
				if (needSave) {
					saver(builder)
				}
				allInfo.newElement(listData.push(items, b))

			}
		}
	}
}
