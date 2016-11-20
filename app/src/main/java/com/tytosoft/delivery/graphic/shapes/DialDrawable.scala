package com.tytosoft.delivery.graphic.shapes

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff.Mode
import android.graphics._
import android.graphics.drawable.Drawable
import android.support.annotation.{ColorInt, DrawableRes}
import android.support.v4.graphics.drawable.TintAwareDrawable

/**
  * Created by Kos on 07.07.2016.
  */
class DialDrawable(val context:Context,@DrawableRes  val drawableId: Int,var topText: String,var bottomText: String)
	extends Drawable with TintAwareDrawable{

	def setText(topText: String, bottomText: String): Unit =
	{
		this.topText=topText
		this.bottomText=bottomText
	}


	val superDrawable=drawable(drawableId)

	def drawable(@DrawableRes id: Int) =
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			context.getDrawable(id)

		}
		else {
			context.getResources.getDrawable(id)

		}

	val dp=context.getResources.getDisplayMetrics.density
	val pen=new Paint()

	pen.setTextSize(8*dp)
	pen.setColor(Color.BLACK)

	val topTextWidth=pen.measureText(topText)
	val bottomTextWidth=pen.measureText(bottomText)
	val maxTextLength=Math.max(topTextWidth,bottomTextWidth)

	override def draw(canvas: Canvas): Unit = {
		superDrawable.setBounds(0,0,getIntrinsicWidth,getIntrinsicHeight)
		superDrawable.draw(canvas)
		val bounds=canvas.getClipBounds
		val x=bounds.right
		val y=bounds.centerY()

		canvas.drawText(topText,x-maxTextLength,y,pen)
		canvas.drawText(bottomText,x-maxTextLength,y+pen.getTextSize,pen)
	}

	override def setColorFilter(colorFilter: ColorFilter): Unit = {
		superDrawable.setColorFilter(colorFilter)
		pen.setColorFilter(colorFilter)
	}


	override def setColorFilter(color: Int, mode: Mode): Unit = {
		val filter=new PorterDuffColorFilter(color, mode)
		superDrawable.setColorFilter(filter)
		pen.setColorFilter(filter)
	}

	override def setAlpha(alpha: Int): Unit = {
		superDrawable.setAlpha(alpha)
		pen.setAlpha(alpha)
	}

	override def getOpacity: Int = {
		superDrawable.getOpacity
	}

	override def getIntrinsicWidth: Int = superDrawable.getIntrinsicWidth

	override def getIntrinsicHeight: Int = superDrawable.getIntrinsicHeight

	override def setTint(@ColorInt tint: Int): Unit ={
		val filter=new PorterDuffColorFilter(tint, Mode.SRC_IN)
		superDrawable.setColorFilter(filter)
		pen.setColorFilter(filter)
	}

	override def setTintList(tint: ColorStateList): Unit ={

	}

	override def setTintMode(tintMode: PorterDuff.Mode): Unit ={

	}
}
