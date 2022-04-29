package intellij.item;

import static intellij.Parameter.*;
import intellij.Position;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bomb {
    private final PApplet pApplet;
    private final List<PImage> bombStateList;
    private final Map<String, PImage> explosionImgMap;
    private final Map<String, Position> destoredPosition;
    private long time;
    private Position position;
    int state;
    private boolean isExplosion;
    private boolean isIgnite;
    char[][] currentMap;

    public Bomb(PApplet pApplet, Position position) {
        this.pApplet = pApplet;
        this.position = position;
        this.currentMap = position.getMap();
        this.destoredPosition = new HashMap<>();
        this.bombStateList = new ArrayList<>();
        this.explosionImgMap = new HashMap<>();
        this.isExplosion = false;
        this.isIgnite = false;

        getImgList();
        getExplosionImg();
        this.time = System.currentTimeMillis();
        this.state = 0;
    }

    public void draw() {
//        pApplet.image(pApplet.loadImage("src/main/resources/bomb/bomb.png"),position.getRowOnMap(),position.getColumnOnMap());
        bombCountdown();
        bombCountdown();
    }

    private void bombCountdown() {
        if (state < 8) {
            if (System.currentTimeMillis() - time < 250) {
                int row = position.getRowOnMap();
                int column = position.getColumnOnMap();
                pApplet.image(bombStateList.get(state), row, column);
            } else if (isIgnite && !isExplosion) {
//                pApplet.image(explosionImgMap.get("centre"), position.getRowOnMap(), position.getColumnOnMap());
                explosion();
            } else {
                state++;
                time = System.currentTimeMillis();
            }
        } else {
            explosion();

        }


    }

    private void explosion() {
        int row = position.getRowOnMap();
        int column = position.getColumnOnMap();
        if (System.currentTimeMillis() - time < 500 && !isExplosion) {
            //show explosion
//            pApplet.image(explosionMap.get("centre"), row, column);
//            for (int i = 0; i < EXPLOSION_RANG; i++) {
//                if (currentMap[row][column-i-1]!='W'){
//                    //left
//                    pApplet.image(explosionMap.get("horizontal"), row, column);
//                }
//
//                if (currentMap[row][column+i+1]!='W'){
//                    pApplet.image(explosionMap.get("horizontal"), row, column);
//                }
//            }
            showExplosionImg(position, 0, "centre", ' ');
        } else {
            isExplosion = true;
        }
    }


    public void getImgList() {
        for (int i = 0; i < 8; i++) {
            bombStateList.add(pApplet.loadImage("src/main/resources/bomb/bomb" + (i + 1) + ".png"));
        }
    }

    public void getExplosionImg() {
        explosionImgMap.put("centre", pApplet.loadImage("src/main/resources/explosion/centre.png"));
        explosionImgMap.put("horizontal", pApplet.loadImage("src/main/resources/explosion/horizontal.png"));
        explosionImgMap.put("vertical", pApplet.loadImage("src/main/resources/explosion/vertical.png"));
    }

    public void showExplosionImg(Position position, int recursive, String direction, char previousPositionItem) {
        int row = position.getRow();
        int column = position.getColumn();
        String rowAndColumn = String.format("%02d/%02d", row, column);
        if (recursive > EXPLOSION_RANG || row >= MAP_ROW || column >= MAP_COLUMN) {
            return;
        }
        char currentPositionItem = position.getMap()[row][column];

        if (previousPositionItem == 'B') {
            return;
        }


        if ("centre".equalsIgnoreCase(direction)) {
            if (position.check_explosion_left()) {
                showExplosionImg(new Position(row, column - 1, "Item", currentMap), recursive + 1, "left", currentPositionItem);
            }
            if (position.check_explosion_up()) {
                showExplosionImg(new Position(row - 1, column, "Item", currentMap), recursive + 1, "up", currentPositionItem);
            }
            if (position.check_explosion_right()) {
                showExplosionImg(new Position(row, column + 1, "Item", currentMap), recursive + 1, "right", currentPositionItem);
            }
            if (position.check_explosion_down()) {
                showExplosionImg(new Position(row + 1, column, "Item", currentMap), recursive + 1, "down", currentPositionItem);
            }
            pApplet.image(explosionImgMap.get("centre"), position.getRowOnMap(), position.getColumnOnMap());
            destoredPosition.put(rowAndColumn, position);
            return;
        } else {
            if ("left".equalsIgnoreCase(direction)) {
                if (position.check_explosion_left()) {
                    showExplosionImg(new Position(row, column - 1, "Item", currentMap), recursive + 1, "left", currentPositionItem);
                }
                pApplet.image(explosionImgMap.get("horizontal"), position.getRowOnMap(), position.getColumnOnMap());
                destoredPosition.put(rowAndColumn, position);
            } else if ("up".equalsIgnoreCase(direction)) {
                if (position.check_explosion_up()) {
                    showExplosionImg(new Position(row - 1, column, "Item", currentMap), recursive + 1, "up", currentPositionItem);
                }
                pApplet.image(explosionImgMap.get("vertical"), position.getRowOnMap(), position.getColumnOnMap());
                destoredPosition.put(rowAndColumn, position);
            } else if ("right".equalsIgnoreCase(direction)) {
                if (position.check_explosion_right()) {
                    showExplosionImg(new Position(row, column + 1, "Item", currentMap), recursive + 1, "right", currentPositionItem);
                }
                pApplet.image(explosionImgMap.get("horizontal"), position.getRowOnMap(), position.getColumnOnMap());
                destoredPosition.put(rowAndColumn, position);
            } else if ("down".equalsIgnoreCase(direction)) {
                if (position.check_explosion_down()) {
                    showExplosionImg(new Position(row + 1, column, "Item", currentMap), recursive + 1, "down", currentPositionItem);
                }
                pApplet.image(explosionImgMap.get("vertical"), position.getRowOnMap(), position.getColumnOnMap());
                destoredPosition.put(rowAndColumn, position);
            }
        }
    }

    public boolean isExplosion() {
        return isExplosion;
    }

    public void setExplosion(boolean explosion) {
        isExplosion = explosion;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isIgnite() {
        return isIgnite;
    }

    public void setIgnite(boolean ignite) {
        isIgnite = ignite;
    }

    public Map<String, Position> getDestoredPositionMap() {
        if (this.isExplosion) {
            return destoredPosition;
        } else {
            return null;
        }
    }

}
