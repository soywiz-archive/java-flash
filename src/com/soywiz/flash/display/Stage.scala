package com.soywiz.flash.display

import java.awt.Color

import com.soywiz.flash.backend.RenderContext
import com.soywiz.flash.backend.swing.SwingRenderContext

class Stage extends DisplayObjectContainer {
  /*
  override def render(context: RenderContext): Unit = {
    context.clear()
  }
  */
  var backgroundColor = Color.black

  override protected def renderInternal(context: RenderContext) = {
    context.clear(backgroundColor)
    super.renderInternal(context)
  }
}
