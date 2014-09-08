package com.soywiz.flash.util

class MatrixTransform(val x:Float, val y:Float, val scaleX:Float, val scaleY:Float, val rotation:Float, val skewX:Float, val skewY:Float) {
/*
  var x:Float
  var y:Float
  var scaleX:Float
  var scaleY:Float
  var rotation:Float
  var skewX:Float
  var skewY:Float

  def this(matrix:Matrix) = {
    val M_PI_4 = Math.PI / 4
    var x:Float = 0
    var y:Float = 0
    var scaleX:Float = 1
    var scaleY:Float = 1
    var rotation:Float = 0
    var skewX:Float = 0
    var skewY:Float = 0
    x = matrix.tx
    y = matrix.ty

    skewX = Math.atan(-matrix.c / matrix.d).toFloat
    skewY = Math.atan(matrix.b / matrix.a).toFloat

    if (skewX.isNaN) skewX = 0.0f
    if (skewY.isNaN) skewY = 0.0f

    var scaleY = (skewX > -M_PI_4 && skewX < M_PI_4) ? matrix.d / Math.cos(skewX) : -matrix.c / Math.sin(skewX)
    var scaleX = (skewY > -M_PI_4 && skewY < M_PI_4) ? matrix.a / Math.cos(skewY) : matrix.b / Math.sin(skewY)

    if (Math.abs(skewX - skewY) < 0.0001) {
      rotation = skewX
      skewX = skewY = 0
    } else {
      rotation = 0
    }

    new MatrixTransform(x, y, scaleX, scaleY, rotation, skewX, skewY);
  }
*/
}
