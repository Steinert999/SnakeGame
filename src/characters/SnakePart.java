package characters;

import java.awt.Graphics2D;

public class SnakePart extends EntityCharactersImpl{

	public SnakePart(int xCoor, int yCoor, int size) {
		super(xCoor, yCoor, size);
	}
	
	public SnakePart(int xCoor, int yCoor) {
		super(xCoor, yCoor);
	}
	
	@Override
	public void render(Graphics2D g2d) {
		super.render(g2d);
	}
}
