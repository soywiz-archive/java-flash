package com.soywiz.flash.display

import com.soywiz.flash.backend.EngineContext
import com.soywiz.flash.util.Color

class Stage(val context: EngineContext) extends DisplayObjectContainer {
  var backgroundColor = Color.black

  def loop(): Unit = {
    context.loop(this)
  }

  override protected def renderInternal(context: EngineContext) = {
    context.clear(backgroundColor)
    super.renderInternal(context)
  }
}
