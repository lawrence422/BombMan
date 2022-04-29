package intellij.people;

import processing.core.PApplet;
import static intellij.Parameter.*;
public class RedEnemy extends People {
    private static final String NAME = "red";

    public RedEnemy(PApplet pApplet) {
        super(pApplet);
    }

    @Override
    public void getPeopleImg() {
        for (int i = 1; i <= 4; i++) {
            imgDownList.add(pApplet.loadImage("src/main/resources/red_enemy/" + NAME + "_down" + i + ".png"));
            System.out.println("src/main/resources/red_enemy/" + NAME + "_down" + i + ".png");
        }
        for (int i = 1; i <= 4; i++) {
            imgLeftList.add(pApplet.loadImage("src/main/resources/red_enemy/" + NAME + "_left" + i + ".png"));
        }
        for (int i = 1; i <= 4; i++) {
            imgUpList.add(pApplet.loadImage("src/main/resources/red_enemy/" + NAME + "_up" + i + ".png"));
        }
        for (int i = 1; i <= 4; i++) {
            imgRightList.add(pApplet.loadImage("src/main/resources/red_enemy/" + NAME + "_right" + i + ".png"));
        }

    }

    public void redMove() {
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
                    if (randomDirection < 0.33) {
                        lastDirection = UP;
                    } else if (randomDirection < 0.66) {
                        lastDirection = RIGHT;
                    } else if (randomDirection < 1) {
                        lastDirection = DOWN;
                    }
                } else if (lastDirection == UP) {
                    if (randomDirection < 0.33) {
                        lastDirection = RIGHT;
                    } else if (randomDirection < 0.66) {
                        lastDirection = DOWN;
                    } else if (randomDirection < 1) {
                        lastDirection = LEFT;
                    }
                } else if (lastDirection == RIGHT) {
                    if (randomDirection < 0.33) {
                        lastDirection = DOWN;
                    } else if (randomDirection < 0.66) {
                        lastDirection = LEFT;
                    } else if (randomDirection < 1) {
                        lastDirection = UP;
                    }
                } else if (lastDirection == DOWN) {
                    if (randomDirection < 0.33) {
                        lastDirection = LEFT;
                    } else if (randomDirection < 0.66) {
                        lastDirection = UP;
                    } else if (randomDirection < 1) {
                        lastDirection = RIGHT;
                    }
                }
            }
            countFps=0;
        }else {
            countFps++;
        }
    }

}
