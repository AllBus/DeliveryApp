package com.tytosoft.delivery

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.{MenuItem, View}
import android.view.View.OnClickListener
import com.kos.delivery.net.DataStore
import com.tytosoft.badgesapp.adapters.holders.OrderProductHolder
import com.tytosoft.badgesapp.model.{OrderModel, ProductModel}
import com.tytosoft.delivery.common.utils.BusActivity
import com.tytosoft.delivery.model.AdsModel

class DetailActivity extends BusActivity with OnClickListener {
	import DataStore._

	lazy val id=getID
	lazy val order=orders.get(id)

	lazy val itemClick: OnClickListener = (view: View) => {
		view.getTag match{
			case product: ProductModel ⇒
				show(classOf[ProductActivity],product.getId)
			case order:OrderModel ⇒

				order.addToKorzina( lastKorzina)
				show(classOf[KorzinaActivity])
			case _ ⇒

		}
	}

	override protected def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)

		setupToolBarWithBackButton(R.id.toolbar)
		setTitle("")

		val holder=new OrderProductHolder(find(R.id.topLayout), itemClick)
		holder.bind(0,order)

		find[View](R.id.callBtn).setOnClickListener(this)
	}

	override def onClick(view: View): Unit = {
		view.getId match {
			case R.id.callBtn ⇒
				try {
					val phone = preferences.phone
					val intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
					startActivity(intent)
				}catch {
					case _:Throwable ⇒
				}
			case _ ⇒
		}
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