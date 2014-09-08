package com.soywiz.flash.backend

class Texture(val base:TextureBase, val x:Int, val y:Int, val width:Int, val height:Int) {
  def slice(x:Int, y:Int, width:Int, height:Int): Texture = {
    new Texture(base, this.x + x, this.y + y, width, height)
  }
}
