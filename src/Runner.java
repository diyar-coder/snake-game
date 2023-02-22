import java.awt.Color;

import javax.swing.Timer;

public class Runner {

	public static void main(String[] args) {
		SnakeDisplay snake = new SnakeDisplay(Color.cyan);
		Timer timer = new Timer(70, snake);
		timer.start();
	}

}
