package com.soywiz.flash.backend

object TouchEventType {
  val Move = new TouchEventType("move")
  val Enter = new TouchEventType("enter")
  val Out = new TouchEventType("out")
  val Click = new TouchEventType("click")
}

class TouchEventType(val kind:String)