import com.soywiz.flash.backend.swing.SwingEngineContext
import com.soywiz.flash.display.{Quad, Stage}

object Sample extends App {
  var stage = new Stage()
  var context = new SwingEngineContext(800, 600, stage)
  //var texture = context.createImageFromFile(new File("c:/temp/titleimg.jpg"))

  //stage.addChild(new Image(texture) {
  stage.addChild(new Quad {
    this.x = 100
    this.y = 100
    this.alpha = 0.5f
    this.scaleX = 2

    override def update(dt: Int): Unit = {
      this.x += dt * 0.1f
      this.rotation += 0.01f
    }
  })

  context.loop()
}
