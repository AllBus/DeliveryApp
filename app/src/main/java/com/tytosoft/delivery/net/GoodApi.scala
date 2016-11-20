package com.tytosoft.delivery.net

import java.util.concurrent.Executors

import com.kos.fastuimodule.good.common.IDSet
import com.kos.fastuimodule.good.common.model.IEntity
import com.kos.fastuimodule.good.common.system.collection.ListLoadingData
import com.kos.fastuimodule.good.common.system.{EConstructType, GoodPostArguments}
import com.tytosoft.delivery.net.loader.GoodBasesLoader
import com.tytosoft.delivery.net.constructors.EntityConstructor
import com.tytosoft.delivery.model.ListModel


/**
  * Created by Kos on 13.07.2016.
  */
object GoodApi {
	import Program._

	val tpe = Executors.newSingleThreadExecutor()

	val EMPTY_IDS = Set(-1L)
	val NONE_IDS = Set(ListModel.NONE_ID)

	def load[T <: IEntity](infoType: String, action: String, onlyServer: Boolean = false)(listData: ListLoadingData[T], ids: IDSet): Unit = {

		if (ids == NONE_IDS) {
			return
		}

		val arguments = new GoodPostArguments(infoType, action,
			ids,
			if (ids == EMPTY_IDS)
				EConstructType.action
			else
				EConstructType.idType,
			listData.start, listData.end)

		new GoodBasesLoader(
			DataStore.info,
			API + infoType,
			new EntityConstructor(DataStore.info, arguments, listData),
			arguments,
			onlyServer, false, false
		).executeOnExecutor(tpe)
	}

	def loadBase[T <: IEntity](infoType: String, action: String)(listData: ListLoadingData[T], ids: IDSet): Unit = {

		if (ids == NONE_IDS) {
			return
		}

		val arguments = new GoodPostArguments(infoType, action,
			ids,
			if (ids == EMPTY_IDS)
				EConstructType.action
			else
				EConstructType.idType,
			listData.start, listData.end)

		new GoodBasesLoader(
			DataStore.info,
			API + infoType,
			new EntityConstructor(DataStore.info, arguments, listData),
			arguments,
			false, false, true
		).executeOnExecutor(tpe)
	}
}
