package com.soywiz.flash.backend.swing

import java.awt.geom.AffineTransform
import java.awt.{Graphics2D, Graphics, Color}
import javax.swing.{JPanel, JFrame}

import com.soywiz.flash.backend.{RenderContext, Renderizable}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class SwingRenderContext(val width: Int, val height: Int, val root:Renderizable) extends RenderContext {
  def loop() = {
    val fps = 60.0f
    while (true) {
      //root.render(this)
      frame.repaint()
      Thread.sleep((1000 / fps).toInt)
    }
  }

  val frame = new JFrame("scala-flash")
  var gList = new mutable.Stack[Graphics2D]()
  var g:Graphics2D = null
  var context = this
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.pack()
  frame.setSize(width, height)
  frame.setVisible(true)
  frame.setLocationRelativeTo(null)
  frame.add(new JPanel() {
    this.setDoubleBuffered(true)

    override def paintComponent(_g: Graphics): Unit = {
      super.paintComponent(_g)
      g = _g.asInstanceOf[Graphics2D]
      root.render(context)
    }
  })

  override def clear(color:Color): Unit = {
    g.setColor(color)
    g.fillRect(0, 0, width, height)
  }


  override def drawSolid(width: Int, height: Int, color: Color): Unit = {
    g.setColor(color)
    g.fillRect(0, 0, width, height)
  }

  override def translate(x:Float, y:Float): Unit = {
    g.translate(x, y)
  }

  override def rotate(angle:Float): Unit = {
    g.rotate(angle)
  }

  override def scale(sx:Float, sy:Float): Unit = {
    g.scale(sx, sy)
  }

  override def keep(callback: () => Unit): Unit = {
    val old = g
    g = g.create().asInstanceOf[Graphics2D]
    callback()
    g = old
  }
}
