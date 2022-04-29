package intellij.item;

import intellij.Position;
import processing.core.PApplet;
import processing.core.PImage;

public class SolidWall extends Item{
    public SolidWall(PApplet pApplet) {
        super( pApplet);
    }

    @Override
    public PImage getImg() {
        return super.pApplet.loadImage("src/main/resources/wall/solid.png");
    }
}
