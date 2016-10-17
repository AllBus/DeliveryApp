package com.tytosoft.delivery.net

/**
  * Created by Kos on 06.07.2016.
  */
class TGetListNameValuePair() {
	val sb=Seq.newBuilder[String]
	def encodeGET(): String = sb.result().mkString("?","&","")

	def add(name: String, value: String) = {
		sb+= name+"="+value
	}

}
