package intellij.item;

import intellij.Position;
import processing.core.PApplet;
import processing.core.PImage;
import static intellij.Parameter.*;

public class Clock extends Item {
    private final PImage clockImg;
    private final Position position;
    private int timeLimit;
    private long previousTime;
    private boolean start;

    public Clock(PApplet pApplet, int timeLimit) {
        super(pApplet);
        this.clockImg = getImg();
        this.timeLimit = timeLimit;
        this.start = false;
        this.position = new Position(CLOCK_ROW, CLOCK_COLUMN, "Item");

    }

    @Override
    public PImage getImg() {
        return super.pApplet.loadImage("src/main/resources/icons/clock.png");
    }

    public void draw() {
        if (!start) {
            previousTime = System.currentTimeMillis();
            start = true;
        }
        int rowOnMap=position.getRowOnMap();
        int columnOnMap=position.getColumnOnMap();
        super.pApplet.image(clockImg, rowOnMap + CLOCK_ADJUST_ROW, columnOnMap + CLOCK_ADJUST_COLUMN);
        pApplet.fill(0,0,0);
        pApplet.text(String.format("%3d",timeLimit) , rowOnMap+TIME_ADJUST_ROW, columnOnMap+TIME_ADJUST_COLUMN);
        if (System.currentTimeMillis() - previousTime >= SEC_IN_MILISEC) {
            previousTime = System.currentTimeMillis();
            timeLimit--;
        }
    }

    public boolean hasTime(){
        return timeLimit>0;
    }


}
