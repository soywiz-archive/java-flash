package com.soywiz.flash.display

import com.soywiz.flash.backend.{Texture, RenderContext}

class Image(texture:Texture) extends DisplayObject {
  override protected def renderInternal(context: RenderContext) = {
    //context.drawSolid()
    //context.drawQuad();
  }
}
