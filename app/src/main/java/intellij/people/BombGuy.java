package intellij.people;

import intellij.Position;
import intellij.item.Bomb;
import processing.core.PApplet;

public class BombGuy extends People {
    private static final String NAME ="player";
    private Bomb bomb;

    public BombGuy(PApplet pApplet) {
        super(pApplet);
    }

    public void getPeopleImg() {
        for (int i = 1; i <= 4; i++) {
            imgDownList.add(pApplet.loadImage("src/main/resources/" + NAME + "/" + NAME + "_down" + i + ".png"));
            System.out.println("src/main/resources/" + NAME + "/" + NAME + "_down" + i + ".png");
        }
        for (int i = 1; i <= 4; i++) {
            imgLeftList.add(pApplet.loadImage("src/main/resources/" + NAME + "/" + NAME + "_left" + i + ".png"));
        }
        for (int i = 1; i <= 4; i++) {
            imgUpList.add(pApplet.loadImage("src/main/resources/" + NAME + "/" + NAME + "_up" + i + ".png"));
        }
        for (int i = 1; i <= 4; i++) {
            imgRightList.add(pApplet.loadImage("src/main/resources/" + NAME + "/" + NAME + "_right" + i + ".png"));
        }
    }

    public Position getPosition(){
        return position;
    }





}
