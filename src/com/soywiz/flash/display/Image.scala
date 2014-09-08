package com.soywiz.flash.display

import com.soywiz.flash.backend.{EngineContext, Texture}

class Image(texture: Texture) extends DisplayObject {
  override protected def renderInternal(context: EngineContext) = {
    context.drawImage(texture.width, texture.height, texture)
    //context.drawSolid()
    //context.drawQuad();
  }
}
