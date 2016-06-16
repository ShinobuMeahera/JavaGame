import java.awt.Rectangle;

//PLIK GOTOWY

public class Enemy extends Object {
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	protected boolean remove;
	protected boolean facingRight;
	protected Animation animation;
	
	
	public Enemy(TileMap tm) {
		super(tm);
		remove = false;
		animation = new Animation();
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void hit(int damage) {
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		//if(dead) remove = true;
	}
	
	public void update() {}
	
	public void draw(java.awt.Graphics2D g) {
		setMapPosition();
		if(facingRight) {
			g.drawImage( animation.getImage(), (int)(x + xmap - width / 2), 		(int)(y + ymap - height / 2), null );
		}
		else {
			g.drawImage( animation.getImage(), (int)(x + xmap - width / 2 + width), (int)(y + ymap - height / 2), -width, height, null );
		}
		
		// draw collision box
		/*Rectangle r = getRectangle();
		r.x += xmap;
		r.y += ymap;
		g.draw(r);*/
	}
}