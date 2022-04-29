package intellij.item;

import intellij.Position;
import processing.core.PApplet;
import processing.core.PImage;

public class Space extends Item{
    public Space( PApplet pApplet) {
        super( pApplet);
    }

    @Override
    public PImage getImg() {
        return super.pApplet.loadImage("src/main/resources/empty/empty.png");
    }
}
