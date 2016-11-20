package com.tytosoft.delivery.data

import android.content.Context
import com.tytosoft.delivery.model.data.UserProfile
import com.tytosoft.delivery.model.utils.SONS
import com.tytosoft.delivery.R
import com.tytosoft.delivery.model.SystemModel
import com.tytosoft.delivery.model.data.Korzina

/**
  * Created by Kos on 06.07.2016.
  */
class AppPrefererences(val context: Context,profile:UserProfile,korzina:Korzina) {

	def saveKorzina(korzina: Korzina) = {
		//todo:save korzina
	}

	def saveProfile(profile: UserProfile) = {
		val editor=context.getSharedPreferences("system_pref", Context.MODE_PRIVATE).edit()
		SONS.put(editor,"profile_name", profile.name)
		SONS.put(editor,"profile_phone", profile.phone)
		SONS.put(editor,"profile_address", profile.address)
		editor.commit()
	}

	var adsIndex = 0


	def price(priceValue: Int) = priceValue+currency


	def currency: String = " \u20bd"


	def phone: String = {
		val i=systemModel.companyPhone.indexOf(":")
		if (i>=0)
			systemModel.companyPhone.substring(i+1)
		else
			systemModel.companyPhone
	}

	def dialStartTime: String = systemModel.deliveryTimeFrom

	def dialEndTime: String =  systemModel.deliveryTimeTo

	def tintColor: Int = 0xA0FFFFFF

	def dostavkaTime: String = systemModel.deliveryTime.toString+" мин."


	def dostavkaPrice: String =dostavkaPriceValue + currency
	def dostavkaMinFree: String = context.getString(R.string.dostavkaMinFreePrefix)+minFreeValue + currency
	def minZakazCost: String = minCostValue + currency

	def dostavkaPriceValue=systemModel.deliveryPrice
	def minFreeValue=systemModel.freeDeliveryPrice
	def minCostValue=systemModel.minOrderSumma

	def companyName=systemModel.companyName

	def saveModel() = {
		systemModel.save(context.getSharedPreferences("system_pref", Context.MODE_PRIVATE))
	}

	var systemModel:SystemModel= new SystemModel(context.getSharedPreferences("system_pref", Context.MODE_PRIVATE))

	def needUpdate(): Boolean = {
		systemModel.getModificationDate<System.currentTimeMillis()-2*Day

	}

	val editor=context.getSharedPreferences("system_pref", Context.MODE_PRIVATE)
	profile.name=SONS.get(editor,"profile_name", profile.name)
	profile.phone=SONS.get(editor,"profile_phone", profile.phone)
	profile.address=SONS.get(editor,"profile_address", profile.address)


	val Day=1000L*60*60*24
}
