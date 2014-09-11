package com.soywiz.flash.display

import java.awt.Color

import com.soywiz.flash.backend.EngineContext
import com.soywiz.flash.util.Rectangle

class Quad extends DisplayObject {
  var width:Int = 100
  var height:Int = 100
  var color:Color = Color.red

  override protected def renderInternal(context: EngineContext): Unit = {
    context.drawSolid(width, height, color)
  }

  override def getLocalUntransformedBounds: Rectangle = new Rectangle(0, 0, width, height)
}
