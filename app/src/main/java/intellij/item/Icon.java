package intellij.item;

import intellij.Position;
import processing.core.PApplet;
import processing.core.PImage;

public class Icon extends Item{
    private static final int ICON_ROW=-1;
    private static final int ICON_COLUMN=4;
    private static final int ICON_ADJUST_ROW=0;
    private static final int ICON_ADJUST_COLUMN=-14;
    private static final int LIVE_ADJUST_ROW = 38;
    private static final int LIVE_ADJUST_COLUMN = 16;

    private final Position position;
    private final PImage iconImg;
    private int lives;

    public Icon(PApplet pApplet,int lives) {
        super(pApplet);
        this.position=new Position(ICON_ROW,ICON_COLUMN,"Item");
        this.iconImg=getImg();
        this.lives=lives;
    }

    public void draw(){
        int rowOnMap=position.getRowOnMap();
        int columnOnMap=position.getColumnOnMap();
        super.pApplet.image(iconImg,rowOnMap+ICON_ADJUST_ROW,columnOnMap+ICON_ADJUST_COLUMN);
        pApplet.fill(0,0,0);
        pApplet.text(lives,rowOnMap+LIVE_ADJUST_ROW,columnOnMap+LIVE_ADJUST_COLUMN);
    }


    @Override
    public PImage getImg() {
        return super.pApplet.loadImage("src/main/resources/icons/player.png");
    }

    public void loseLive(){
        lives--;
    }

    public boolean hasLive(){
        return lives>0;
    }

}
