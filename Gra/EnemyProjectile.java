import java.awt.Graphics2D;

public abstract class EnemyProjectile extends Object {
	
	protected boolean hit;
	protected boolean remove;
	protected int damage;
	private boolean facingRight;
	protected Animation animation = new Animation();
	
	public EnemyProjectile(TileMap tm) {
		super(tm);
	}
	
	public int getDamage() { return damage; }
	public boolean shouldRemove() { return remove; }
	
	public abstract void setHit();
	
	public abstract void update();
	
	public void draw(Graphics2D g) {
		setMapPosition();
		if(facingRight) {
			g.drawImage( animation.getImage(), (int)(x + xmap - width / 2), 		(int)(y + ymap - height / 2), null );
		}
		else {
			g.drawImage( animation.getImage(), (int)(x + xmap - width / 2 + width), (int)(y + ymap - height / 2), -width, height, null );
		}
	}
	
}
