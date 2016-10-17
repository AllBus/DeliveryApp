package com.tytosoft.delivery.common.utils

import android.support.v7.app.AppCompatActivity
import com.kos.fastuimodule.good.common.bus.BusProvider
import com.tytosoft.badgesapp.common.utils.SActivity


/**
  * Created by Kos on 14.07.2016.
  */
class BusActivity extends AppCompatActivity with SActivity{
	override def onStart(): Unit = {
		super.onStart()
		BusProvider.register(this)
	}

	override def onStop(): Unit = {
		BusProvider.unregister(this)
		super.onStop()

	}
}
