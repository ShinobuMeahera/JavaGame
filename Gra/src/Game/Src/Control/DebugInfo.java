package Game.Src.Control;

/**
 * Created by Magnus on 06.07.2016.
 */

import Game.Src.Map.TileMap.TileMap;
import Game.Src.Objects.Player;
import Game.Src.Start.GamePanel;
import Game.Src.Objects.ParentObject;

import java.awt.*;

public class DebugInfo extends ParentObject{

    private String playerX;
    private String playerY;
    private String playerDX;
    private String playerDY;

    private Player p;

    private void convert(){
        playerX = "X:" + Integer.toString(p.getx());
        playerY = "Y:" + Integer.toString(p.gety());
        playerDX = "DX:" + Double.toString(p.getdx());
        playerDY = "DY:" + Double.toString(p.getdy());
    }

    public DebugInfo(TileMap tm, Player pl){
        super(tm);
        p = pl;
        convert();
        debugReady = false;
    }

    public void update(){
        convert();
    }
    public static boolean getStatus(){
        return debugReady;
    }

    public void setReady(){
        debugReady = !debugReady;
    }

    @Override
    public void draw(Graphics2D g){
        if (debugReady){
            setMapPosition();
            g.setColor(java.awt.Color.GREEN);
            g.drawString(playerX, GamePanel.WIDTH-150, 20);
            g.drawString(playerY, GamePanel.WIDTH-150, 30);
            g.drawString(playerDX, GamePanel.WIDTH-100, 20);
            g.drawString(playerDY, GamePanel.WIDTH-100, 30);
        }
    }
}
