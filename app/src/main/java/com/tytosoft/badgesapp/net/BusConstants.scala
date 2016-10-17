package com.tytosoft.badgesapp.net

import com.kos.delivery.net.{OrderItemUpdate, SectionItemUpdate}
import com.kos.fastuimodule.good.common.bus.{EndLoadUpdate, StartLoadUpdate}
import com.kos.fastuimodule.good.common.{ID, IDSet}
import com.tytosoft.badgesapp.net.updaters.UpdateSystem
import com.tytosoft.delivery.net.{AdsItemUpdate, ListItemUpdate}


/**
  * Created by Kos on 10.03.2016.
  */
object BusConstants {
	lazy val clearKorzina = new KorzinaUpdater("Clear")

	def startLoad(infoType: String)(ids: IDSet) = new StartLoadUpdate(infoType, ids)
	def endLoad(infoType: String)(ids: IDSet) = new EndLoadUpdate(infoType, ids)

	def section(id: Seq[ID]) = new SectionItemUpdate(id)
	def product(id: Seq[ID]) = new ProductItemUpdate(id)
	def ads(id: Seq[ID]) = new AdsItemUpdate(id)
	def list(id: Seq[ID]) = new ListItemUpdate(id)
	def order(id: Seq[ID]) = new OrderItemUpdate(id)

	lazy val updateSystem = new UpdateSystem()

	lazy val updateZakaz = new UpdateZakaz("order")


}

case class UpdateZakaz(command: String)

case class KorzinaUpdater(command: String)

case class ProductItemUpdate(id: Seq[ID]) {

}



