package com.tytosoft.badgesapp.model

import com.kos.fastuimodule.common.net.IData
import com.kos.fastuimodule.common.share.SON
import com.kos.fastuimodule.common.ui.Utils
import com.kos.fastuimodule.good.common.model.IEntity
import com.tytosoft.badgesapp.model.utils.{ISave, ModeLUtils}
import org.json.JSONObject

object BaseModel {
	val _modification_date: String = "modification_date"
	val _date: String = "date"
	val _sort: String = "sort"
	val _id: String = "id"

	lazy val NULL = new BaseModel{
		override def isNull=true }
}

class BaseModel extends Comparable[BaseModel] with ISave with IEntity with IData {

	def this(obj: JSONObject) {
		this()
		if (null != obj) baseSON(obj)
	}

	protected var date: Long = 0
	private var sort: Int = 0
	protected var id: Long = 0
	protected var modification_date: Long = 0

	def getDate: String = ModeLUtils.getDateText(date)

	def getImage: String = ""

	def getInfo: String = ""

	def getGroup: Int =  -1

	def getKind: String = ""

	def getStartTime = date


	def getEndTime = date


	def getDuration: Long = {
		 getEndTime - getStartTime
	}

	def getApi= ""

	def getSort = sort

	def getId =	 id

	def getName: String = ""

	def getPlace: String = ""

	def getTime: String = Utils.getTimeText(date)

	def baseSON(obj: JSONObject) {
		date = SON.get(obj, BaseModel._date, date / 1000) * 1000
		sort = SON.get(obj, BaseModel._sort, sort)
		id = SON.get(obj, BaseModel._id, id)
		modification_date = SON.get(obj, BaseModel._modification_date, modification_date)
	}

	def compareTo(another: BaseModel): Int = {
		if ((sort < another.sort)) -1 else (if ((sort == another.sort)) 0 else 1)
	}

	def isNull: Boolean = false

	def handle: Unit = {

	}

	override def getModificationDate = modification_date

	override def save(): JSONObject = {
		val obj=new JSONObject()
		SON.put(obj, BaseModel._date, date / 1000)
		SON.put(obj, BaseModel._sort, sort)
		SON.put(obj, BaseModel._id, id)
		SON.put(obj, BaseModel._modification_date, modification_date)
		obj
	}
}