package com.soywiz.flash.display

import com.soywiz.flash.backend.{TouchEventType, Updatable, Component, EngineContext}
import com.soywiz.flash.util.{Signal, Rectangle, Point, Matrix}

import scala.collection.mutable.ListBuffer

abstract class DisplayObject extends DisplayObjectBase {
  var x = 0.0
  var y = 0.0
  var scaleX = 1.0
  var scaleY = 1.0
  var rotation = 0.0
  var alpha = 1.0
  var visible = true
  var parent: DisplayObjectContainer = null
  var name:String = null
  var updating:Boolean = true
  var updateSpeed:Double = 1.0
  val components:ListBuffer[Updatable] = ListBuffer()

  val onMouseUpdate = ListBuffer[MouseUpdate]()

  def interactive = onMouseUpdate.length > 0

  def stage: Stage = parent match {
    case stage:Stage => stage
    case null => null
    case _ => parent.stage
  }

  def render(context: EngineContext): Unit = {
    if (!visible) return

    context.keep(() => {
      context.translate(x.toFloat, y.toFloat)
      context.rotate(Math.toRadians(rotation).toFloat)
      context.scale(scaleX.toFloat, scaleY.toFloat)
      context.alpha(alpha.toFloat)
      renderInternal(context)
    })
  }

  protected def renderInternal(context: EngineContext): Unit = {
  }

  private var lastInside = false
  def touchUpdate(point: Point, kind: TouchEventType) = {
    if (interactive) {
      val globalBounds = this.globalBounds
      val inside = globalBounds.contains(point)
      val localPoint = globalToLocal(point)

      if (inside) {
        if (kind == TouchEventType.Click) {
          for (item <- onMouseUpdate) item.click(localPoint)
        } else {
          if (!lastInside) for (item <- onMouseUpdate) item.over(localPoint)
          for (item <- onMouseUpdate) item.move(localPoint)
        }
      } else {
        if (lastInside) for (item <- onMouseUpdate) item.out(localPoint)
      }

      lastInside = inside
    }
  }

  override def update(dt: Int): Unit = {
    for (component <- components) component.update(dt)
  }

  def transformMatrix = {
    val matrix = new Matrix()
    matrix.translate(x.toFloat, y.toFloat)
    matrix.rotate(Math.toRadians(rotation).toFloat)
    matrix.scale(scaleX.toFloat, scaleY.toFloat)
    matrix
  }

  def globalTransformMatrix(_output:Matrix = null) = {
    var output = _output
    if (output == null) output = new Matrix()
    var node = this
    while (node != null) {
      output.preconcat(node.transformMatrix)
      node = node.parent
    }
    output
  }

  /*
  def width: Int = 0
  def height: Int = 0
  def width_= (value:Int) { }
  def height_= (value:Int) { }
  */

  final def globalBounds:Rectangle = {
    getLocalUntransformedBounds.transform(globalTransformMatrix())
  }

  final def localBounds = {
    getLocalUntransformedBounds.transform(transformMatrix)
  }

  def getLocalUntransformedBounds = {
    new Rectangle(0, 0, 0, 0)
  }

  final def localToGlobal(point: Point): Point = {
    globalTransformMatrix().transformPoint(point)
  }

  final def globalToLocal(point: Point): Point = {
    globalTransformMatrix().invert().transformPoint(point)
  }
}
