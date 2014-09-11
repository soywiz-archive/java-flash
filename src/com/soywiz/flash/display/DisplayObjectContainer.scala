package com.soywiz.flash.display

import com.soywiz.flash.backend.EngineContext
import com.soywiz.flash.util.{Point, Rectangle}

import scala.collection.mutable.ListBuffer

abstract class DisplayObjectContainer extends DisplayObject {
  protected var children:ListBuffer[DisplayObject] = ListBuffer[DisplayObject]()

  def numChildren() = children.length

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

  def removeChildren(): Unit = {
    children.clear()
  }

  protected def transformChild(child:DisplayObject) = child

  def getChildAt(index:Int) = transformChild(children(index))

  def getChildByName(name:String) = transformChild(children.find(item => item.name == name).get)

  override def getLocalUntransformedBounds = {
    Rectangle.bounds(children.map(child => child.getLocalUntransformedBounds).toList)
  }

  override def update(dt: Int): Unit = {
    super.update(dt)
    for (child <- children) child.update(dt)
  }

  override def touchUpdate(point: Point, kind: Int) = {
    super.touchUpdate(point, kind)
    for (child <- children) child.touchUpdate(point, kind)
  }

  override protected def renderInternal(context: EngineContext): Unit = {
    for (child <- children) child.render(context)
  }
}
