package com.tytosoft.delivery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.View.OnClickListener
import android.view.{Menu, MenuItem, View}
import android.widget.{Button, TextView}
import com.kos.delivery.model.OrderProductModel
import com.kos.delivery.net.DataStore
import com.squareup.otto.Subscribe
import com.tytosoft.badgesapp.common.dialogs.AlertDialogFragment
import com.tytosoft.badgesapp.common.dialogs.AlertDialogFragment.IYesNoListener
import com.tytosoft.badgesapp.net.KorzinaUpdater
import com.tytosoft.badgesapp.views.TwoLineTextView
import com.tytosoft.delivery.adapters.KorzinaAdapter
import com.tytosoft.delivery.common.helpers.ResHelper
import com.tytosoft.delivery.common.utils.BusActivity

class KorzinaActivity extends BusActivity with OnClickListener with IYesNoListener{

	import DataStore._

	private[this] lazy val dostavkaTime:TwoLineTextView=find(R.id.dostavkaTime)
	private[this] lazy val dostavkaCost:TwoLineTextView=find(R.id.dostavkaCost)
	private[this] lazy val dostavkaPrice:TwoLineTextView=find(R.id.dostavkaPrice)
	private[this] lazy val dostavkaFree:TwoLineTextView=find(R.id.dostavkaFree)
	private[this] lazy val list:RecyclerView=find(R.id.list)
	private[this] lazy val completeCost:TextView=find(R.id.completeCost)
	private[this] lazy val createZakazBtn:Button=find(R.id.createZakazBtn)

	val selectColor=0xFF000000
	val unselectColor=0xFFD4D4D4
	val KORZINA_CLEAR=1

	lazy val adapter= new KorzinaAdapter(
		this,
		R.layout.item_ticket_product_button,
		lastKorzina,
		itemClick,
		plusClick,
		minusClick

	)

	lazy val itemClick:OnClickListener= (view: View) => {
		view.getTag match {
			case product: OrderProductModel ⇒
				if (!product.isNull) {
					show(classOf[ProductActivity], product.getId)
				}

			case _ ⇒
		}

	}

	lazy val plusClick:OnClickListener= (view: View) => {
		view.getTag match {
			case v: View ⇒
				v.getTag match {
					case product: OrderProductModel ⇒
						if (!product.isNull) {

							plusItem(product)
						}

					case _ ⇒
				}
			case _ ⇒
		}

	}
	lazy val minusClick:OnClickListener= (view: View) => {
		view.getTag match {
			case v: View ⇒
				v.getTag match {
					case product: OrderProductModel ⇒
						if (!product.isNull) {
							minusItem(product)
						}

					case _ ⇒
				}
			case _ ⇒
		}

	}


	def plusItem(product:OrderProductModel): Unit ={
		product+=1
		updateState()
		adapter.updateItems(Seq(product.getId))
	}

	def minusItem(product:OrderProductModel): Unit ={
		product-=1
		if (product.count==0){
			adapter.remove(lastKorzina.indexOf(product.getId))
		}else{
			adapter.updateItems(Seq(product.getId))
		}
		updateState()
	}
	protected override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_korzina)
		setupToolBarWithBackButton(R.id.toolbar)


		Seq(R.id.createZakazBtn).foreach(addClick(_,this))

		dostavkaTime.setFirstText(preferences.dostavkaTime)
		dostavkaCost.setFirstText(preferences.minZakazCost)
		dostavkaPrice.setFirstText(preferences.dostavkaPrice)
		dostavkaFree.setFirstText(preferences.dostavkaMinFree)

		dostavkaTime.setTintColor(selectColor)
		dostavkaCost.setTintColor(selectColor)
		dostavkaPrice.setTintColor(selectColor)
		dostavkaFree.setTintColor(selectColor)

		list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
		list.setAdapter(adapter)
	}

	override def onStart(): Unit ={
		super.onStart()
		updateState()
		adapter.notifyDataSetChanged()
	}

	override def onResume(): Unit = {
		super.onResume()

	}

	def updateState(): Unit ={

		val bFree=lastKorzina.isFree(preferences)
		val bComplete=lastKorzina.isComplete(preferences)

		dostavkaFree.setTintColor(if (bFree) selectColor else unselectColor	)
		dostavkaPrice.setTintColor(if (!bFree) selectColor else unselectColor	)
		dostavkaCost.setTintColor(if (bComplete) selectColor else unselectColor	)
		completeCost.setTextColor(if (bComplete) selectColor else unselectColor)

		completeCost.setText(lastKorzina.getCost(preferences))
		createZakazBtn.setEnabled(bComplete)

	//	list.setAdapter(adapter)
	}



	override def onOptionsItemSelected(item: MenuItem): Boolean = {
		item.getItemId match {
			case android.R.id.home ⇒
				onBackPressed()
			case R.id.actionClearKorzina ⇒
				AlertDialogFragment.newInstance(R.string.alertClearKorzinaTitle, R.string.alertClearKorzinaText,KORZINA_CLEAR).show(fragmentManager, "ALERT_KORZINA");

			case _ ⇒
				return super.onOptionsItemSelected (item)
		}
		return true

	}

	override def onClick(view: View): Unit = {
		view.getId match {
				case R.id.createZakazBtn⇒
					lastProfile.progressState=0
					showForResult(classOf[ActivityCreateZakaz],ActivityCreateZakaz.CREATE_ZAKAZ_CODE)
			case _ ⇒
		}
	}

	override def onCreateOptionsMenu(menu: Menu): Boolean = {
		super.onCreateOptionsMenu(menu)
		getMenuInflater.inflate(R.menu.menu_korzina, menu)

		ResHelper.tintingMenu(menu, preferences.tintColor)
		true
	}


	@Subscribe
	def updateKorzina(updater: KorzinaUpdater): Unit ={
		//todo:
		updateState()
		adapter.notifyDataSetChanged()
	}

	def onYes(index: Int): Unit ={
		if (index==KORZINA_CLEAR)
			lastKorzina.clear()
	}

	def onNo(index: Int): Unit ={

	}

	override protected def onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
		if (requestCode == ActivityCreateZakaz.CREATE_ZAKAZ_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				this.finish()
			}
		}
	}

	override def onPause(): Unit = {
		saveData(lastKorzina)
		super.onPause()

	}
}