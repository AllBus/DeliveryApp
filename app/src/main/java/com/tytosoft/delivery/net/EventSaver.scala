package com.tytosoft.delivery.net

import android.database.Cursor
import android.provider.BaseColumns._
import com.kos.fastuimodule.good.common.DataColumns._
import com.kos.fastuimodule.good.common.core.IContexter
import com.kos.fastuimodule.good.common.{CommonEventSaver, ID}
import org.json.JSONObject

import scala.collection.mutable.ArrayBuffer
/**
  * Created by Kos on 10.03.2016.
  */
object EventSaver {


	//	def saveListIds(context: IContexter, infoType: String, builder: ArrayBuffer[(Int, JSONObject)]) = {
	//		val obj =new JSONObject()
	//		)
	//
	//		CommonEventSaver.saveSon(context,DataBase.rowListId(infoType) ,DataBase.TABLE_LIST_NAME,obj)
	//	}


	def saveSonMulti(tableName: String)(context: IContexter, elements: Seq[(ID, JSONObject)]): Unit = {
	//	LogD(s"Save db $tableName $elements")
		CommonEventSaver.saveSon(context, tableName, elements)
	}

	def deleteAll(context: IContexter, tableName: String): Unit = {
		if (context == null) return
		try {
			val db = context.getDB
			try {
				db.delete(tableName, null, null)

			} finally {
				db.close()
			}
		}
		catch {
			case ignored: Exception ⇒
		}
	}

	def getAll(context: IContexter, tableName: String): (JSONObject, Seq[(Long, ID)]) = {

		val sonBuf = new ArrayBuffer[String]()
		var buf = new ArrayBuffer[(Long, ID)]()

		if (context == null) return CommonEventSaver.toJSONArray(sonBuf) → buf

		try {
			val db = context.getDB
			try {

				val cursor: Cursor = db.query(
					tableName,
					projection,
					null,
					null,
					null,
					null,
					null)

				try {
					if (cursor.moveToFirst) {
						do {
							sonBuf += cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VALUE))
							buf += cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_MODTIME)) →
								cursor.getInt(cursor.getColumnIndexOrThrow(_ID))

						} while (cursor.moveToNext())
					}
				}
				finally {
					cursor.close()
				}
			} finally {
				db.close()
			}
		}
		catch {
			case ignored: Exception ⇒
		}
		CommonEventSaver.toJSONArray(sonBuf) → buf
	}

}
