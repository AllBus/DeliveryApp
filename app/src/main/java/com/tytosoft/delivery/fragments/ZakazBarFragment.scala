package com.tytosoft.delivery.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.View.OnClickListener
import android.view.{LayoutInflater, View, ViewGroup}
import com.kos.delivery.common.utils.LogD
import com.kos.delivery.net.{DataStore, OrderItemUpdate}
import com.kos.fastuimodule.good.common.bus.EndLoadUpdate
import com.squareup.otto.Subscribe
import com.tytosoft.badgesapp.adapters.HistoryOrderAdapter
import com.tytosoft.badgesapp.model.{OrderModel, ProductModel}
import com.tytosoft.badgesapp.net.{ProductItemUpdate, Program}
import com.tytosoft.delivery.model.controler.OrderModelController
import com.tytosoft.delivery.{DetailActivity, KorzinaActivity, ProductActivity, R}

/**
  * A simple {@link Fragment} subclass.
  * Use the {@link ZakazBarFragment#newInstance} factory method to
  * create an instance of this fragment.
  */
object ZakazBarFragment {
	private val ARG_PARAM1: String = "param1"
	private val ARG_PARAM2: String = "param2"

	/**
	  * Use this factory method to create a new instance of
	  * this fragment using the provided parameters.
	  *
	  * @param param1 Parameter 1.
	  * @param param2 Parameter 2.
	  * @return A new instance of fragment ZakazBarFragment.
	  */
	def newInstance(param1: String, param2: String): ZakazBarFragment = {
		val fragment: ZakazBarFragment = new ZakazBarFragment
		val args: Bundle = new Bundle
		args.putString(ARG_PARAM1, param1)
		args.putString(ARG_PARAM2, param2)
		fragment.setArguments(args)
		return fragment
	}
}

class ZakazBarFragment extends BusFragment with OnRefreshListener {
	import DataStore._
	private var mParam1: String = null
	private var mParam2: String = null


	var swipe:SwipeRefreshLayout=_
	private[this] lazy val controller=new OrderModelController(orders.get)



	private[this] lazy val adapter=new HistoryOrderAdapter(
		context,
		R.layout.item_ticket,
		controller,
		itemClick
	)

	val itemClick: OnClickListener = (view: View) => {
		view.getTag match{
			case product: ProductModel ⇒
				show(classOf[ProductActivity],product.getId)
			case order:OrderModel ⇒
				show(classOf[DetailActivity],order.getId)
				//order.addToKorzina( lastKorzina)
				//show(classOf[KorzinaActivity])
			case _ ⇒
		}
	}

	override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		if (getArguments != null) {
			mParam1 = getArguments.getString(ZakazBarFragment.ARG_PARAM1)
			mParam2 = getArguments.getString(ZakazBarFragment.ARG_PARAM2)
		}
	}

	override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
		val view: View = inflater.inflate(R.layout.nav_zakaz_bar, container, false)

		val list=find[RecyclerView](view,R.id.list)
		swipe=find[SwipeRefreshLayout](view,R.id.swipe)

		loadZakazHistory()


		list.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false))
		list.setAdapter(adapter)

		swipe.setOnRefreshListener(this)

		view
	}


	def reload(): Unit = {
		adapter.changeList(orders.allItems())
		swipe.setRefreshing(false)
	}

	override def onResume(): Unit = {
		super.onResume()
		reload()
	}

	@Subscribe
	def updateOrder(updater:OrderItemUpdate): Unit ={
		//todo:
	}

	@Subscribe
	def updateProduct(updater:ProductItemUpdate): Unit ={
		if (controller.hasUpdates(updater.id))
			adapter.notifyDataSetChanged()

	}

	@Subscribe
	def updateEndLoad(updater:EndLoadUpdate):Unit={
		if (updater.infoType==Program.API_ORDER){
			reload()
		}
	}

	override def onRefresh(): Unit = {
		reload()
	}
}