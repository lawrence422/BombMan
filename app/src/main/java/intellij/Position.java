package intellij;

import static intellij.Parameter.*;

public class Position {


    private int row;
    private int column;
    private String type;
    private char[][] map;

    public Position(int row, int column, String type) {
        this.type = type;
        this.row = row;
        this.column = column;
    }

    public Position(int row, int column, String type, char[][] map) {
        this.type = type;
        this.row = row;
        this.column = column;
        this.map = map;
    }

    public Position() {
    }

    public boolean move_left() {
        char next=map[row][column-1];
        if (next !='W'&&next !='B') {
            this.column -= 1;
            return true;
        }else {
            return false;
        }
    }

    public boolean move_up() {
        char next=map[row-1][column];
        if (next !='W'&&next !='B') {
            this.row -= 1;
            return true;
        }else {
            return false;
        }

    }

    public boolean move_right() {
        char next=map[row][column+1];
        if (next !='W'&&next !='B') {
            this.column += 1;
            return true;
        }else {
            return false;
        }

    }

    public boolean move_down() {
        char next=map[row+1][column];
        if (next !='W'&&next !='B') {
            this.row += 1;
            return true;
        }else {
            return false;
        }
    }

    public boolean check_explosion_left() {
        char next=map[row][column-1];
        return next != 'W';
    }

    public boolean check_explosion_up() {
        char next=map[row-1][column];
        return next != 'W';

    }

    public boolean check_explosion_right() {
        char next=map[row][column+1];
        return next != 'W';

    }

    public boolean check_explosion_down() {
        char next=map[row+1][column];
        return next != 'W';
    }


    public void setLocation(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumnOnMap() {
        if (type.equalsIgnoreCase("people")) {
            return row * MAP_UNIT + PEOPLE_COLUMN_ADJUST +MAP_DOWN_UNIT;
        } else {
            return row * MAP_UNIT+MAP_DOWN_UNIT;
        }
    }


    public int getRowOnMap() {
        if (type.equalsIgnoreCase("people")) {
            return column * MAP_UNIT+ PEOPLE_ROW_ADJUST;
        } else {
            return column * MAP_UNIT;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public char[][] getMap() {
        return map;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public static boolean isEqual(Position p1, Position p2){

        return p1 != null && p2 != null && p1.getRow() == p2.getRow() && p1.getColumn() == p2.getColumn();
    }

    public static String formatPosition(Position p){
        return String.format("%02d/%02d",p.getRow(),p.getColumn());
    }

    public static Position deepClone(Position p){
        return new Position(p.getRow(), p.getColumn(),p.getType());
    }
    public static int[]toRowColumnOnMap(int row,int column){
        return new int[]{row* MAP_UNIT,column*MAP_UNIT};
    }

    public static int mutipleMapUnit(int num){
        return num*MAP_UNIT;
    }

}
