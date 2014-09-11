package com.soywiz.flash.backend

import java.io.{InputStream, File}

import android.hardware.usb.UsbInterface
import com.soywiz.flash.util.{Color, Point}
import org.apache.commons.io.IOUtils

object EngineContext {
  var instance: EngineContext = null
}

trait EngineContext {
  def clear(color: Color)

  def drawSolid(width: Int, height: Int, color: Color): Unit

  def drawImage(width: Int, height: Int, texture: Texture): Unit

  def openFile(path:String):InputStream

  def readFile(path:String):Array[Byte] = {
    val is = openFile(path)
    try {
      IOUtils.toByteArray(is)
    } finally {
      is.close()
    }
  }

  def createImageFromBytes(data: Array[Byte]): Texture

  def keep(callback: () => Unit): Unit

  def translate(x: Float, y: Float): Unit

  def scale(sx: Float, sy: Float): Unit

  def rotate(angle: Float): Unit

  def alpha(alpha: Float): Unit

  def loop(root: Component): Unit
}
