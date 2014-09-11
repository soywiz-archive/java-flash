package com.soywiz.flash.util

object Rectangle {
  def fromBounds(left:Float, top:Float, right:Float, bottom:Float): Rectangle = {
    new Rectangle(left, top, right - left, bottom - top)
  }

  def fromBounds(topLeft:Point, bottomRight:Point): Rectangle = {
    fromBounds(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y)
  }

  lazy val empty = new Rectangle(0, 0, 0, 0)

  def bounds(items:List[Rectangle]) = {
    if (items.length > 0) {
      Rectangle.fromBounds(
        items.map(item => item.left).min,
        items.map(item => item.top).min,
        items.map(item => item.right).max,
        items.map(item => item.bottom).max
      )
    } else {
      Rectangle.empty
    }
  }
}

final class Rectangle(val x:Float, val y:Float, val width:Float, val height:Float) {
  lazy val topLeft = new Point(left, top)
  lazy val bottomRight = new Point(right, bottom)
  lazy val size = new Size(width, height)
  @inline def left = x
  @inline def top = y
  @inline def right = x + width
  @inline def bottom = y + height

  def +(that:Rectangle) = Rectangle.fromBounds(
      Math.min(this.left, that.left),
      Math.min(this.top, that.top),
      Math.max(this.right, that.right),
      Math.max(this.bottom, that.bottom)
    )

  def transform(matrix:Matrix) = {
    Rectangle.fromBounds(matrix.transformPoint(topLeft), matrix.transformPoint(bottomRight))
  }

  def contains(point: Point) = ((point.x >= left) && (point.x < right) && (point.y >= top) && (point.y < bottom))

  override def toString: String = s"Rectangle(($left,$top)-($right,$bottom))"
}
