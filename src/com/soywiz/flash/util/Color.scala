package com.soywiz.flash.util

object Color {
  lazy val black = new Color(0, 0, 0, 255)
  lazy val red = new Color(255, 0, 0, 255)
  def apply(v:Int) = new Color(
    (v >>> 0) & 0xFF,
    (v >>> 8) & 0xFF,
    (v >>> 16) & 0xFF,
    (v >>> 24) & 0xFF
  )
}

class Color(val r:Int, val g:Int, val b:Int, val a:Int) {
  def toInt: Int = (a << 24) | (r << 16) | (b << 8) | (g << 0)
}
