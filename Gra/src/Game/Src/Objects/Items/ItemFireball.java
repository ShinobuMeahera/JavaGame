package Game.Src.Objects.Items;

import Game.Src.Control.Content;
import Game.Src.Map.TileMap.TileMap;
import Game.Src.Objects.Player;

import java.awt.*;

/**
 * Created by Magnus on 10.07.2016.
 */
public class ItemFireball extends ItemParent {

    private int tick;
    private double b;

    private Player player;

    public ItemFireball(TileMap tm, Player pl){
        super(tm);
        player = pl;
        facingRight = true;

        width = 16;
        height = 16;

        cwidth = 16;
        cheight = 16;

        sprites = Content.Fireball[0];

        animation.setFrames(sprites);
        animation.setDelay(-1);

        tick = 0;
        b = Math.random() * 0.06 + 0.07;
    }

    public void update(){
        tick++;

        y = Math.sin(b * tick) + y;

        // update animation
        animation.update();
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        if (player.getSkill(3)) super.drawInHUD(g, 30, 15);
    }
}
