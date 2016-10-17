package com.tytosoft.delivery

import android.app.WallpaperManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.View.OnClickListener
import android.view.{Menu, View}
import android.widget.ImageView
import com.kos.delivery.graphic.shapes.DialDrawable
import com.kos.delivery.net.DataStore._
import com.kos.fastuimodule.good.common._
import com.tytosoft.badgesapp.model.ProductModel
import com.tytosoft.delivery.common.helpers.ResHelper
import com.tytosoft.delivery.common.utils.BusActivity
import com.tytosoft.delivery.model.{AdsModel, ListModel}
import com.tytosoft.delivery.views.FABwithText

/**
  * Created by Kos on 17.07.2016.
  */
class NavActivity extends BusActivity{
	lazy val fab=find[FABwithText](R.id.fab)
	lazy val zakaz_bar_width=dimension(R.dimen.zakaz_board_width)

	lazy val viewMain: View = find(R.id.main)
	lazy val drawer: DrawerLayout = find(R.id.drawer_layout)
	def dialDrawable= //drawable(R.drawable.ic_cancel_zakaz_24dp)
		new DialDrawable(getApplicationContext, R.drawable.ring, preferences.dialStartTime,preferences.dialEndTime)


	var listId:ID=ListModel.NONE_ID

	protected override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setupActivity(savedInstanceState)

		drawer.setScrimColor(Color.TRANSPARENT)
		drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
			def onDrawerSlide(drawerView: View, slideOffset: Float) {
				translateMain(slideOffset)
			}

			def onDrawerOpened(drawerView: View) {
				translateMain(1)
			}

			def onDrawerClosed(drawerView: View) {
				translateMain(0)
			}

			def onDrawerStateChanged(newState: Int) {
			}
		})


		setupWallpaper()
	}

	def setupActivity(savedInstanceState: Bundle): Unit ={

	}

	def updateKorzina(): Unit ={

		val zakaz=lastKorzina.summa
		fab.show()
		if (zakaz==0)
			fab.setText(null)
		else
			fab.setText(zakaz.toString)
	}

	def setupWallpaper(): Unit ={
//		val wallpaperManager = WallpaperManager.getInstance(this)
//		val wallpaperDrawable = wallpaperManager.getFastDrawable
//		find[ImageView](R.id.backgroundImage).setImageDrawable(wallpaperDrawable)

	}



	override def onPostCreate(savedInstanceState: Bundle): Unit ={
		super.onPostCreate(savedInstanceState)
		translateMain(if (drawer.isDrawerOpen(GravityCompat.END)) 1 else 0)
	}

	def translateMain(slideOffset:Float){

		viewMain.setTranslationX(-slideOffset * zakaz_bar_width)
		viewMain.setScaleX(1 - (slideOffset * 0.25f))
		viewMain.setScaleY(1 - (slideOffset * 0.25f))
	}


	def application() = getApplication.asInstanceOf[App]

	def toggleZakazBar() ={
		if (drawer.isDrawerOpen(GravityCompat.END))
			drawer.closeDrawer(GravityCompat.END)
		else
			drawer.openDrawer(GravityCompat.END)
	}

	override def onBackPressed(): Unit = {
		if (drawer.isDrawerOpen(GravityCompat.END)){
			drawer.closeDrawers()
		}else {
			if (fragmentManager.getBackStackEntryCount>1)
				fragmentManager.popBackStack()
			else
				super.onBackPressed()
		}

	}

	override def onPrepareOptionsMenu(menu: Menu): Boolean ={
		val item=menu.findItem(R.id.actionCall)

		if (item!=null) {
			item.setIcon(dialDrawable)
			ResHelper.tintingMenu(menu, preferences.tintColor)
		}
		super.onPrepareOptionsMenu(menu)

	}

	override def onResume(): Unit = {
		super.onResume()
		updateCatalog()
		updateKorzina()
	}

	def updateCatalog() = {

	}

//==================================
	def isProduct(model: ProductModel)=true

	lazy val itemClick:OnClickListener= (view: View) => {
		view.getTag match {
			case ads: AdsModel ⇒
				if (!ads.isNull) {
					show(classOf[ProductActivity], ads.getProductId)
				}
			case event: ProductModel ⇒
				if (isProduct(event))
					show(classOf[ProductActivity],event.getId)
				else
					show(classOf[CatalogActivity],event.getId)
			case _ ⇒
		}

	}

	lazy val btnClick:OnClickListener= (view: View) => {
		view.getTag match {
			case ads: AdsModel ⇒
				lastKorzina.add(ads.getProductId, ads.getPricesCurrent, 1)
				saveData(lastKorzina)
				updateKorzina()
			case event: ProductModel ⇒
				//if (isProduct(event)){
					lastKorzina.add(event.getId, event.getPricesCurrent, 1)
					saveData(lastKorzina)
					updateKorzina()
				//}

			case _ ⇒
		}
	}
}
