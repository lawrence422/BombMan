package intellij.item;

import intellij.App;
import intellij.Position;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Item {


     PApplet pApplet;
        private Position itemPosition;
    public Item(PApplet pApplet) {
        this.pApplet=pApplet;


    }

    public abstract PImage getImg();
}
