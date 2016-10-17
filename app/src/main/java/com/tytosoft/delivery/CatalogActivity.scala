package com.tytosoft.delivery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.View.OnClickListener
import android.view.{Menu, MenuItem, View}
import android.widget.ProgressBar
import com.kos.delivery.net._
import com.squareup.otto.Subscribe
import com.tytosoft.badgesapp.model.ProductModel
import com.tytosoft.badgesapp.net.updaters.UpdateSystem
import com.tytosoft.badgesapp.net.{ProductItemUpdate, Program}
import com.tytosoft.delivery.adapters.SimpleAdapter
import com.tytosoft.delivery.adapters.holders.FullProductHolder
import com.tytosoft.delivery.common.helpers.ResHelper
import com.tytosoft.delivery.net.ListItemUpdate

class CatalogActivity extends NavActivity with OnClickListener{

	import DataStore._
//	private[this] lazy val progressBar:ProgressBar=find(R.id.progressBar)
	protected override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
	}

	lazy val adapter= new SimpleAdapter(
		this,
		R.layout.item_main_full,
		R.layout.item_main_list,

		new FullProductHolder(_,btnClick,itemClick	)
	)

	lazy val thisListId=getID

	override def setupActivity(savedInstanceState: Bundle) {
		setContentView(R.layout.activity_main)

		Seq(R.id.fab).foreach(addClick(_,this))

		val product=sections.get(thisListId)
		listId=getListFor(product)
		loadFirstItems(listId)

		setTitle(product.getName)

		val list=find[RecyclerView](R.id.list)
		list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
		list.setAdapter(adapter)

		find[ProgressBar](R.id.progressBar).setVisibility(View.GONE)

		setupToolBarWithBackButton(R.id.toolbar)
	}

	override def onCreateOptionsMenu(menu: Menu): Boolean = {
		super.onCreateOptionsMenu(menu)
		getMenuInflater.inflate(R.menu.main_menu, menu)

		val item=menu.findItem(R.id.actionCall)
		item.setIcon(dialDrawable)
		ResHelper.tintingMenu(menu,preferences.tintColor)
		true
	}

	override def onOptionsItemSelected(item: MenuItem): Boolean = {
		item.getItemId match {
			case R.id.`actionCall` ⇒
				val phone = preferences.phone
				val intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
				startActivity(intent);
			case R.id.`actionProfile` ⇒
				show(classOf[ProfileActivity])
			case R.id.`actionZakaz` ⇒
					updateKorzina()
					toggleZakazBar()
			case android.R.id.home ⇒
				onBackPressed()
			case _ ⇒
		 	return super.onOptionsItemSelected (item)
		}
		true
	}

	override def onClick(view: View): Unit = view.getId match{
			case R.id.fab ⇒
				show(classOf[KorzinaActivity])
			case _ ⇒
		}

	@Subscribe
	def updateSystem(updateSystem: UpdateSystem): Unit ={
		updateCatalog()
		setTitle(preferences.companyName)
		invalidateOptionsMenu()
	}

	@Subscribe
	def updateList(updater:ListItemUpdate): Unit ={
		if (updater.id.contains(listId)){
			loadFirstItems(listId)
			updateCatalog()
		}
	}

	@Subscribe
	def updateSection(updater: SectionItemUpdate): Unit = {
		adapter.updateItems(updater.id)
	}

	@Subscribe
	def updateProduct(updater:ProductItemUpdate): Unit ={
		adapter.updateItems(updater.id)
	}

	override def updateCatalog(): Unit = {
		adapter.changeList(lists.get(listId))
	}

	override def isProduct(model: ProductModel)={
		adapter.getList.getKind match{
			case Program.LIST_SECTION ⇒ false
			case _ ⇒ true
		}
	}
}