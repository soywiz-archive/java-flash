package com.soywiz.flash.backend.swing

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.image.BufferedImage
import java.awt._
import java.io.{File, ByteArrayInputStream}
import javax.imageio.ImageIO
import javax.swing.{JFrame, JPanel, Timer}

import com.soywiz.flash.backend.Component
import com.soywiz.flash.backend._

import scala.collection.mutable

class SwingEngineContext(val width: Int, val height: Int, val root: Component) extends EngineContext {
  def setTimeout(callback: () => Unit, ms: Int) = {
    new Timer(ms, new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        callback()
      }
    }).start()
  }

  def loop() = {
    val fps = 60.0f
    val frameMs = (1000 / fps).toInt
    var lastTime = System.currentTimeMillis
    while (true) {
      var currentTime = System.currentTimeMillis
      var deltaTime = (currentTime - lastTime).toInt
      lastTime = currentTime
      root.update(deltaTime)
      frame.repaint()
      Thread.sleep(frameMs)
    }
  }

  val frame = new JFrame("scala-flash")
  var gList = new mutable.Stack[Graphics2D]()
  var g: Graphics2D = null
  var context = this
  var alpha = 1.0f
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

  override def clear(color: Color): Unit = {
    g.setColor(color)
    g.fillRect(0, 0, frame.getWidth, frame.getHeight)
  }

  override def drawSolid(width: Int, height: Int, color: Color): Unit = {
    g.setColor(color)
    g.fillRect(0, 0, width, height)
  }

  override def drawImage(width: Int, height: Int, texture: Texture): Unit = {
    val base = texture.base.asInstanceOf[SwingTextureBase]
    g.drawImage(base.image, 0, 0, width, height, texture.x, texture.y, texture.x + texture.width, texture.y + texture.height, null)
  }

  override def translate(x: Float, y: Float): Unit = {
    if (x == 0 && y == 0) return
    g.translate(x, y)
  }

  override def rotate(angle: Float): Unit = {
    if (angle == 0) return
    g.rotate(angle)
  }

  override def scale(sx: Float, sy: Float): Unit = {
    if (sx == 1 && sy == 1) return
    g.scale(sx, sy)
  }

  override def alpha(alpha: Float): Unit = {
    if (alpha == 1) return
    this.alpha *= alpha
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha))
  }

  override def keep(callback: () => Unit): Unit = {
    val old = g
    val oldAlpha = alpha
    g = g.create().asInstanceOf[Graphics2D]
    try {
      callback()
    } finally {
      alpha = oldAlpha
      g = old
    }
  }

  override def createImageFromBytes(data:Array[Byte]): Texture = {
    val image = ImageIO.read(new ByteArrayInputStream(data))
    new Texture(new SwingTextureBase(image), 0, 0, image.getWidth, image.getHeight)
  }

  override def createImageFromFile(file:File): Texture = {
    val image = ImageIO.read(file)
    new Texture(new SwingTextureBase(image), 0, 0, image.getWidth, image.getHeight)
  }
}

