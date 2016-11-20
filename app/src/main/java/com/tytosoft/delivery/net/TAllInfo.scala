package com.tytosoft.delivery.net

import java.util.UUID

import android.content.{Context, SharedPreferences}
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.kos.delivery.common.utils.LogD
import com.kos.fastuimodule.common.net.{ErrorElement, TGetPostListNameValuePair, TPostListNameValuePair}
import com.kos.fastuimodule.common.ui.Utils
import com.kos.fastuimodule.good.common.core.IContexter
import com.tytosoft.badgesapp.model.utils.SONS
import com.tytosoft.badgesapp.net.Program

import scala.util.Try

/**
  * Created by Kos on 10.03.2016.
  */
class TAllInfo extends IContexter {

	private[this] var api: String = ""

	override def defaultParamNameValuePair(infoType: String, action: String): TPostListNameValuePair = {
		val builder: TGetPostListNameValuePair = new TGetPostListNameValuePair()
		val crypto_date=System.currentTimeMillis().toString
		val key=Utils.md5("1OCYZhLTcS9k7vG8q0T96S4LfzSdA0lAYX44AF885479wM22S7kFA3z7s5x7n5IBIJT8c27nxuy30z85QKEB295577795C06pX2g8JV66T5J5luO77we5ohHCI55l50g8202QIm4db3d99an66D5sB5Yhjzbos4l89a90111wrbky40Vo3wHP8BHgJ3dZI5b729RQ0oIpfR5994500egW3S50OVT2RJ1ULi1yL8l3aCF617inowR3PwFONI4kaOrNkhDKZMTNYTVJmwxarlagsYbNqbLeSLZypKZoFlAZxZNfDDgVTmIiRAuIDxgxqrfHTDVJRTooaYyePyFuQuFUDHMDUOLbYrMZdgcCPFcaQtVYObxZXBgMImJvsmXqyKVrisabQzBOvANFpCdvvZaAdYOAuPbYRhvKJaoscwNYmmLoCtnjzthzZURFZLNUDaicylAWPQUuyraFiTgzOxIzkgqBsFFtuGKtDFmCDIwVZGDaGHGdhcnnlyAKUcLJEjKICAgciSEgOWsyYPaqlZEwZnezFyTfdGOmQedSVTNrXywEeJRImxDxRirUNMkZjeFJeYqSTAQHLNTaMMVTZFNfTApPeprPNlcjsGvlWFNEUgIDrObVPCdKfMBCFDQFfoJXZcpCLavkPMaEJAqSlqjlETKjawFyfktHTCXgCMZPAroyYPfoxnekPOdEeAEbwohbcaeCMDVgBRAgmHazIdAFQKyKafNTtZsYmmFCkyYutvNAglZxKEmIVCmHDqsMpTQDwoRkdrdaDIxLBUJAvsONsqWxmeVsdsUTHJViFwJMWllVlyYSSZyUDfVHzbLaqlOdlukYORtPXaSZCQXKzHzDzamWpoFNXSnadHpPgOzinRqzHKAlpwTMCBYcteXvzynMUPjKDzUZviwGHpEiWPBiDvzjEEEHtJgVokIkErOCNxwJNoZglqprxBpmjPPRLoLhZoFuBqzDLiZuvVbmGbqOtSgXfyZnmWRBWjRPtAUicHREzvKqDXzUPknLBUYevIj"
			+crypto_date)

		apiVersion

		builder.add(Program.POST_APP,"uid",uid)
		builder.add(Program.POST_APP,"key",key)
		builder.add(Program.POST_APP,"crypto_date",crypto_date)
		builder.add(Program.POST_APP,"device_uid",device_uid)
		builder.add(Program.POST_APP,"build",build)
		builder.add(Program.POST_APP,"os_type",os_type)
		builder
	}

	override def sendError(error: ErrorElement) {
	}

	private[this] val uid ="test_app"

	private[this] var device_uid=""
	private[this] var os_type="Android"
	private[this] var build="1.0.0"

	val DEVICE_UID: String = "dev_id_for_client"

	override def apiVersion: String = {
		if (api.isEmpty) {
			api = Try {
				val context = this.info.getContext
				if (context != null) {
				//	LogD("create api")//todo: log out
					val pInfo = context.getPackageManager.getPackageInfo(context.getPackageName, 0)
					val os = android.os.Build.VERSION.SDK_INT
					val version = pInfo.versionName
					val code = pInfo.versionCode


					val reader=context.getSharedPreferences("system_propref", Context.MODE_PRIVATE)
					device_uid=SONS.get(reader,DEVICE_UID,device_uid)
			//		LogD(device_uid)//todo: log out
					if (device_uid==null || device_uid.isEmpty) {
						val editor = reader.edit()
						device_uid = UUID.randomUUID().toString + UUID.randomUUID().toString
						SONS.put(editor,DEVICE_UID, device_uid)
						editor.apply()
					}
			//		LogD(device_uid)//todo: log out
			//		LogD("end api")//todo: log out

					os_type=s"Android_$os"
					build=s"$version"

					s"Android_${os}_${version}_${code}_"

				} else
					""
			}.getOrElse("")
		}
		api
	}

	override def tableName(infoType: String): String = {
		infoType match {
			case Program.API_LIST ⇒		DataBase.TABLE_LIST_NAME
			case Program.API_ORDER ⇒	DataBase.TABLE_ORDER
			case Program.API_PRODUCT ⇒	DataBase.TABLE_PRODUCT
			case Program.API_SECTION ⇒	DataBase.TABLE_SECTION
			case Program.API_ADS ⇒ 		DataBase.TABLE_ADS
			case _ ⇒					DataBase.TABLE_OTHER_NAME
		}
	}

	override def getDB: SQLiteDatabase = {
		var p:SQLiteDatabase=null

		try {
			p = new DataBase(this.getContext).getReadableDatabase
		}
		catch{
			case e: Exception ⇒
	//			e.printStackTrace()
		}
		p
	}

	override def getWritableDB: SQLiteDatabase = {
		var p:SQLiteDatabase=null

		try {
			p = new DataBase(this.getContext).getWritableDatabase
		}
		catch{
			case e: Exception ⇒
			//			e.printStackTrace()
		}
		p
	}
}