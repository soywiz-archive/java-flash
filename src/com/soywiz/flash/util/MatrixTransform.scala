package com.soywiz.flash.util

object MatrixTransform {
  def fromMatrix(matrix:Matrix): MatrixTransform = {
    val M_PI_4 = Math.PI / 4
    var rotation:Float = 0
    var skewX:Float = 0
    var skewY:Float = 0

    val x = matrix.tx
    val y = matrix.ty

    skewX = Math.atan(-matrix.c / matrix.d).toFloat
    skewY = Math.atan(matrix.b / matrix.a).toFloat

    if (skewX.isNaN) skewX = 0.0f
    if (skewY.isNaN) skewY = 0.0f

    val scaleY = if (skewX > -M_PI_4 && skewX < M_PI_4) matrix.d / Math.cos(skewX) else -matrix.c / Math.sin(skewX)
    val scaleX = if (skewY > -M_PI_4 && skewY < M_PI_4) matrix.a / Math.cos(skewY) else matrix.b / Math.sin(skewY)

    if (Math.abs(skewX - skewY) < 0.0001) {
      rotation = skewX
      skewX = 0
      skewY = 0
    } else {
      rotation = 0
    }

    new MatrixTransform(x, y, scaleX.toFloat, scaleY.toFloat, rotation, skewX, skewY)
  }
}

class MatrixTransform(val x:Float, val y:Float, val scaleX:Float, val scaleY:Float, val rotation:Float, val skewX:Float, val skewY:Float)
