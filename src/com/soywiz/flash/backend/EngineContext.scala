package com.soywiz.flash.backend

import java.awt.Color
import java.io.File

import com.soywiz.flash.util.Point

object EngineContext {
  var instance:EngineContext = null
}

trait EngineContext {
  EngineContext.instance = this

  def clear(color:Color)
  def drawSolid(width:Int, height:Int, color:Color): Unit
  def drawImage(width: Int, height: Int, texture: Texture): Unit
  def createImageFromBytes(data:Array[Byte]): Texture
  def createImageFromFile(file:File): Texture
  def keep(callback: () => Unit): Unit
  def translate(x:Float, y:Float): Unit
  def scale(sx:Float, sy:Float): Unit
  def rotate(angle:Float): Unit
  def alpha(alpha: Float): Unit
  def loop(root:Component): Unit
  def mousePosition: Point
}
