package com.tytosoft.badgesapp.common.utils

import android.app.Activity
import android.content.{DialogInterface, Intent}
import android.support.annotation.{DimenRes, DrawableRes, IdRes}
import android.support.design.widget.Snackbar
import android.support.v7.app.{AlertDialog, AppCompatActivity}
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.kos.fastuimodule.good.common.ID

/**
  * Created by Kos on 27.06.2016.
  */
object SActivity{
	val KEY_ID = "ID"
	val NONE_ID= -2
}
trait SActivity {

	self: AppCompatActivity =>





	@inline def find[T](@IdRes id: Int) = findViewById(id).asInstanceOf[T]

	@inline def snack(view: View, text: CharSequence) = Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()

	@inline def toast(text: CharSequence) = Toast.makeText(getApplicationContext,text, Toast.LENGTH_SHORT).show()

	def drawable(@DrawableRes id: Int) =
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			getDrawable(id)

		}
		else {
			getResources.getDrawable(id)

		}

	def dimension(@DimenRes id: Int) =	getResources.getDimension(id)

	def addClick(@IdRes viewId:Int,clickListener: OnClickListener): Unit ={
		findViewById(viewId) match{
			case v:View ⇒ v.setOnClickListener(clickListener)
			case _ ⇒
		}
	}

	def fragmentManager = getSupportFragmentManager

	def show(activityClass: Class[_]) = startActivity(new Intent(this,activityClass))

	def showForResult(activityClass: Class[_],code:Int) = startActivityForResult(new Intent(this,activityClass),code)

	def show(activityClass: Class[_],id:ID) ={

		val intent=new Intent(this,activityClass)
		intent.putExtra(SActivity.KEY_ID,id)
		startActivity(intent)
	}

	def setupToolBar(@IdRes toolbarId:Int): Unit ={
		val toolbar = findViewById(toolbarId).asInstanceOf[Toolbar]
		setSupportActionBar(toolbar)
	}

	def setupToolBarWithBackButton(@IdRes toolbarId:Int): Unit ={
		setupToolBar(toolbarId)

		val actionBar=getSupportActionBar
		if (actionBar!=null){
			actionBar.setDisplayHomeAsUpEnabled(true)

		}
	}

	def getID: ID ={
		val intent=getIntent
		if (intent!=null) {
			intent.getLongExtra(SActivity.KEY_ID,SActivity.NONE_ID)
		}else{
			SActivity.NONE_ID
		}
	}

	def alertYesNo(resTitle: Int, resInfo: Int, yesOperator: () => Unit, noOperator: () => Unit ): Unit = {

		val alertDialog: AlertDialog = new AlertDialog.Builder(this).create
		alertDialog.setTitle(resTitle)
		alertDialog.setMessage(getString(resInfo))
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(android.R.string.yes),new DialogInterface
		.OnClickListener {
			override def onClick(dialog: DialogInterface, which: Int): Unit = {
				yesOperator()
			}
		} )
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.no),new DialogInterface
		.OnClickListener{
			override def onClick(dialog: DialogInterface, which: Int): Unit = {
				noOperator()
			}
		})

		alertDialog.show

		//		val dialog= new DialogFragment{
		//			override def onCreateDialog(savedInstanceState:Bundle):Dialog = {
		//				// Use the Builder class for convenient dialog construction
		//				val builder = new AlertDialog.Builder(getActivity())
		//				builder.setTitle(resTitle)
		//				builder.setMessage(getString(resInfo))
		//					.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		//						override def onClick(dialog:DialogInterface, id:Int) {
		//							yesOperator()
		//						}
		//					})
		//					.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		//						override def onClick(dialog :DialogInterface, id: Int) {
		//							noOperator()
		//						}
		//					})
		//				// Create the AlertDialog object and return it
		//				builder.create()
		//			}
		//		}
		//		dialog.

	}

}


