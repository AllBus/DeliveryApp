package com.tytosoft.delivery

import java.util

import android.animation.{ArgbEvaluator, ObjectAnimator}
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.{PagerAdapter, ViewPager}
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.{Editable, InputType}
import android.view.View.OnClickListener
import android.view._
import android.view.inputmethod.{EditorInfo, InputMethodManager}
import android.widget._
import com.kos.fastuimodule.common.ui.U
import com.squareup.otto.Subscribe
import com.tytosoft.delivery.common.listeners.SimpleTextWatcher
import com.tytosoft.delivery.common.utils.{BusActivity, SActivity, UU}
import com.tytosoft.delivery.net.{DataStore, Program, ProgramRun, UpdateZakaz}
import com.tytosoft.delivery.views.dots.{OnDotClickListener, ShaperIndicator}


object ActivityCreateZakaz {
	val SECTION_ID: Int = 1000

	private val SAVE_COMPLETE_VISIBLE = "completeVisible"
	val CREATE_ZAKAZ_CODE = 2000
}

class ActivityCreateZakaz extends BusActivity
	with SActivity
	with ViewPager.OnPageChangeListener
	with OnDotClickListener
	with OnClickListener {

	import ActivityCreateZakaz._
	import DataStore._

	private[this] lazy val mViewPager = find[ViewPager](R.id.pager)
	private[this] lazy val indicator = find[ShaperIndicator](R.id.indicator)
	private[this] lazy val mSectionsPagerAdapter = new SectionsPagerAdapter(this)
	private[this] lazy val nextBtn = find[Button](R.id.nextBtn)
	private[this] lazy val completeLayout = find[View](R.id.completeLayout)
	private[this] lazy val progressBar = find[View](R.id.progressBar)
	private[this] lazy val layoutResult = find[View](R.id.layoutResult)
	private[this] lazy val resultTitle: TextView = find(R.id.resultTitle)
	private[this] lazy val resultMessage: TextView = find(R.id.resultMessage)

	protected override def onCreate(savedInstanceState: Bundle):Unit = {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_create_zakaz)
		mViewPager.setAdapter(mSectionsPagerAdapter)
		mViewPager.addOnPageChangeListener(this)
		indicator.setNumberOfItems(mSectionsPagerAdapter.getCount)

		//		indicator.check(0,lastProfile.checkPhone)
		//		indicator.check(1,lastProfile.checkName)
		//		indicator.check(2,lastProfile.checkAddress)

		indicator.setDotClickListener(this)


		Seq(R.id.nextBtn, R.id.completeBtn).foreach(addClick(_, this))

		onPageSelected(0)
		setupToolBarWithBackButton(R.id.toolbar)

		getWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

		setTitle("")
	}


	override def onSaveInstanceState(outState: Bundle): Unit = {
		super.onSaveInstanceState(outState)
		outState.putBoolean(SAVE_COMPLETE_VISIBLE, completeLayout.getVisibility == View.VISIBLE)
	}


	override def onRestoreInstanceState(savedInstanceState: Bundle): Unit = {
		super.onRestoreInstanceState(savedInstanceState)
		U.gone(completeLayout, savedInstanceState.getBoolean(SAVE_COMPLETE_VISIBLE))

	}

	def restoreResult(): Unit = {


		UU.visible(progressBar, layoutResult, lastProfile.progressState == 1)
		if (Program.isZakazComplete(lastProfile.progressState)) {
			U.text(resultTitle, R.string.createZakazCompleteName)
			U.text(resultMessage, R.string.createZakazCompleteText)

			if (resultTitle != null) {
				val imm = getSystemService(Context.INPUT_METHOD_SERVICE).asInstanceOf[InputMethodManager]
				imm.hideSoftInputFromWindow(resultTitle.getWindowToken, 0)
			}
			if (Program.isZakazComplete(lastProfile.progressState))
				setResult(Activity.RESULT_OK, null)
		} else {
			U.text(resultTitle, R.string.createZakazErrorName)
			U.text(resultMessage, R.string.createZakazErrorText)
		}
	}

	override def onOptionsItemSelected(item: MenuItem): Boolean = {
		item.getItemId match {
			case android.R.id.home ⇒
				onBackPressed()
			case _ ⇒
				return super.onOptionsItemSelected(item)
		}
		true
	}

	override def onBackPressed(): Unit = {

		super.onBackPressed()
	}

	override def onStart(): Unit = {
		super.onStart()
		restoreResult()
	}

	override def onPause(): Unit = {
		saveData(lastProfile)

		super.onPause()
	}

	class SectionsPagerAdapter(context: Context) extends PagerAdapter {

		private[this] val items = new util.ArrayList[View]
		private[this] val inflater: LayoutInflater = LayoutInflater.from(context)
		private[this] val nextActionListener: TextView.OnEditorActionListener =
			new TextView.OnEditorActionListener {
				override def onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean = {
					nextPage()
					false
				}
			}

		private[this] val doneActionListener: TextView.OnEditorActionListener =
			new TextView.OnEditorActionListener {
				override def onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean = {

					false
				}
			}

		var i: Int = 0
		while (i < getCount) {
			{
				val rootView: View = inflater.inflate(R.layout.item_create_zakaz, null).asInstanceOf[ViewGroup]
				rootView.setId(i + SECTION_ID)

				val text: TextView = rootView.findViewById(R.id.name).asInstanceOf[TextView]
				val edit: EditText = rootView.findViewById(R.id.edit).asInstanceOf[EditText]
				val image: ImageView = rootView.findViewById(R.id.image).asInstanceOf[ImageView]

				if (i == getCount - 1) {
					edit.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI)
					edit.setOnEditorActionListener(doneActionListener)

				}
				else {
					edit.setImeOptions(EditorInfo.IME_ACTION_NEXT | EditorInfo.IME_FLAG_NO_EXTRACT_UI)
					edit.setOnEditorActionListener(nextActionListener)

				}

				i match {
					case 0 ⇒
						text.setText(R.string.createZakazPhoneName)
						edit.setHint(R.string.createZakazPhoneHint)
						image.setImageResource(R.drawable.tel)
						edit.setInputType(InputType.TYPE_CLASS_PHONE)
						edit.addTextChangedListener(new PhoneNumberFormattingTextWatcher())
						edit.addTextChangedListener(new SimpleTextWatcher {
							override def afterTextChanged(editable: Editable): Unit = {
								lastProfile.phone = editable.toString
								//	indicator.check(0,lastProfile.checkPhone)
							}
						})
						edit.setText(lastProfile.phone)

					case 1 ⇒
						text.setText(R.string.createZakazFioName)
						edit.setHint(R.string.createZakazFioHint)
						image.setImageResource(R.drawable.fio)
						edit.setInputType(InputType.TYPE_CLASS_TEXT)
						edit.addTextChangedListener(new SimpleTextWatcher {
							override def afterTextChanged(editable: Editable): Unit = {
								lastProfile.name = editable.toString
								//indicator.check(1,lastProfile.checkName)
							}
						})
						edit.setText(lastProfile.name)

					case 2 ⇒
						text.setText(R.string.createZakazAddressName)
						edit.setHint(R.string.createZakazAddressHint)
						image.setImageResource(R.drawable.geo)
						edit.setInputType(InputType.TYPE_CLASS_TEXT)
						edit.addTextChangedListener(new SimpleTextWatcher {
							override def afterTextChanged(editable: Editable): Unit = {
								lastProfile.address = editable.toString
								//	indicator.check(2,lastProfile.checkAddress)
								checkCompleteBtn()
							}
						})
						edit.setText(lastProfile.address)
					case _ ⇒
				}
				items.add(rootView)
			}

			i += 1
		}


		override def instantiateItem(container: ViewGroup, position: Int): AnyRef = {
			val rootView: View = items.get(position)
			container.addView(rootView)
			rootView
		}

		private def nextItem:Unit = {
		}

		def starData:Unit = {
		}

		override def destroyItem(container: ViewGroup, position: Int, `object`: AnyRef):Unit = {
			container.removeView(`object`.asInstanceOf[View])
		}

		def getCount: Int = {
			3
		}

		def isViewFromObject(view: View, `object`: AnyRef): Boolean = {
			view == `object`
		}

		override def getPageTitle(position: Int): CharSequence = {
			""
		}
	}

	def onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int):Unit = {
	}

	def onPageSelected(position: Int):Unit = {
		indicator.setSelectedItem(position, true)
		for (i ← 0 until 2) {
			indicator.check(i, i < position)
		}


		val v: View = mViewPager.findViewById(position + SECTION_ID)
		if (v != null) {
			v.findViewById[View](R.id.edit).requestFocus
		}

		if (position == mSectionsPagerAdapter.getCount() - 1) {
			val colorAnim: ObjectAnimator = ObjectAnimator.ofInt(nextBtn, "textColor", Color.TRANSPARENT, 0xFF7BAE37)
			colorAnim.setDuration(500)
			colorAnim.setEvaluator(new ArgbEvaluator)
			colorAnim.start()
			nextBtn.setText(R.string.createZakazNextBtnDone)
			checkCompleteBtn()
		}
		else {
			val colorAnim: ObjectAnimator = ObjectAnimator.ofInt(nextBtn, "textColor", Color.TRANSPARENT, 0xFF7BAE37)
			colorAnim.setDuration(500)
			colorAnim.setEvaluator(new ArgbEvaluator)
			colorAnim.start()
			nextBtn.setText(R.string.createZakazNextBtnNext)
			nextBtn.setEnabled(true)
		}

	}

	def onPageScrollStateChanged(state: Int): Unit = {

	}

	def onDotClick(index: Int):Unit = {
		mViewPager.setCurrentItem(index)
	}

	override def onClick(view: View): Unit = {
		view.getId match {
			case R.id.nextBtn ⇒

				if (nextPage()) {
					completeLayout.setVisibility(View.VISIBLE)
					lastProfile.progressState = 1
					//		getWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

					restoreResult()

					ProgramRun.addOrder(lastProfile, lastKorzina)
				}

			case R.id.completeBtn ⇒
				if (Program.isZakazComplete(lastProfile.progressState))
					setResult(Activity.RESULT_OK, null)
				finish()
			case _ ⇒
		}
	}

	def nextPage() = {
		val newIndex: Int = mViewPager.getCurrentItem + 1
		if (newIndex >= 0 && newIndex < mSectionsPagerAdapter.getCount) {
			mViewPager.setCurrentItem(newIndex)
		}
		newIndex == mSectionsPagerAdapter.getCount
	}

	def checkCompleteBtn(): Unit = {
		nextBtn.setEnabled(lastProfile.completeAll)
	}

	@Subscribe
	def zakazStateUpdate(updater: UpdateZakaz): Unit = {
		restoreResult()
	}


}