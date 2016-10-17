package com.tytosoft.delivery

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.tytosoft.badgesapp.common.utils.SActivity

class AboutActivity extends AppCompatActivity with SActivity {

	protected override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_about)

		setupToolBarWithBackButton(R.id.toolbar)

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