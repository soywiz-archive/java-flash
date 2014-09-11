package com.soywiz.flash.backend

import com.soywiz.flash.util.Point

trait Component extends Renderizable with Updatable {
  def touchUpdate(point: Point, kind: Int)
}
