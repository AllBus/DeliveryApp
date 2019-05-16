package com.tytosoft.delivery.model

import com.kos.fastuimodule.common.share.SON
import com.kos.fastuimodule.good.common.ID
import com.tytosoft.delivery.model.utils.SONS
import com.tytosoft.delivery.net.Program
import org.json.JSONObject

/**
  * Created by Kos on 13.07.2016.
  */
object ListModel {
	lazy val NULL = new ListModel {
		override def isNull = true
	}
	val NONE_ID = -2L
	val NULL_ID = 0L
}

class ListModel extends BaseModel {

	import ListModel._

	def this(obj: JSONObject) {
		this()
		if (null != obj) baseSON(obj)
	}

	private[this] var kind = ""
	private[this] var parent_id: ID = 0
	private[this] var list = Seq[ID]()

	override def baseSON(obj: JSONObject):Unit = {
		super.baseSON(obj)
		kind = SON.get(obj, "type", kind)
		parent_id = SON.get(obj, "parent_id", parent_id)
		list = SONS.getIDList(obj, "list")
	}

	override def getKind = kind

	def getList = list

	def parentId = parent_id

	def size = list.size

	def apply(index: Int): ID = {
		if (index >= 0 && index < list.size)
			list(index)
		else
			NONE_ID
	}

	def indexOf(id:ID): Int ={

		list.indexOf(id)
//		def loop(index:Int,seq: Seq[ID]): Int ={
//			if (seq.isEmpty)
//				-1
//			else
//				if (seq.head)
//		}
//		loop(0,list)
	}

	def isSection = getKind == Program.LIST_SECTION

}
