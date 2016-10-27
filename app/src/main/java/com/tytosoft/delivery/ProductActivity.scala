package com.tytosoft.delivery

import android.os.Bundle
import android.support.design.widget.{FloatingActionButton, Snackbar}
import android.view.View.OnClickListener
import android.view.{Menu, MenuItem, View}
import android.widget.{ImageView, TextView}
import com.kos.delivery.net.DataStore
import com.kos.delivery.net.DataStore._
import com.kos.fastuimodule.common.ui.U
import com.tytosoft.delivery.adapters.holders.FullProductHolder
import com.tytosoft.delivery.common.helpers.ResHelper
import com.tytosoft.delivery.common.utils.BusActivity
import com.tytosoft.delivery.views.FABwithText

class ProductActivity extends BusActivity with OnClickListener{

	lazy val fab=find[FABwithText](R.id.fab)


	lazy val id=getID
	lazy val product=DataStore.products.get(id)

	protected override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_product_v2)

		setupToolBarWithBackButton(R.id.toolbar)

	//	val addToTash: View = findViewById(R.id.addToTashBtn).asInstanceOf[FloatingActionButton]
	//	U.gone(addToTash,product.isActive)


		val addToTashClick=	new View.OnClickListener() {
			def onClick(view: View) {
				lastKorzina.add(product.getId, product.getPricesCurrent, 1)
				saveData(lastKorzina)
				updateKorzina()

//				Snackbar.make(view, R.string.snackAddToKorzina, Snackbar.LENGTH_LONG).setAction(R.string.snackAddToKorzinaGoTo, new OnClickListener {
//					override def onClick(view: View): Unit = {
//						show(classOf[KorzinaActivity])
//					}
//				}).show
			}
		}
	//	addToTash.setOnClickListener(addToTashClick)
		fab.setOnClickListener(this)

		setTitle(product.getName)

		//U.text(find[TextView](R.id.text),product.getDetail)
		U.image(find[ImageView](R.id.image),product.getImageBig)
		val holder=new FullProductHolder(find(R.id.topLayout),addToTashClick,null)
		holder.bind(0,product)

	}

	override def onOptionsItemSelected(item: MenuItem): Boolean = {
		item.getItemId match {
			case android.R.id.home ⇒
				onBackPressed()
			case R.id.actionShowKorzina ⇒
				show(classOf[KorzinaActivity])
			case _ ⇒
				return super.onOptionsItemSelected (item)
		}
		return true

	}

	override def onClick(view: View): Unit = {
		view.getId match {
			case R.id.fab ⇒
				show(classOf[KorzinaActivity])

			case _ ⇒
		}
	}

//	override def onCreateOptionsMenu(menu: Menu): Boolean = {
//		super.onCreateOptionsMenu(menu)
//		getMenuInflater.inflate(R.menu.menu_product, menu)
//
//		ResHelper.tintingMenu(menu, preferences.tintColor)
//		true
//	}

	def updateKorzina(): Unit ={

		val zakaz=lastKorzina.summa
		fab.show()
		if (zakaz==0)
			fab.setText(null)
		else
			fab.setText(zakaz.toString)
	}

	override def onResume(): Unit = {
		super.onResume()
		updateKorzina()
	}
}