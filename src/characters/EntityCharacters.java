package characters;

import java.awt.Graphics2D;

public interface EntityCharacters {
	void move(int dx, int dy);
	void render(Graphics2D g2d);
}
