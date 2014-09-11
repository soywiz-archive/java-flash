package com.soywiz.flash.util

import scala.collection.mutable.ListBuffer

class Signal[T]() {
  private var handlers = new ListBuffer[T => Unit]

  def add(handler:T => Unit): Unit = {
    handlers += handler
  }

  def apply(value: T): Unit = {
    for (handler <- handlers) handler(value)
  }
}
