package intellij.item;

import intellij.Position;
import processing.core.PApplet;
import processing.core.PImage;

public class BrokenWall extends Item {
    public BrokenWall(PApplet pApplet) {
        super( pApplet);
    }

    @Override
    public PImage getImg() {
        return super.pApplet.loadImage("src/main/resources/broken/broken.png");
    }
}

