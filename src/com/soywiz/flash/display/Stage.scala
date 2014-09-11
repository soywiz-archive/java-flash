package com.soywiz.flash.display

import java.awt.Color

import com.soywiz.flash.backend.EngineContext
import com.soywiz.flash.backend.swing.SwingEngineContext

class Stage(val context:EngineContext) extends DisplayObjectContainer {
  var backgroundColor = Color.black

  def loop(): Unit = {
    context.loop(this)
  }

  def mousePosition = context.mousePosition

  override protected def renderInternal(context: EngineContext) = {
    context.clear(backgroundColor)
    super.renderInternal(context)
  }
}
