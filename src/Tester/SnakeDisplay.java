package Tester;


import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class SnakeDisplay extends JComponent implements KeyListener, ActionListener {

	public static final int CELL_SIZE = 15;
	public static final int NUM_ROWS = 50;
	public static final int NUM_COLS = 50;

	// The color of the snake.
	private Color color;

	// The list of Nodes that make up the snake.
	private LinkedList<Node> body;

	// The direction that the snake is traveling in the x direction (across
	// columns).
	private int directionX;

	// The direction that the snake is traveling in the y direction (across rows).
	private int directionY;

	// The image that we will draw to the frame.
	private Image image;

	// The frame that will contain the image.
	private JFrame frame;

	// The node that represents the apple.
	private Node apple;

	public SnakeDisplay(Color c) {
		color = c;
		body = new LinkedList<Node>();
		Node head = new Node((NUM_ROWS / 2), (NUM_COLS / 2));
		body.addFirst(head);
		directionX = 0;
		directionY = -1;
		image = new BufferedImage(NUM_COLS * CELL_SIZE, NUM_ROWS * CELL_SIZE, BufferedImage.TYPE_INT_RGB);
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimension = new Dimension(NUM_COLS * CELL_SIZE, NUM_ROWS * CELL_SIZE);
		this.setPreferredSize(dimension);
		frame.add(this);
		frame.addKeyListener(this);
		frame.pack();
		frame.setVisible(true);
		spawnApple();

	}
	// This method randomly generates a new location for the apple and paints it to
	// the game grid.

	private void spawnApple() {
		int randomRow = (int) (Math.random() * NUM_ROWS);
		int randomCol = (int) (Math.random() * NUM_COLS);
		apple = new Node(randomRow, randomCol);
		paint(randomRow, randomCol, Color.red);

	}

	// This method paints a row and column of our game grid a certain color.
	private void paint(int row, int col, Color color) {
		Graphics graphics;
		graphics = image.getGraphics();
		graphics.setColor(color);
		graphics.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
	}

	// This method will paint our image to the frame. This method is part of the
	// JComponent class, so make sure you spell everything correctly.
	public void paintComponent(Graphics g) {

		g.drawImage(image, 0, 0, null);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// These two if statements make sure that if the snake is going down, it
		// shouldn't be able to go up
		// otherwise it will count as running into itself and the game will end
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (directionY != 1) {
				directionY = -1;
				directionX = 0;

			}

		}
		// These two if statements make sure that if the snake is going up, it shouldn't
		// be able to go down
		// otherwise it will count as running into itself and the game will end
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (directionY != -1) {
				directionY = 1;
				directionX = 0;
			}

		}
		// These two if statements make sure that if the snake is going right, it
		// shouldn't be able to go left
		// otherwise it will count as running into itself and the game will end
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (directionX != 1) {
				directionX = -1;
				directionY = 0;
			}
		}
		// These two if statements make sure that if the snake is going left, it
		// shouldn't be able to go right
		// otherwise it will count as running into itself and the game will end
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (directionX != -1) {
				directionX = 1;
				directionY = 0;
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// This method has all of the game logic and will be called every time the game
		// is played.
		Node head = body.peekFirst();
		Node tail = body.peekLast();
		Node newHead = new Node(head.getRows() + directionY, head.getColumns() + directionX);
		for (Node i : body) {
			if (newHead.getRows() == i.getRows() && newHead.getColumns() == i.getColumns()) {
				System.out.println("GAME OVER");
				System.exit(0);
			}
		}

		if (newHead.getRows() == -1 || newHead.getRows() == 50 || newHead.getColumns() == -1
				|| newHead.getColumns() == 50) {
			System.out.println("GAME OVER");
			System.exit(0);
		}

		body.addFirst(newHead);
		if (newHead.getColumns() == apple.getColumns() && newHead.getRows() == apple.getRows()) {
			spawnApple();
		} else {
			body.pollLast();
			paint(tail.getRows(), tail.getColumns(), Color.black);
		}

		for (Node i : body) {
			paint(i.getRows(), i.getColumns(), color);
		}
		frame.repaint();
	}

}