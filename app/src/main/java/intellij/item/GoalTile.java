package intellij.item;

import intellij.Position;
import processing.core.PApplet;
import processing.core.PImage;

public class GoalTile extends Item {
    private Position position;
    public GoalTile( PApplet pApplet) {
        super(  pApplet);
    }

    @Override
    public PImage getImg() {
        return super.pApplet.loadImage("src/main/resources/goal/goal.png");
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
