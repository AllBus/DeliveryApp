package com.tytosoft.delivery.common.utils

/**
  * Created by Kos on 17.07.2016.
  */
object StringUtils {

	def textOrEmpty(text:String,append:String)={
		if (text.isEmpty)
			""
		else
			append+text
	}
}
