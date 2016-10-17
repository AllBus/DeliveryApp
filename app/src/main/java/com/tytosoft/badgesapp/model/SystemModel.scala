package com.tytosoft.badgesapp.model

import android.content.SharedPreferences
import com.kos.delivery.net.DataStore
import com.kos.fastuimodule.common.share.SON
import com.tytosoft.badgesapp.model.utils.SONS
import org.json.JSONObject

/**
  * Created by Kos on 13.07.2016.
  */
object SystemModel{
	private val MAIN_PAGE_TYPE: String = "main_page_type"
	private val MOD_TIME: String = "mod_time"
	private val BEFORE_CALL_TEXT: String = "before_call_text"
	private val DELIVERY_TIME: String = "delivery_time"
	private val DELIVERY_TIME_TO: String = DELIVERY_TIME + "_to"
	private val DELIVERY_TIME_FROM: String = DELIVERY_TIME + "_from"
	private val COMPANY_NAME: String = "company_name"
	private val MIN_ORDER_SUMMMA: String = "min_order_summa"
	private val COMPANY_PHONE: String = "company_phone"
	private val DELIVERY_PRICE: String = "delivery_price"
	private val FREE_DELIVERY_PRICE: String = "free_" + DELIVERY_PRICE
}

class SystemModel extends BaseModel{

	import SystemModel._

	def this(obj: JSONObject) {
		this()
		if (null != obj) baseSON(obj)
	}

	def this(sharedPreferences: SharedPreferences){
		this()

		main_page_type=SONS.get(sharedPreferences,MAIN_PAGE_TYPE, main_page_type)
		delivery_time=SONS.get(sharedPreferences,DELIVERY_TIME, delivery_time)
		delivery_price=SONS.get(sharedPreferences,DELIVERY_PRICE,delivery_price)
		free_delivery_price=SONS.get(sharedPreferences,FREE_DELIVERY_PRICE,free_delivery_price)
		company_phone=SONS.get(sharedPreferences,COMPANY_PHONE,company_phone)
		min_order_summa=SONS.get(sharedPreferences,MIN_ORDER_SUMMMA,min_order_summa)
		company_name=SONS.get(sharedPreferences,COMPANY_NAME,company_name)
		delivery_time_from=SONS.get(sharedPreferences,DELIVERY_TIME_FROM,delivery_time_from)
		delivery_time_to=SONS.get(sharedPreferences,DELIVERY_TIME_TO,delivery_time_to)
		before_call_text=SONS.get(sharedPreferences,BEFORE_CALL_TEXT,before_call_text)
		modification_date=SONS.get(sharedPreferences,MOD_TIME,modification_date)
	}

	def save(sharedPreferences: SharedPreferences): Unit ={
		val editor=sharedPreferences.edit()
		SONS.put(editor,MAIN_PAGE_TYPE,main_page_type)
		SONS.put(editor,DELIVERY_TIME,delivery_time)
		SONS.put(editor,DELIVERY_PRICE,delivery_price)
		SONS.put(editor,FREE_DELIVERY_PRICE,free_delivery_price)
		SONS.put(editor,COMPANY_PHONE,company_phone)
		SONS.put(editor,MIN_ORDER_SUMMMA,min_order_summa)
		SONS.put(editor,COMPANY_NAME,company_name)
		SONS.put(editor,DELIVERY_TIME_FROM,delivery_time_from)
		SONS.put(editor,DELIVERY_TIME_TO,delivery_time_to)
		SONS.put(editor,BEFORE_CALL_TEXT,before_call_text)
		SONS.put(editor,MOD_TIME,System.currentTimeMillis())
		editor.commit()
	}

	private[this] var main_page_type		=""	//Тип списка на главной странице - section | product
	private[this] var delivery_time			=0L	//Время доставки (используется в заказе)
	private[this] var delivery_price		=0	//Стоимость доставки (используется в заказе)
	private[this] var free_delivery_price	=0	//Стоимость бесплатной доставки ((используется в заказе))
	private[this] var company_phone			=""	//Номер телефона для связи с фирмой
	private[this] var min_order_summa		=0	//Минимальная сумма заказа (используется в заказе)
	private[this] var company_name			=""	//Название компании
	private[this] var delivery_time_from	=""	//Начальное время доставки и приема заказов (используется в иконке звонка)
	private[this] var delivery_time_to 		=""	//Конечное время доставки и приема заказов (используется в иконке звонка)
	private[this] var before_call_text		=""	//Текст, который предупреждает пользователя, о том, что сейчас будет произведен звонок оператору

	override def baseSON(obj: JSONObject): Unit = {
		super.baseSON(obj)
		main_page_type=SONS.get(obj,MAIN_PAGE_TYPE,main_page_type)
		delivery_time=SON.get(obj,DELIVERY_TIME,delivery_time)
		delivery_price=SON.get(obj,DELIVERY_PRICE,delivery_price)
		free_delivery_price=SON.get(obj,FREE_DELIVERY_PRICE,free_delivery_price)
		min_order_summa=SON.get(obj,MIN_ORDER_SUMMMA,min_order_summa)

		company_phone=SONS.get(obj,COMPANY_PHONE,company_phone)
		company_name=SONS.get(obj,COMPANY_NAME,company_name)
		delivery_time_from=SONS.get(obj,DELIVERY_TIME_FROM,delivery_time_from)
		delivery_time_to=SONS.get(obj,DELIVERY_TIME_TO,delivery_time_to)
		before_call_text=SONS.get(obj,BEFORE_CALL_TEXT,before_call_text)

		modification_date=System.currentTimeMillis()

//		LogD(main_page_type+" "+
//			delivery_time+" "+
//			delivery_price+" "+
//			free_delivery_price+" "+
//			min_order_summa+" "+
//			company_phone+" "+
//
//			company_name+" "+
//			delivery_time_from+" "+
//			delivery_time_to+" "+
//			before_call_text+" "+
//			modification_date+" "
//		)
	}

	def mainPageKind		=main_page_type

	def deliveryTime		=delivery_time
	def deliveryPrice		=delivery_price
	def freeDeliveryPrice	=free_delivery_price
	def companyPhone		=company_phone
	def minOrderSumma		=min_order_summa
	def companyName			=company_name
	def deliveryTimeFrom	=delivery_time_from
	def deliveryTimeTo 		=delivery_time_to
	def beforeCallText		=before_call_text

	override def handle: Unit = {
		DataStore.updatePreferences(this)
	}
}
