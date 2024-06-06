import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 300;
    static final int SCREEN_HEIGHT = 300;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 170;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    private Image backgroundImageEnd;
    private Image backgroundImageInGame;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        loadImage();
        startGame();
    }

    private void loadImage() {
        // Carica le immagini di sfondo
        ImageIcon imageEnd = new ImageIcon(getClass().getResource("/image.png"));
        backgroundImageEnd = imageEnd.getImage();

        ImageIcon imageInGame = new ImageIcon(getClass().getResource("backgroundingame.png"));
        backgroundImageInGame = imageInGame.getImage();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // Disegna l'immagine di sfondo durante il gioco
            if (backgroundImageInGame != null) {
                g.drawImage(backgroundImageInGame, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
            }

            // Disegna la griglia
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            // Disegna la mela
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Disegna il serpente
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(0, 128, 0)); // Testa del serpente più scura
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(0, 100, 0)); // Corpo del serpente più scuro
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            // Disegna il punteggio
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			if (applesEaten == 138) {//lo score di mele mangiate per la vittoria
				running = false;
				timer.stop();
				showWinPanel();
			} else {
				newApple();
			}
		}
    }

    public void checkCollisions() {
        // Controlla se la testa ha toccato il corpo
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // Controlla se la testa ha toccato il bordo sinistro o destro
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH) {
            running = false;
        }

        // Controlla se la testa ha toccato il bordo superiore o inferiore
        if (y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

	private void showWinPanel() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		frame.getContentPane().removeAll();
		frame.add(new WinPanel());
		frame.revalidate();
		frame.repaint();
	}

    public void gameOver(Graphics g) {
        // Disegna l'immagine di sfondo della fine del gioco
        if (backgroundImageEnd != null) {
            g.drawImage(backgroundImageEnd, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
        }

        // Score
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize());
        
        // Restart
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setBounds((SCREEN_WIDTH - 150) /2, SCREEN_HEIGHT -70, 150, 50);

        // Aggiungi il pulsante restart al pannello
        this.setLayout(null);
        this.add(restartButton);

        restartButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        // Aggiunta di un KeyListener per rilevare il tasto Invio
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    restartGame();
                }
            }
        });
        this.requestFocusInWindow();
    
    }

    private void restartGame() {
        // Riavvia il gioco
        applesEaten = 0;
        bodyParts = 6;
        direction = 'R';
        running = true;
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        newApple();
        timer.start();
        this.removeAll();
        repaint();
        requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}