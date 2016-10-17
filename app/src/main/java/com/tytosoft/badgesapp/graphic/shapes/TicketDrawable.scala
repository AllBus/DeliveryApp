package com.tytosoft.badgesapp.graphic.shapes

import android.graphics.drawable.Drawable
import android.graphics.{Canvas, Color, ColorFilter, Paint}

/**
  * Created by Kos on 07.07.2016.
  */
class TicketDrawable(val triangleCount :Int,val triangleHeight:Float) extends Drawable{

	private[this] var triangleWidth:Float=1f

	private[this] val pen=new Paint()
	private[this] val path=new android.graphics.Path()

	private[this] var lastWidth=0
	private[this] var lastHeight=0

	pen.setColor(Color.WHITE)
	pen.setStyle(Paint.Style.FILL)

	private[this] def resizePath(newWidth: Int, newHeight: Int): Unit={
		if (newWidth==lastWidth && newHeight==lastHeight)
			return

		lastWidth=newWidth
		lastHeight=newHeight

		path.reset()
		path.moveTo(0,0)
		path.rLineTo(0,lastHeight-triangleHeight)

		triangleWidth=lastWidth*1.0f/(triangleCount*2)
		var i=0
		while (i<triangleCount) {
			path.rLineTo(triangleWidth, triangleHeight)
			path.rLineTo(triangleWidth, -triangleHeight)
			i+=1
		}
		path.rLineTo(0,-(lastHeight-triangleHeight))
		path.close()
	}

	override def draw(canvas: Canvas): Unit = {
		val bounds=canvas.getClipBounds
		val x=bounds.right
		val y=bounds.bottom

		resizePath(bounds.width(),bounds.height())

		canvas.drawPath(path,pen)
	}


	override def setColorFilter(colorFilter: ColorFilter): Unit = {

		pen.setColorFilter(colorFilter)
	}

	override def setAlpha(alpha: Int): Unit = {

		pen.setAlpha(alpha)
	}

	override def getOpacity: Int = {
		pen.getAlpha
	}

	override def getIntrinsicWidth: Int = (triangleWidth*2).toInt

	override def getIntrinsicHeight: Int = (triangleHeight).toInt
}
