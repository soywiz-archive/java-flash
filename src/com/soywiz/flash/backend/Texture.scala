package com.soywiz.flash.backend

class Texture(val base:TextureBase, val x:Int, val y:Int, val width:Int, val height:Int) {
  def this(base:TextureBase) {
    this(base, 0, 0, base.width, base.height)
  }

  def slice(x:Int, y:Int, width:Int, height:Int): Texture = {
    // @TODO: Check limits
    new Texture(base, this.x + x, this.y + y, width, height)
  }

  override def toString: String = s"Texture(($x,$y)-($width-$height))"
}
