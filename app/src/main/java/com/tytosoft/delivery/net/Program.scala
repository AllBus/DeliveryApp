package com.tytosoft.delivery.net

/**
  * Created by Kos on 06.07.2016.
  */
object Program {

	//val API="http://api.devparking.club/v1/"
	val API = "https://takocrm.ru/api/takodemo/"

	val _ACTION_EMPTY=""

	val API_SECTION="section"//
	val API_TOP = "section/top"
	val API_PRODUCT="product"//
	val API_ADS="ads"//
	val API_ORDER ="order"//
	val API_ORDER_ADD="order/add"//
	val API_SYSTEM ="system"//Возвращает перечень системных параметров
	val API_LIST="list"//Возвращает списки элементов

	val API_SUBSCRIBE="subscribe"//Оформляет подписку устройства на уведомления

	val POST_APP="App"
	val POST_DATA="Data"
	val POST_CLIENT="client"
	val POST_ORDER="order"


	val LIST_SECTION= "section"
	val LIST_PRODUCT= "product"
	val LIST_ADV ="ads"

	val CODE_COMPLETE=200

	def isZakazComplete(code:Int):Boolean= code == CODE_COMPLETE
}
