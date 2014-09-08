package com.soywiz.flash.display

import com.soywiz.flash.backend.RenderContext

import scala.collection.mutable.ListBuffer

abstract class DisplayObjectContainer extends DisplayObject {
  protected var children:ListBuffer[DisplayObject] = ListBuffer[DisplayObject]()

  def addChild(child:DisplayObject): Unit = {
    children.append(child)
  }

  override protected def renderInternal(context: RenderContext): Unit = {
    for (child <- children) {
      child.render(context)
    }
  }
}
