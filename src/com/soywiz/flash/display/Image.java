package com.soywiz.flash.display;

import com.soywiz.flash.backend.EngineContext;
import com.soywiz.flash.backend.Texture;
import com.soywiz.flash.util.Rectangle;

public class Image extends DisplayObject {
    private Texture texture;

    public Image(Texture texture) {
        this.texture = texture;
    }

    @Override
    protected void renderInternal(EngineContext context) {
        context.drawImage(texture.width, texture.height, texture);
    }

    @Override
    public Rectangle getLocalUntransformedBounds() {
        return new Rectangle(0, 0, texture.width, texture.height);
    }

}
