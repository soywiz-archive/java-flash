package com.soywiz.flash.display

import java.awt.Color

import com.soywiz.flash.backend.EngineContext
import com.soywiz.flash.backend.swing.SwingEngineContext

class Stage extends DisplayObjectContainer {
  /*
  override def render(context: RenderContext): Unit = {
    context.clear()
  }
  */
  var backgroundColor = Color.black

  override protected def renderInternal(context: EngineContext) = {
    context.clear(backgroundColor)
    super.renderInternal(context)
  }
}
