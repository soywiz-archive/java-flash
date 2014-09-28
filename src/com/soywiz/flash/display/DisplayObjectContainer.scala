package com.soywiz.flash.display

import com.soywiz.flash.backend.{TouchEventType, EngineContext}
import com.soywiz.flash.util.{Point, Rectangle}

import scala.collection.mutable.ListBuffer

abstract class DisplayObjectContainer extends DisplayObject {
  private[display] var first:DisplayObject = null
  private[display] var last:DisplayObject = null
  private var _numChildren:Int = 0
  //private var children:Array[DisplayObject] = new Array[DisplayObject](0)

  def numChildren() = _numChildren

  private def _addChildNode(child:DisplayObject):Unit = {
    if (child == null) throw new Exception("Invalid child")
    if (child == this) throw new Exception("Can't add to itself")
    if (child.parent != null) child.parent.removeChild(child)

    child.parent = this
    if (last == null) {
      last = child
      first = child
      child.prev = null
      child.next = null
    } else {
      child.next = null
      child.prev = last
      last.next = child
      last = child
    }
    _numChildren += 1

    //if (_childsByName) _childsByName[child.name] = child
  }

  private def _removeChildNode(child:DisplayObject):Unit = {
    if (child == null) throw new Exception("Invalid child")
    if (child.parent == null) throw new Exception("Container doesn't contain child")
    if (child.parent != this) throw new Exception("Container doesn't contain child")

    //if (_childsByName) _childsByName[child.name] = undefined;

    if (child == first) first = child.next
    if (child == last) last = child.prev
    if (child.prev != null) child.prev.next = child.next
    if (child.next != null) child.next.prev = child.prev

    child.parent._numChildren -= 1
    child.parent = null
    child.prev = null
    child.next = null
  }

  def addChild(child:DisplayObject): Unit = {
    _addChildNode(child)
  }

  def removeChild(child:DisplayObject): Unit = {
    _removeChildNode(child)
  }

  def removeChildren(): Unit = {
    while (last != null) _removeChildNode(last)
  }

  protected def transformChild(child:DisplayObject):DisplayObject = child

  def getChildAt(index:Int):DisplayObject = {
    var currentIndex:Int = 0
    var child = this.first
    while (child != null) {
      if (currentIndex == index) return transformChild(child)
      child = child.next
      currentIndex += 1
    }
    null
  }

  def getChildByName(name:String):DisplayObject = {
    var child = this.first
    while (child != null) {
      if (child.name == name) return transformChild(child)
      child = child.next
    }
    null
  }

  override def getLocalUntransformedBounds = {
    Rectangle.bounds(children.map(child => child.getLocalUntransformedBounds).toList)
  }

  override def update(dt: Int): Unit = {
    super.update(dt)
    var child = this.first
    while (child != null) {
      if (child.updating) child.update((dt * child.updateSpeed).toInt)
      child = child.next
    }
  }

  override def touchUpdate(point: Point, kind: TouchEventType) = {
    super.touchUpdate(point, kind)
    var child = this.first
    while (child != null) {
      child.touchUpdate(point, kind)
      child = child.next
    }
  }

  override protected def renderInternal(context: EngineContext): Unit = {
    var child = this.first
    while (child != null) {
      child.render(context)
      child = child.next
    }
  }

  def children = {
    val buffer = new ListBuffer[DisplayObject]
    var child = this.first
    while (child != null) {
      buffer.append(child)
      child = child.next
    }
    buffer
  }
}
