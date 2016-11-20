package com.tytosoft.delivery.model.utils

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import com.kos.fastuimodule.good.common.ID
import com.tytosoft.delivery.model.OrderStatusModel
import org.json.{JSONArray, JSONObject}



/**
  * Created by Kos on 13.07.2016.
  */
object SONS {

	def put(obj: JSONObject, name: String, value: Seq[ISave]) = {
		val arr=new JSONArray()
		value.foreach(x ⇒ arr.put(x.save()))
		obj.putOpt(name, arr)
	}

	def put(obj: JSONObject, name: String, value: ISave) = {
		obj.putOpt(name, value.save())
	}

	def put(obj: JSONObject, name: String, subName :String, value: String) = {
		val subObj=new JSONObject()
		subObj.putOpt(subName,value)
		obj.putOpt(name, subObj)
	}

	//=====================================

	def put(bundle: Bundle, name: String, id: ID) = {
		bundle.putLong(name,id)
	}

	def put(bundle: Bundle, name: String, id: Int) = {
		bundle.putInt(name,id)
	}

	def put(bundle: Bundle, name: String, id: String) = {
		bundle.putString(name,id)
	}

	def get(bundle: Bundle, name: String, typ: String): String = {
		bundle.getString(name,typ)
	}

	def get(bundle: Bundle, name: String, typ: ID): ID = {
		bundle.getLong(name,typ)
	}

	def get(bundle: Bundle, name: String, typ: Int): Int = {
		bundle.getInt(name,typ)
	}


	//============================================

	def get(sharedPreferences: SharedPreferences, name: String, typ: String): String = {
		try {
			sharedPreferences.getString(name, typ)
		}catch {
			case _: Throwable ⇒ ""
		}
	}



	def get(sharedPreferences: SharedPreferences, name: String, typ: Int): Int = {
		sharedPreferences.getInt(name,typ)
	}

	def get(sharedPreferences: SharedPreferences, name: String, typ: Float): Float = {
		sharedPreferences.getFloat(name,typ)
	}

	def get(sharedPreferences: SharedPreferences, name: String, typ: Double): Double = {
		java.lang.Double.longBitsToDouble(sharedPreferences.getLong(name, 0))
	}

	def get(sharedPreferences: SharedPreferences, name: String, typ: Long): Long = {
		sharedPreferences.getLong(name,typ)
	}

	def get(sharedPreferences: SharedPreferences, name: String, typ: Boolean): Boolean = {
		sharedPreferences.getBoolean(name,typ)
	}

	def put(edit:Editor, name:String, value:Double)= {
		edit.putLong(name, java.lang.Double.doubleToRawLongBits(value))
	}

	def put(edit:Editor, name:String, value:Long)= {
		edit.putLong(name, value)
	}
	def put(edit:Editor, name:String, value:Int)= {
		edit.putInt(name, value)
	}
	def put(edit:Editor, name:String, value:Float)= {
		edit.putFloat(name, value)
	}
	def put(edit:Editor, name:String, value:Boolean)= {
		edit.putBoolean(name, value)
	}
	def put(edit:Editor, name:String, value:String)= {
		edit.putString(name, value)
	}

	//=====================
	def get(jsonObject: JSONObject, name: String, subName :String, typ: Int): Int ={
		val jobj=jsonObject.optJSONObject(name)
		if (jobj!=null){
			jobj.optInt(subName)
		}
		else
			0
	}

	def get(jsonObject: JSONObject, name: String,typ:String):String={
		val res=jsonObject.optString(name, typ)
		if (res!=null && res!="null")
			res
		else
			""
	}

	def get(jsonObject: JSONObject, name: String, subName :String, typ: String): String ={
		val jobj=jsonObject.optJSONObject(name)
		if (jobj!=null){
			jobj.optString(subName)
		}
		else
			""
	}

	def getIDList(jsonObject: JSONObject, name: String): Seq[ID] = {
		val arr=jsonObject.optJSONArray(name)
		val result=Vector.newBuilder[Long]
		if (arr!=null){
			var i=0
			while (i<arr.length()){
				result+=arr.optLong(i)
				i+=1
			}
		}
		result.result()
	}

	def get(jsonObject: JSONObject,
			name: String,
			run: JSONObject ⇒ OrderStatusModel,
			orElse: ⇒ OrderStatusModel): OrderStatusModel = {
		val jobj=jsonObject.optJSONObject(name)
		if (jobj!=null){
			run(jsonObject)
		}else{
			orElse
		}
	}
}
