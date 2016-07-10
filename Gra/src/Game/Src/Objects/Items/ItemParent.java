package Game.Src.Objects.Items;

import Game.Src.Control.Animation;
import Game.Src.Map.TileMap.TileMap;
import Game.Src.Objects.ParentObject;

import java.awt.image.BufferedImage;

/**
 * Created by Magnus on 10.07.2016.
 */

public class ItemParent extends ParentObject {

    protected boolean remove;
    protected BufferedImage[] sprites;

    public ItemParent(TileMap tm) {
        super(tm);
        remove = false;
        animation = new Animation();
    }

    public boolean shouldRemove() {
        return remove;
    }

    public void canBeRemoved(){
        remove = true;
    }

    public void update() {}

    public void draw(java.awt.Graphics2D g) {
        super.draw(g);
    }
    public void drawInHUD(java.awt.Graphics2D g, int x, int y){
        g.drawImage( animation.getImage(), x, y, null );
    }
}
