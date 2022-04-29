package intellij.people;


import static intellij.Parameter.*;
import intellij.Position;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public abstract class People {
    protected List<PImage> imgDownList;
    protected List<PImage> imgLeftList;
    protected List<PImage> imgUpList;
    protected List<PImage> imgRightList;


    protected int lastDirection;
    protected PApplet pApplet;
    private long time;
    private int index;
    protected int countFps;
    protected Position position;
    protected boolean isDead;

    public People(PApplet pApplet) {
        this.pApplet = pApplet;


        this.lastDirection = DOWN;
        this.index = 0;
        this.isDead=false;
        countFps=0;
        time = System.currentTimeMillis();
        imgLeftList = new ArrayList<>();
        imgUpList = new ArrayList<>();
        imgRightList = new ArrayList<>();
        imgDownList = new ArrayList<>();
        getPeopleImg();
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void draw() {
        int rowOnMap = position.getRowOnMap();
        int columnOnMap = position.getColumnOnMap();

        if (System.currentTimeMillis() - time >= RENDER_F_IN_MILISEC) {
            time = System.currentTimeMillis();
            index++;
        }

        index %= 4;

        if (lastDirection == LEFT) {
            pApplet.image(imgLeftList.get(index), rowOnMap, columnOnMap);
        } else if (lastDirection == UP) {
            pApplet.image(imgUpList.get(index), rowOnMap, columnOnMap);
        } else if (lastDirection == RIGHT) {
            pApplet.image(imgRightList.get(index), rowOnMap, columnOnMap);
        } else if (lastDirection == DOWN) {
            pApplet.image(imgDownList.get(index), rowOnMap, columnOnMap);
        }

    }

    public void move(int inputKey) {

        if (inputKey == LEFT) {
            position.move_left();
            lastDirection = LEFT;
        } else if (inputKey == UP) {
            position.move_up();
            lastDirection = UP;
        } else if (inputKey == RIGHT) {
            position.move_right();
            lastDirection = RIGHT;
        } else if (inputKey == DOWN) {
            position.move_down();
            lastDirection = DOWN;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
        position=null;
    }

    public abstract void getPeopleImg();


}
