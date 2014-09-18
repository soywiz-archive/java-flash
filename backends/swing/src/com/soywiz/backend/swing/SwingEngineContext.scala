package com.soywiz.flash.backend.swing

import java.awt.event.{MouseWheelEvent, MouseEvent, ActionEvent, ActionListener}
import java.awt.image.{VolatileImage, BufferedImage}
import java.awt._
import java.io.{InputStream, FileInputStream, File, ByteArrayInputStream}
import javax.imageio.ImageIO
import javax.swing.{JFrame, JPanel, Timer}

import com.soywiz.flash.backend.Component
import com.soywiz.flash.backend._
import com.soywiz.flash.util.{Color, Point}

import scala.collection.mutable

class SwingEngineContext(val width: Int, val height: Int) extends EngineContext {
  //System.setProperty("awt.useSystemAAFontSettings","on")
  //System.setProperty("swing.aatext", "true")

  EngineContext.instance = this

  def setTimeout(callback: () => Unit, ms: Int) = {
    new Timer(ms, new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        callback()
      }
    }).start()
  }

  def mousePosition: Point = new Point(0, 0)

  def loop(root: Component) = {
    frame = new JFrame("scala-flash")
    g = null
    alpha = 1.0f


    val canvas = new Canvas() {
      //private var createdBuffers = false
      private var volatileImg: VolatileImage = null

      enableEvents(AWTEvent.MOUSE_EVENT_MASK |
        AWTEvent.MOUSE_MOTION_EVENT_MASK |
        AWTEvent.KEY_EVENT_MASK)

      override def processMouseEvent(e: MouseEvent): Unit = {
        super.processMouseEvent(e)

        root.touchUpdate(new Point(e.getX, e.getY), 1)
        //println(s"processMouseEvent:$e")
      }

      override def processMouseMotionEvent(e: MouseEvent): Unit = {
        super.processMouseMotionEvent(e)

        root.touchUpdate(new Point(e.getX, e.getY), 0)

        e.getID match {
          case MouseEvent.MOUSE_MOVED =>
          case _ =>
        }
        //println(s"processMouseMotionEvent:$e")
      }

      override def processMouseWheelEvent(e: MouseWheelEvent): Unit = {
        super.processMouseWheelEvent(e)
        //println(s"processMouseWheelEvent:$e")
      }

      def update(): Unit = {
        // create the hardware accelerated image.
        createBackBuffer()

        // Main rendering loop. Volatile images may lose their contents.
        // This loop will continually render to (and produce if neccessary) volatile images
        // until the rendering was completed successfully.
        do {

          // Validate the volatile image for the graphics configuration of this
          // component. If the volatile image doesn't apply for this graphics configuration
          // (in other words, the hardware acceleration doesn't apply for the new device)
          // then we need to re-create it.
          val gc = this.getGraphicsConfiguration
          val valCode = volatileImg.validate(gc)

          // This means the device doesn't match up to this hardware accelerated image.
          if (valCode == VolatileImage.IMAGE_INCOMPATIBLE) {
            createBackBuffer(); // recreate the hardware accelerated image.
          }

          val offscreenGraphics = volatileImg.getGraphics

          /*
          if (!createdBuffers) {
            createBufferStrategy(2)
            createdBuffers = true
          }
          */

          var strategy = getBufferStrategy

          if (strategy == null || strategy.contentsLost) {
            createBufferStrategy(2)
            strategy = getBufferStrategy
          }
          g = strategy.getDrawGraphics.asInstanceOf[Graphics2D]
          //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
          //g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC)
          g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
          root.render(context)

          //doPaint(offscreenGraphics); // call core paint method.

          // paint back buffer to main graphics
          //g.drawImage(volatileImg, 0, 0, this);

          g.dispose()
          strategy.show()

          // Test if content is lost
        } while (volatileImg.contentsLost())
      }

      private def createBackBuffer() {
        volatileImg = getGraphicsConfiguration.createCompatibleVolatileImage(getWidth, getHeight)
      }

      override def paint(g: Graphics): Unit = {
        update()
      }
    }
    frame.getContentPane.add(canvas)

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.pack()
    frame.setSize(width, height)
    frame.setVisible(true)
    frame.setLocationRelativeTo(null)

    val fps = 60.0f
    val frameMs = (1000 / fps).toInt
    var lastTime = System.currentTimeMillis
    while (true) {
      val currentTime = System.currentTimeMillis
      val deltaTime = (currentTime - lastTime).toInt
      lastTime = currentTime
      root.update(deltaTime)
      //frame.repaint()
      canvas.update()
      Thread.sleep(frameMs)
    }
  }

  private var frame: JFrame = null
  private var alpha: Float = 1.0f
  //private val gList:mutable.Stack = new mutable.Stack[Graphics2D]()
  private var g: Graphics2D = null
  private val context = this

  override def openFile(path: String): InputStream = {
    println(System.getProperty("user.dir") + "/assets/" + path)
    new FileInputStream(System.getProperty("user.dir") + "/assets/" + path)
  }

  override def clear(color: Color): Unit = {
    g.setColor(convertColor(color))
    g.fillRect(0, 0, frame.getWidth, frame.getHeight)
  }

  override def drawSolid(width: Int, height: Int, color: Color): Unit = {
    g.setColor(convertColor(color))
    g.fillRect(0, 0, width, height)
  }

  override def drawImage(width: Int, height: Int, texture: Texture): Unit = {
    val base = texture.base.asInstanceOf[SwingTextureBase]
    g.drawImage(base.image, 0, 0, width, height, texture.x, texture.y, texture.x + texture.width, texture.y + texture.height, null)
  }

  private def convertColor(color: Color): java.awt.Color = {
    new java.awt.Color(color.r, color.g, color.b, color.a)
  }

  override def drawText(x: Float, y: Float, width: Float, height: Float, text: String, fontFamily: String, color: Color, size: Int, align: TextAlign): Unit = {
    g.setColor(convertColor(color))
    g.setFont(new Font(fontFamily, Font.PLAIN, size))
    val fm = g.getFontMetrics
    val textWidth = fm.stringWidth(text)
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    //g.getFontRenderContext.
    g.clipRect(x.toInt, y.toInt, width.toInt, height.toInt)
    g.drawString(text, (x + (width - textWidth) * align.ratio).toInt, y + fm.getAscent)
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

  override def createImageFromBytes(data: Array[Byte]): Texture = {
    val image = ImageIO.read(new ByteArrayInputStream(data))
    new Texture(new SwingTextureBase(image), 0, 0, image.getWidth, image.getHeight)
  }

  /*
  override def createImageFromFile(file:File): Texture = {
    val image = ImageIO.read(file)
    new Texture(new SwingTextureBase(image), 0, 0, image.getWidth, image.getHeight)
  }
  */
}

