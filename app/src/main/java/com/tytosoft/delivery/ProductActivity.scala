package com.tytosoft.delivery

import android.os.Bundle
import android.support.design.widget.{FloatingActionButton, Snackbar}
import android.view.View.OnClickListener
import android.view.{MenuItem, View}
import android.widget.{ImageView, TextView}
import com.kos.delivery.net.DataStore
import com.kos.delivery.net.DataStore._
import com.kos.fastuimodule.common.ui.U
import com.tytosoft.delivery.common.utils.BusActivity

class ProductActivity extends BusActivity {

	lazy val id=getID
	lazy val product=DataStore.products.get(id)

	protected override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_product)

		setupToolBarWithBackButton(R.id.toolbar)

		val fab: FloatingActionButton = findViewById(R.id.fab).asInstanceOf[FloatingActionButton]

		U.gone(fab,product.isActive)

		fab.setOnClickListener(new View.OnClickListener() {
			def onClick(view: View) {
				lastKorzina.add(product.getId, product.getPricesCurrent, 1)
				saveData(lastKorzina)

				Snackbar.make(view, R.string.snackAddToKorzina, Snackbar.LENGTH_LONG).setAction(R.string.snackAddToKorzinaGoTo, new OnClickListener {
					override def onClick(view: View): Unit = {
						show(classOf[KorzinaActivity])
					}
				}).show
			}
		})

		setTitle(product.getName)

		U.text(find[TextView](R.id.text),product.getDetail)
		U.image(find[ImageView](R.id.image),product.getImageBig)

	}

	override def onOptionsItemSelected(item: MenuItem): Boolean = {
		item.getItemId match {
			case android.R.id.home ⇒
				onBackPressed()
			case _ ⇒
				return super.onOptionsItemSelected (item)
		}
		return true

	}
}