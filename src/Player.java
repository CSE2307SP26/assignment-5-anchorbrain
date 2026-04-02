import edu.princeton.cs.introcs.StdDraw;

import java.awt.event.KeyEvent;
import java.awt.Color;

public class Player {
    
    final static double speed = 0.01;
    final static double radius = 0.025;

    private double x;
	private double y;

    public Player() {
        this.x = 0.5;
		this.y = 0.5;
    }

    public double get_x() {
        return this.x;
    }

    public double get_y() {
        return this.y;
    }

    public void set_x(double x) {
        this.x = x;
    }

    public void set_y(double y) {
        this.y = y;
    }

    public void handle_input() {
		if(StdDraw.isKeyPressed(KeyEvent.VK_W)) {
			y = y + speed;
		}
		if(StdDraw.isKeyPressed(KeyEvent.VK_S)) {
			y = y - speed;
		}
		if(StdDraw.isKeyPressed(KeyEvent.VK_A)) {
			x = x - speed;
		}
		if(StdDraw.isKeyPressed(KeyEvent.VK_D)) {
			x = x + speed;
		}
	}

	public void handle_bounds() {
		if(x > 1) {
			x = 1;
		}
		if(x < 0) {
			x = 0;
		}
		if(y > 1) {
			y = 1;
		}
		if(y < 0) {
			y = 0;
		}
	}

    public void draw() {
		StdDraw.setPenColor(Color.black);
		StdDraw.filledCircle(x, y, radius);
	}
}
