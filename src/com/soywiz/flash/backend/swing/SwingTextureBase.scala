package com.soywiz.flash.backend.swing

import java.awt.image.BufferedImage

import com.soywiz.flash.backend.TextureBase

class SwingTextureBase(val image:BufferedImage) extends TextureBase {
  override val width: Int = image.getWidth
  override val height: Int = image.getHeight
}