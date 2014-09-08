package com.soywiz.flash.display

import java.awt.Color

import com.soywiz.flash.backend.RenderContext

class Quad extends DisplayObject {
  var width:Int = 100
  var height:Int = 100
  var color:Color = Color.red

  override protected def renderInternal(context: RenderContext): Unit = {
    context.keep(() => {
      super.renderInternal(context)
      context.drawSolid(width, height, color)
    })
  }
}
