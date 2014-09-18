package com.soywiz.flash.backend

object TextAlign {
  val Left = new TextAlign(0.0)
  val Center = new TextAlign(0.5)
  val Right = new TextAlign(1.0)
  def apply(name:String): TextAlign = {
    name match {
      case "left" => Left
      case "center" => Center
      case "right" => Right
    }
  }
}

class TextAlign(val ratio:Double)