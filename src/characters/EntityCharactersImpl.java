package characters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class EntityCharactersImpl implements EntityCharacters {
	
	private int xCoor, yCoor, size;
	private Color color = Color.YELLOW;
	
	public static final int SIZE = 15;

	public EntityCharactersImpl(int xCoor, int yCoor, int size) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.size = size;
	}
	
	public EntityCharactersImpl(int xCoor, int yCoor) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.size = SIZE;
	}
	
	@Override
	public void move(int dx, int dy) {
		this.xCoor += dx;
		this.yCoor += dy;
	}
	
	public Rectangle getBound() {
		return new Rectangle(xCoor, yCoor, size, size);
	}
	
	public boolean isCollision(EntityCharactersImpl o) {
		if( o == this) return false;
		return getBound().intersects(o.getBound());
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.drawRect(xCoor + 1, yCoor + 1, size - 2, size - 2 );
	}

	public int getxCoor() {
		return xCoor;
	}



	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}



	public int getyCoor() {
		return yCoor;
	}



	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}



	public int getSize() {
		return size;
	}



	public void setSize(int size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
