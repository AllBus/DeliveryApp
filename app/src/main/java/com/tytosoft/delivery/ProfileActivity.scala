package com.tytosoft.delivery

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.{Menu, MenuItem, View}
import com.kos.fastuimodule.common.ui.U
import com.tytosoft.delivery.common.utils.SActivity
import com.tytosoft.delivery.net.DataStore

class ProfileActivity extends AppCompatActivity with SActivity
//	with OnClickListener
{

	import DataStore._

	protected override def onCreate(savedInstanceState: Bundle):Unit = {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_profile)

		val root=find[View](R.id.topLayout)

		U.text(root,R.id.profileName,lastProfile.name)
		U.text(root,R.id.profilePhone,lastProfile.phone)
		U.text(root,R.id.profileAddress,lastProfile.address)

	//	Seq(R.id.showAboutBtn).foreach(addClick(_,this))
		setupToolBarWithBackButton(R.id.toolbar)
	}

//	override def onClick(view: View): Unit = {
//		view.getId match {
//			case R.id.showAboutBtn ⇒
//				show(classOf[AboutActivity])
//			case _ ⇒
//		}
//	}

	override def onCreateOptionsMenu(menu: Menu): Boolean = {
		super.onCreateOptionsMenu(menu)
		getMenuInflater.inflate(R.menu.menu_profile, menu)
		true
	}

	override def onOptionsItemSelected(item: MenuItem): Boolean = {
		item.getItemId match {
			case android.R.id.home ⇒
				onBackPressed()
			case R.id.actionShowAbout ⇒
				show(classOf[AboutActivity])
			case _ ⇒
				return super.onOptionsItemSelected (item)
		}
		return true

	}
}