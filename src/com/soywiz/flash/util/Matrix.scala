package com.soywiz.flash.util

class Matrix(var a:Float = 1, var b:Float = 0, var c:Float = 0, var d:Float = 1, var tx:Float = 0, var ty:Float = 0) {
  def concat(m: Matrix) = {
    val a1 = a * m.a + b * m.c
    b = a * m.b + b * m.d
    a = a1

    val c1 = c * m.a + d * m.c
    d = c * m.b + d * m.d
    c = c1

    val tx1 = tx * m.a + ty * m.c + m.tx
    ty = tx * m.b + ty * m.d + m.ty
    tx = tx1
  }

  def copyFrom(sourceMatrix: Matrix) = {
    a = sourceMatrix.a
    b = sourceMatrix.b
    c = sourceMatrix.c
    d = sourceMatrix.d
    tx = sourceMatrix.tx
    ty = sourceMatrix.ty
  }

  def setTo(a: Float, b: Float, c: Float, d: Float, tx: Float, ty: Float) = {
    this.a = a
    this.b = b
    this.c = c
    this.d = d
    this.tx = tx
    this.ty = ty
  }

  def identity() = {
    a = 1
    b = 0
    c = 0
    d = 1
    tx = 0
    ty = 0
  }

  def invert() = {
    var norm = a * d - b * c

    if (norm == 0) {
      a = 0
      b = 0
      c = 0
      d = 0
      tx = -tx
      ty = -ty
    } else {
      norm = 1.0f / norm
      val a1 = d * norm
      d = a * norm
      a = a1
      b *= -norm
      c *= -norm

      val tx1 = -a * tx - c * ty
      ty = -b * tx - d * ty
      tx = tx1
    }
  }

  def mult(m: Matrix) = new Matrix(
    a * m.a + b * m.c,
    a * m.b + b * m.d,
    c * m.a + d * m.c,
    c * m.b + d * m.d,
    tx * m.a + ty * m.c + m.tx,
    tx * m.b + ty * m.d + m.ty
  )

  def rotate(theta: Float) = {
    val cos = Math.cos(theta).toFloat
    val sin = Math.sin(theta).toFloat

    val a1 = a * cos - b * sin
    b = a * sin + b * cos
    a = a1

    val c1 = c * cos - d * sin
    d = c * sin + d * cos
    c = c1

    val tx1 = tx * cos - ty * sin
    ty = tx * sin + ty * cos
    tx = tx1
  }

  def scale(sx: Float, sy: Float) {
    a *= sx
    b *= sy
    c *= sx
    d *= sy
    tx *= sx
    ty *= sy
  }

  def translate(dx: Float, dy: Float) = {
    val m = new Matrix()
    m.tx = dx
    m.ty = dy
    this.concat(m)
  }

  def transformPoint(pos: Point) = new Point(pos.x * a + pos.y * c + tx, pos.x * b + pos.y * d + ty)
}
