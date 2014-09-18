package com.soywiz.flash.display

import com.soywiz.flash.backend.{TextAlign, EngineContext}
import com.soywiz.flash.util.{Rectangle, Color}

class TextField extends DisplayObject {
  var text:String = "text"
  var fontFamily:String = "Arial"
  var width:Int = 100
  var height:Int = 100
  var size:Int = 20
  var color: Color = Color.red
  var align = TextAlign.Left

  override protected def renderInternal(context: EngineContext): Unit = {
    context.drawText(x, y, width, height, text, fontFamily, color, size, align)
  }

  override def getLocalUntransformedBounds: Rectangle = new Rectangle(0, 0, width, height)
}

