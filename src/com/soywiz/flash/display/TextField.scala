package com.soywiz.flash.display

import com.soywiz.flash.backend.EngineContext
import com.soywiz.flash.util.{Rectangle, Color}

class TextField extends DisplayObject {
  var text:String = "text"
  var width:Int = 100
  var height:Int = 100
  var color: Color = Color.red

  override protected def renderInternal(context: EngineContext): Unit = {
    context.drawText(x, y, text, color)
  }

  override def getLocalUntransformedBounds: Rectangle = new Rectangle(0, 0, width, height)
}
