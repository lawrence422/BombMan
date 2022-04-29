package intellij.people;

import processing.core.PApplet;
import static intellij.Parameter.*;
public class YellowEnemy extends People {
    private static final String NAME ="yellow";
    public YellowEnemy(PApplet pApplet) {
        super(pApplet);
    }

    @Override
    public void getPeopleImg() {
        for (int i = 1; i <= 4; i++) {
            imgDownList.add(pApplet.loadImage("src/main/resources/yellow_enemy/" + NAME + "_down" + i + ".png"));
            System.out.println("src/main/resources/yellow_enemy/" + NAME + "_down" + i + ".png");
        }
        for (int i = 1; i <= 4; i++) {
            imgLeftList.add(pApplet.loadImage("src/main/resources/yellow_enemy/" + NAME + "_left" + i + ".png"));
        }
        for (int i = 1; i <= 4; i++) {
            imgUpList.add(pApplet.loadImage("src/main/resources/yellow_enemy/" + NAME + "_up" + i + ".png"));
        }
        for (int i = 1; i <= 4; i++) {
            imgRightList.add(pApplet.loadImage("src/main/resources/yellow_enemy/" + NAME + "_right" + i + ".png"));
        }
    }

    public void yellowMove(){
        if (countFps== ENEMY_MOVE_FPS) {
            boolean canWalk = false;
            if (lastDirection == LEFT) {
                canWalk = position.move_left();
            } else if (lastDirection == UP) {
                canWalk = position.move_up();
            } else if (lastDirection == RIGHT) {
                canWalk = position.move_right();
            } else if (lastDirection == DOWN) {
                canWalk = position.move_down();
            }

            if (!canWalk) {
                double randomDirection = Math.random();
                if (lastDirection == LEFT) {
                   lastDirection=UP;
                } else if (lastDirection == UP) {
                    lastDirection=RIGHT;
                } else if (lastDirection == RIGHT) {
                   lastDirection=DOWN;
                } else if (lastDirection == DOWN) {
                    lastDirection=LEFT;
                }
            }
            countFps=0;
        }else {
            countFps++;
        }


//            if (!position.move_down()){
//                double randomDirection = Math.random();
//                if (lastDirection==LEFT){
//
//                    while ()
//                    position.move_left();
//                }
//
//
//                if (countFps==RANDOM_FPS) {
//                    if (randomDirection < 0.33) {
//                        position.move_left();
//                        lastDirection = LEFT;
//                    } else if (randomDirection < 0.5) {
//                        position.move_up();
//                        lastDirection = UP;
//                    } else if (randomDirection < 0.75) {
//                        position.move_right();
//                        lastDirection = RIGHT;
//                    } else {
//                        position.move_down();
//                        lastDirection = DOWN;
//                    }
//                    countFps=0;
//                }else {
//                    countFps++;
//                }
//            }

    }



}
