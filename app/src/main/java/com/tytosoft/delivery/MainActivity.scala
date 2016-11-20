package com.tytosoft.delivery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.View.OnClickListener
import android.view.{Menu, MenuItem, View}
import android.widget.ProgressBar
import com.kos.delivery.common.utils.LogD
import com.kos.delivery.net._
import com.kos.fastuimodule.good.common.ID
import com.kos.fastuimodule.good.common.bus.EndLoadUpdate
import com.squareup.otto.Subscribe
import com.tytosoft.badgesapp.model.ProductModel
import com.tytosoft.badgesapp.net.updaters.UpdateSystem
import com.tytosoft.badgesapp.net.{ProductItemUpdate, Program, ProgramRun}
import com.tytosoft.delivery.adapters.HeaderSimpleAdapter
import com.tytosoft.delivery.adapters.holders.{AdsItemHolder, SimpleHolder}
import com.tytosoft.delivery.common.helpers.ResHelper
import com.tytosoft.delivery.model.ListModel
import com.tytosoft.delivery.net.{AdsItemUpdate, ListItemUpdate}


class MainActivity extends NavActivity with OnClickListener {

	import DataStore._

	var adsId: ID = ListModel.NONE_ID
	lazy val list = find[RecyclerView](R.id.list)

	private[this] lazy val progressBar: ProgressBar = find(R.id.progressBar)

	lazy val adapter = new HeaderSimpleAdapter(
		this,
		R.layout.item_main_full,
		R.layout.item_main_list,
		R.layout.layout_ads,
		new SimpleHolder(_, itemClick),
		new AdsItemHolder(_, itemClick, btnClick)
	)

	override def setupActivity(savedInstanceState: Bundle): Unit = {
		setContentView(R.layout.activity_main)
		reloadList()

		Seq(R.id.fab).foreach(addClick(_, this))

		listId = getListTop
		adsId = getListAds

		list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
		list.setAdapter(adapter)

		setupToolBar(R.id.toolbar)
		setTitle(preferences.companyName)

		//		if (fragmentManager.getBackStackEntryCount==0)
		//			showTopProduct()
	}


	override def onCreateOptionsMenu(menu: Menu): Boolean = {
		super.onCreateOptionsMenu(menu)
		getMenuInflater.inflate(R.menu.main_menu, menu)

		val item = menu.findItem(R.id.actionCall)

		item.setIcon(dialDrawable)

		ResHelper.tintingMenu(menu, preferences.tintColor)
		true
	}


	override def onOptionsItemSelected(item: MenuItem): Boolean = {

		item.getItemId match {
			case R.id.`actionCall` ⇒
				try {
					val phone = preferences.phone
					val intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
					startActivity(intent);
				} catch {
					case _: Throwable ⇒
				}
			case R.id.`actionProfile` ⇒
				show(classOf[ProfileActivity])

			//				application().badgeCount += 1
			//				val success = ShortcutBadger.applyCount(this, application().badgeCount)


			//				case R.id.tashMenu ⇒
			//					application().badgeCount-=1
			//					ShortcutBadger.applyCount(this,application().badgeCount)
			case R.id.`actionZakaz` ⇒
				//				ShortcutBadger.removeCount(this);
				//				application().zakazSize = 0

				updateKorzina()
				toggleZakazBar()


			case _ ⇒
				return super.onOptionsItemSelected(item)
		}
		true
	}

	override def onClick(view: View): Unit = {
		view.getId match {
			case R.id.fab ⇒
				show(classOf[KorzinaActivity])
			//snack(find(R.id.list),"soobshenie add to korzina")
			//			case R.id.addCounterButton ⇒ application().badgeCount+=1
			//
			//				val success =ShortcutBadger.applyCount(this,application().badgeCount)
			//
			//				toast( "Set count=" + application().badgeCount + ", success=" + success)
			//			case R.id.subtCounterButton ⇒
			//				application().badgeCount-=1
			//				ShortcutBadger.applyCount(this,application().badgeCount)
			//			case R.id.clearCounterButton ⇒ ShortcutBadger.removeCount(this);
			case _ ⇒
		}
	}


	@Subscribe
	def updateSystem(updateSystem: UpdateSystem): Unit = {
		updateCatalog()
		setTitle(preferences.companyName)
		invalidateOptionsMenu()
	}


	@Subscribe
	def updateList(updater: ListItemUpdate): Unit = {
		//LogD("Update list "+updater.id)
		listId = getListTop
		adsId = getListAds

		if (updater.id.contains(listId)) {
			loadFirstItems(listId)
			updateCatalog()
		}
		if (updater.id.contains(adsId)) {
			loadFirstItems(adsId)
			adapter.updateHeader(lists.get(adsId))
			adapter.notifyHeader()
		}
		updateProgress()
	}


	@Subscribe
	def endLoad(updater: EndLoadUpdate): Unit = {
		if (updater.infoType == Program.API_LIST) {
			updateProgress()
		}
		if (updater.infoType == Program.API_ADS) {
			//	LogD("Update ads")
			adapter.notifyHeader()
		}

	}

	override def onStart(): Unit = {
		super.onStart()
		listId = getListTop
		adsId = getListAds
		updateCatalog()
	}

	@Subscribe
	def updateAds(updater: AdsItemUpdate): Unit = {
		adapter.updateHeader(lists.get(adsId))
		adapter.notifyHeader()

	}

	@Subscribe
	def updateProduct(updater: ProductItemUpdate): Unit = {
		adapter.updateItems(updater.id)
	}

	@Subscribe
	def updateSection(updater: SectionItemUpdate): Unit = {
		adapter.updateItems(updater.id)
	}

	def updateProgress() = {

		if (adapter.getList.isNull) {
			if (lists.isLoading(-1)) {
				progressBar.setVisibility(View.VISIBLE)
			} else {
				progressBar.setVisibility(View.GONE)
				val snackBar = Snackbar.make(list, R.string.snackNoInternet, Snackbar.LENGTH_INDEFINITE)
				snackBar.setAction(R.string.snackNoInternetRetry,
					new OnClickListener {
						override def onClick(view: View): Unit = {
							reloadList()
							snackBar.dismiss()
						}
					}
				).show()

			}

		} else {
			progressBar.setVisibility(View.GONE)
		}
	}

	override def updateCatalog(): Unit = {
		adapter.changeList(lists.get(listId))
		adapter.updateHeader(lists.get(adsId))

		updateProgress()


	}

	def reloadList(): Unit = {
		ProgramRun.getSystem()
		ProgramRun.getLists()
	}

	override def isProduct(model: ProductModel) = {
		adapter.getList.getKind match {
			case Program.LIST_SECTION ⇒ false
			case _ ⇒ true
		}

	}

	//	def changeFragment(newFragment: Fragment, tag: String,clearStack:Boolean): Unit ={
	//		if (newFragment != null) {
	//			if (clearStack)
	//				getFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
	//
	//			val transaction = fragmentManager.beginTransaction
	//
	//			transaction.replace(R.id.container, newFragment, tag).
	//				addToBackStack(null).
	//				commit
	//		}
	//	}
	//
	//	def showTopProduct(): Unit ={
	//		changeFragment(CatalogFragment(0),"top",true)
	//	}
}