package com.soywiz.flash.display

import com.soywiz.flash.util.Point

trait MouseUpdate {
  def over(point:Point) = {}
  def out(point:Point) = {}
  def move(point:Point) = {}
  def click(point:Point) = {}
}
