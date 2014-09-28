import com.soywiz.backend.swing.SwingEngineContext;
import com.soywiz.flash.display.Quad;
import com.soywiz.flash.display.Stage;

public class Sample {
    static public void main(String[] args) {
        SwingEngineContext context = new SwingEngineContext(800, 600);
        Stage stage = new Stage(context);
        //var texture = context.createImageFromFile(new File("c:/temp/titleimg.jpg"))

        //stage.addChild(new Image(texture) {
        stage.addChild(new Quad() {
            {
                this.x = 100;
                this.y = 100;
                this.alpha = 0.5f;
                this.scaleX = 2;
            }

            @Override
            public void update(int dt) {
                this.x += dt * 0.1f;
                this.rotation += 0.01f;
            }
        });

        context.loop(stage);
    }
}
