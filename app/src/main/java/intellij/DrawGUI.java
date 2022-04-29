package intellij;

import intellij.item.*;
import intellij.people.BombGuy;
import intellij.people.RedEnemy;
import intellij.people.YellowEnemy;
import processing.core.PApplet;

import java.util.*;

import static intellij.Parameter.*;

public class DrawGUI {
    private final BombGuy bombGuy;
    private final RedEnemy redEnemy;
    private final YellowEnemy yellowEnemy;
    private final List<Bomb> bombList;

    private final BrokenWall brokenWall;
    private final SolidWall solidWall;
    private final Space space;
    private final GoalTile goalTile;

    private final Icon icon;
    private final Clock clock;

    private final Map<Integer, char[][]> mapList;
    private final Map<Integer, char[][]> mapListCopy;
    private final Map<String, Position> peopleXYMap;
    //String Format 02d/02d [row/column]
    private final Map<String, Position> deletePosition;
    PApplet pApplet;

    private final int lives;
    private final int timeLimit;
    private int levels;
    private boolean initial;


    public DrawGUI(PApplet pApplet, Map<Integer, char[][]> mapList, int lives, int timeLimit) {
        this.pApplet = pApplet;
        this.mapList = mapList;
        this.lives = lives;
        this.timeLimit = timeLimit;

        bombGuy = new BombGuy(pApplet);
        redEnemy = new RedEnemy(pApplet);
        yellowEnemy = new YellowEnemy(pApplet);

        icon = new Icon(pApplet, lives);
        clock = new Clock(pApplet, timeLimit);

        peopleXYMap = new HashMap<>();
        brokenWall = new BrokenWall(pApplet);
        solidWall = new SolidWall(pApplet);
        space = new Space(pApplet);
        goalTile = new GoalTile(pApplet);
        bombList = new ArrayList<>();
        deletePosition = new HashMap<>();
        mapListCopy = new HashMap<>();

        initial = true;
        levels = 0;

        copyMap();

    }

    public void draw() {
        if (icon.hasLive() && clock.hasTime()) {
            saveChangeToMap();
            if (levels >= 0 && levels < mapList.size()) {
                drawIconAndTime();
                drawMapAndPeople();
                drawBomb();
            } else if (levels >= mapList.size()) {
                int[] rowAndColumn = Position.toRowColumnOnMap(WIN_ROW, WIN_COLUMN);
                pApplet.fill(0, 0, 0);
                pApplet.text("YOU WIN", rowAndColumn[0] + WIN_ROW_ADJUST, rowAndColumn[1] + WIN_COLUMN_ADJUST);
            }
        } else {

            int[] rowAndColumn2 = Position.toRowColumnOnMap(LOSE_ROW, LOSE_COLUMN);
            pApplet.fill(0, 0, 0);
            pApplet.text("GAME OVER", rowAndColumn2[0] + LOST_ROW_ADJUST, rowAndColumn2[1] + LOST_COLUMN_ADJUST);
        }
    }

    private void drawIconAndTime() {
        this.icon.draw();
        this.clock.draw();
    }


    public void drawMapAndPeople() {
        char[][] map = mapList.get(levels);
        Position position = new Position();

        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                position.setType("item");
                position.setLocation(row, column);
                if (map[row][column] == 'W') {
                    pApplet.image(solidWall.getImg(), position.getRowOnMap(), position.getColumnOnMap());
                } else if (map[row][column] == 'B') {
                    pApplet.image(brokenWall.getImg(), position.getRowOnMap(), position.getColumnOnMap());
                } else if (map[row][column] == 'G') {
                    if (initial) {
                        goalTile.setPosition(Position.deepClone(position));
                    }

                    pApplet.image(goalTile.getImg(), position.getRowOnMap(), position.getColumnOnMap());
                } else if (map[row][column] == ' ' || map[row][column] == 'P'
                        || map[row][column] == 'R' || map[row][column] == 'Y') {
                    pApplet.image(space.getImg(), position.getRowOnMap(), position.getColumnOnMap());

                }

                if (initial) {
//                    for (char[]k:map){
//                        for (char j:k){
//                            System.out.print(j);
//                        }
//                        System.out.println();
//                    }

                    if (map[row][column] == 'P') {
                        peopleXYMap.put("P", new Position(row, column, "people", map));
                    } else if (map[row][column] == 'R') {
                        peopleXYMap.put("R", new Position(row, column, "people", map));
                    } else if (map[row][column] == 'Y') {
                        peopleXYMap.put("Y", new Position(row, column, "people", map));
                    }

                }

            }
        }


        Iterator<Map.Entry<String, Position>> iterator = peopleXYMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Position> entry = iterator.next();
            Position positionPeople = entry.getValue();
            if (entry.getKey().equals("P")) {
                if (initial) {
                    bombGuy.setPosition(positionPeople);
                }
                bombGuy.draw();
            } else if (entry.getKey().equals("R")) {
                if (initial) {
                    redEnemy.setPosition(positionPeople);
                }
                if (!redEnemy.isDead()) {
                    redEnemy.draw();
                }
            } else if (entry.getKey().equals("Y")) {
                if (initial) {
                    yellowEnemy.setPosition(positionPeople);
                }
                if (!yellowEnemy.isDead()) {
                    yellowEnemy.draw();
                }
            }
        }
        initial = false;
    }

    public void drawBomb() {
        List<Bomb> removeList = new ArrayList<>();
        for (Bomb bomb : bombList) {
            if (!bomb.isExplosion()) {
                bomb.draw();
            } else {
//                pApplet.image(space.getImg(), bomb.g.getRowOnMap(), position.getColumnOnMap());
                deletePosition.putAll(bomb.getDestoredPositionMap());
                removeList.add(bomb);
            }
        }
        bombList.removeAll(removeList);
    }

    public void saveChangeToMap() {
        if (levels >= 0 && levels < mapList.size()) {
            char[][] currentMap = mapList.get(levels);

            for (int row = 0; row < currentMap.length; row++) {
                for (int column = 0; column < currentMap[row].length; column++) {
                    String rowAndColumn = String.format("%02d/%02d", row, column);
                    if (deletePosition.containsKey(rowAndColumn)) {
                        Position killPosition = deletePosition.get(rowAndColumn);
                        if (currentMap[row][column] == 'B') {
                            currentMap[row][column] = ' ';
                        }
                        if (Position.isEqual(killPosition, this.redEnemy.getPosition())) {
                            redEnemy.setDead(true);
                        }

                        if (Position.isEqual(killPosition, this.yellowEnemy.getPosition())) {
                            yellowEnemy.setDead(true);
                        }
                    }
                }
            }
            if (IGNITE_BOMB) {
                for (Bomb bomb : bombList) {
                    String tempRowAndColumn = Position.formatPosition(bomb.getPosition());
                    if (deletePosition.containsKey(tempRowAndColumn)) {
                        bomb.setIgnite(true);
                    }
                }
            }

            checkIfdead();

            deletePosition.clear();
            if (bombGuy.getPosition() != null && goalTile.getPosition() != null) {
                if (Position.isEqual(bombGuy.getPosition(), goalTile.getPosition())) {
                    levels++;
                    initial = true;
                    redEnemy.setDead(true);
                    yellowEnemy.setDead(true);
//                    if (levels == 2) {
//
//                    }

                }
            }
        }

    }

    private void checkIfdead() {
        Position position = this.bombGuy.getPosition();
        if (position != null) {
            if (deletePosition.containsKey(Position.formatPosition(position)) ||
                    Position.isEqual(position, this.redEnemy.getPosition()) ||
                    Position.isEqual(position, this.yellowEnemy.getPosition())) {

                icon.loseLive();
                initial = true;
                levels = 0;
                this.redEnemy.setDead(false);
                this.yellowEnemy.setDead(false);
                resetMap();
            }
        }
    }

    private void copyMap() {
        for (int i = 0; i < mapList.size(); i++) {
            char[][] temp = mapList.get(i);
            char[][] copy = new char[MAP_ROW][MAP_COLUMN];
            for (int row = 0; row < temp.length; row++) {
                for (int column = 0; column < temp[row].length; column++) {
                    copy[row][column] = temp[row][column];
                }
            }
            mapListCopy.put(i, copy);
        }
    }

    private void resetMap() {
        for (int i = 0; i < mapList.size(); i++) {
            char[][] temp = mapListCopy.get(i);
            char[][] copy = mapList.get(i);
            for (int row = 0; row < temp.length; row++) {
                for (int column = 0; column < temp[row].length; column++) {
                    if (copy[row][column] != temp[row][column]) {
                        copy[row][column] = temp[row][column];
                    }
                }
            }
        }
    }

    public void playerMove(int keyCode) {
        this.bombGuy.move(keyCode);
    }

    public void addBomb() {
        Position position = this.bombGuy.getPosition();
        int bombRow = position.getRow();
        int bomColumn = position.getColumn();
        bombList.add(new Bomb(pApplet, new Position(bombRow, bomColumn, "item", mapList.get(levels))));

    }


    public void enemyMove() {
        if (levels >= 0 && levels < mapList.size()) {
            if (!this.redEnemy.isDead()) {
                this.redEnemy.redMove();
            }
            if (!this.yellowEnemy.isDead()) {
                this.yellowEnemy.yellowMove();
            }
        }
    }
}
