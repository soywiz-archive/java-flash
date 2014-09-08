package com.soywiz.flash.display

import com.soywiz.flash.backend.{Renderizable, RenderContext}

abstract class DisplayObject extends Renderizable {
  var x = 0.0f
  var y = 0.0f
  var scaleX = 1.0f
  var scaleY = 1.0f
  var rotation = 0.0f

  def render(context:RenderContext): Unit = {
    context.keep(() => {
      context.scale(scaleX, scaleY)
      context.rotate(rotation)
      context.translate(x, y)
      renderInternal(context)
    })
  }

  protected def renderInternal(context:RenderContext):Unit = {

  }
}
