package com.tytosoft.delivery.fragments

import android.support.v4.app.Fragment
import com.kos.fastuimodule.good.common.bus.BusProvider
import com.tytosoft.badgesapp.common.utils.SFragment

/**
  * Created by Kos on 17.07.2016.
  */
class BusFragment extends Fragment with SFragment{

	override def onStart(): Unit = {
		super.onStart()
		BusProvider.register(this)
	}

	override def onStop(): Unit = {
		BusProvider.unregister(this)
		super.onStop()

	}
}
