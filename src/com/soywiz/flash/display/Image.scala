package com.soywiz.flash.display

import com.soywiz.flash.backend.{EngineContext, Texture}
import com.soywiz.flash.util.Rectangle

class Image(texture: Texture) extends DisplayObject {
  override protected def renderInternal(context: EngineContext) = {
    context.drawImage(texture.width, texture.height, texture)
  }

  override def getLocalUntransformedBounds: Rectangle = new Rectangle(0, 0, texture.width, texture.height)
}
