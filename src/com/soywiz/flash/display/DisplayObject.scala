package com.soywiz.flash.display

import com.soywiz.flash.backend.{Component, EngineContext}
import com.soywiz.flash.util.{Point, Matrix}

abstract class DisplayObject extends Component {
  var x = 0.0f
  var y = 0.0f
  var scaleX = 1.0f
  var scaleY = 1.0f
  var rotation = 0.0f
  var alpha = 1.0f
  var visible = true
  var parent: DisplayObjectContainer = null
  var name:String = null

  def stage: Stage = parent match {
    case stage:Stage => stage
    case null => null
    case _ => parent.stage
  }

  def render(context: EngineContext): Unit = {
    if (!visible) return

    context.keep(() => {
      context.translate(x, y)
      context.rotate(rotation)
      context.scale(scaleX, scaleY)
      context.alpha(alpha)
      renderInternal(context)
    })
  }

  protected def renderInternal(context: EngineContext): Unit = {
  }

  override def update(dt: Int): Unit = {
  }

  def transformMatrix = {
    val matrix = new Matrix()
    matrix.translate(x, y)
    matrix.rotate(rotation)
    matrix.scale(scaleX, scaleY)
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

  def localToGlobal(point: Point): Point = {
    globalTransformMatrix().transformPoint(point)
  }

  def globalToLocal(point: Point): Point = {
    globalTransformMatrix().invert().transformPoint(point)
  }
}
