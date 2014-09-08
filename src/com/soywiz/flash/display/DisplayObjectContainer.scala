package com.soywiz.flash.display

import com.soywiz.flash.backend.EngineContext

import scala.collection.mutable.ListBuffer

abstract class DisplayObjectContainer extends DisplayObject {
  protected var children:ListBuffer[DisplayObject] = ListBuffer[DisplayObject]()

  def addChild(child:DisplayObject): Unit = {
    if (child.parent != null) child.parent.removeChild(child)
    children.append(child)
    child.parent = this
  }

  def removeChild(child:DisplayObject): Unit = {
    if (child.parent != this) return
    val index = children.indexOf(child)
    if (index >= 0) children.remove(index)
  }


  override def update(dt: Int): Unit = {
    super.update(dt)
    for (child <- children) child.update(dt)
  }

  override protected def renderInternal(context: EngineContext): Unit = {
    for (child <- children) child.render(context)
  }
}
