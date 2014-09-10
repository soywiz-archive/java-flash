package com.soywiz.flash.display

import com.soywiz.flash.backend.{Component, EngineContext}

abstract class DisplayObject extends Component {
  var x = 0.0f
  var y = 0.0f
  var scaleX = 1.0f
  var scaleY = 1.0f
  var rotation = 0.0f
  var alpha = 1.0f
  var visible = true
  var parent: DisplayObjectContainer = null
  var name:String = null

  def render(context: EngineContext): Unit = {
    if (!visible) return

    context.keep(() => {
      context.translate(x, y)
      context.rotate(rotation)
      context.scale(scaleX, scaleY)
      context.alpha(alpha)
      renderInternal(context)
    })
  }

  override def update(dt: Int): Unit = {

  }

  protected def renderInternal(context: EngineContext): Unit = {

  }
}
