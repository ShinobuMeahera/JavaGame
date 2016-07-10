package Game.Src.Control;

import Game.Src.Map.TileMap.TileMap;
import Game.Src.Objects.ParentObject;
import Game.Src.Objects.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Magnus on 06.07.2016.
 */
public class HUD extends ParentObject {

    private BufferedImage hpBar = null;
    private BufferedImage mpBar = null;
    private BufferedImage staBar = null;
    private BufferedImage hudBar = null;

    private static final String HPBAR = "/Game/Src/Assets/hp-bar.png";
    private static final String FIREBAR = "/Game/Src/Assets/fireball-bar.png";
    private static final String DASHBAR = "/Game/Src/Assets/dash-bar.png";
    private static final String HUD = "/Game/Src/Assets/hud.png";

    private Player player;
    public HUD(TileMap tm){
        super(tm);

        try{
            hpBar = ImageIO.read( getClass().getResourceAsStream(HPBAR));
            mpBar = ImageIO.read( getClass().getResourceAsStream(FIREBAR));
            staBar = ImageIO.read( getClass().getResourceAsStream(DASHBAR));
            hudBar = ImageIO.read( getClass().getResourceAsStream(HUD));
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error loading graphics from HUD.");
            System.exit(0);
        }
    }

    public void init(Player p){
        player = p;
    }

    public void draw(Graphics2D g) {
        g.drawImage(hpBar, (player.getHealth()*2) - 75, 15, null);
        g.drawImage(mpBar, (player.getMana()) - 75, 15+16, null);
        g.drawImage(staBar, (player.getSta()/2) - 75, 15+32, null);
        g.drawImage(hudBar, 0, 13, null);
    }
}
