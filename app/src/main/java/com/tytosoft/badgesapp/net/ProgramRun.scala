package com.tytosoft.badgesapp.net

import java.util.concurrent.Executors

import com.kos.delivery.net.DataStore
import com.kos.delivery.net.DataStore._
import com.kos.fastuimodule.common.net.JSONRequest
import com.tytosoft.badgesapp.model.data.UserProfile
import com.tytosoft.badgesapp.net.constructors.SystemConstructor
import com.tytosoft.badgesapp.net.posts.{Poster, AddOrderPost}
import com.tytosoft.delivery.model.data.Korzina
import com.tytosoft.delivery.net.GoodApi
import com.tytosoft.delivery.net.constructors.CodeConstructor


/**
  * Created by Kos on 06.07.2016.
  */
object ProgramRun {
	def addOrder(profile: UserProfile, korzina: Korzina) = {
		val poster = new AddOrderPost(info, new CodeConstructor(Program.API_ORDER_ADD),profile,korzina)

		JSONRequest.asynh(info, Program.API + Program.API_ORDER_ADD, poster)
	}

	def order(profile: UserProfile, korzina: Korzina) = {
		val poster = new AddOrderPost(info, new CodeConstructor(Program.API_ORDER),profile,korzina)

		JSONRequest.asynh(info, Program.API + Program.API_ORDER, poster)
	}



	val tpe = Executors.newSingleThreadExecutor()
	val EMPTY_IDS = Set(-1L)



	def getSystem(): Unit = {

		if (preferences.needUpdate()) {

			val poster = new Poster(info, new SystemConstructor(),Set.empty)

			//new Zapros(DataStore.info, poster).execute(Program.API+Program.API_LIST)
			//JSONRequest.asynh(DataStore.info,Program.API+Program.API_SYSTEM,poster)

			//	LogD("Start")

			JSONRequest.asynh(DataStore.info, Program.API + Program.API_SYSTEM, poster)
		}
	}

	def getAds(): Unit = {
		//val poster = new Poster(info, new AdsConstructor())
	}

	def getLists(): Unit = {

		GoodApi.load(Program.API_LIST, Program._ACTION_EMPTY)(lists, EMPTY_IDS)

	}


}