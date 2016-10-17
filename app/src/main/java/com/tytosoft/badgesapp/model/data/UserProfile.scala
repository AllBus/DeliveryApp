package com.tytosoft.badgesapp.model.data

/**
  * Created by Kos on 09.07.2016.
  */
class UserProfile {

	var phone=""
	var name=""
	var address=""

	var progressState=0

	//todo: нужно определить правила проверки
	def checkPhone = phone.trim.length>2
	def checkName = name.trim.length>0
	def checkAddress= address.trim.length>5

	def completeAll = checkAddress && checkName && checkPhone


}
