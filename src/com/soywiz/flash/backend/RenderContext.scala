package com.soywiz.flash.backend

import java.awt.Color

trait RenderContext {
  def clear(color:Color)
  def drawSolid(width:Int, height:Int, color:Color): Unit
  def keep(callback: () => Unit): Unit
  def translate(x:Float, y:Float): Unit
  def scale(sx:Float, sy:Float): Unit
  def rotate(angle:Float): Unit
}
