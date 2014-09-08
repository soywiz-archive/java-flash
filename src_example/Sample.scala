import com.soywiz.flash.backend.swing.SwingRenderContext
import com.soywiz.flash.display.{Quad, Sprite, Stage}

object Sample extends App {
  var stage = new Stage()
  stage.addChild(new Quad() {
    this.x = 100
    this.y = 100
    this.scaleX = 2
  })
  var context = new SwingRenderContext(800, 600, stage)
  context.loop()
}
