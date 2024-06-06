import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinPanel extends JPanel {
    private Image backgroundImage;

    public WinPanel() {
        setPreferredSize(new Dimension(400, 200)); // Dimensioni più ampie per la finestra di vittoria
        setLayout(new BorderLayout());

        // Carica l'immagine di sfondo
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/winGameImage.png"));
        backgroundImage = imageIcon.getImage();

        JLabel winLabel = new JLabel("Bravo! Hai vinto!", SwingConstants.CENTER);
        winLabel.setForeground(Color.RED);
        winLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Ridimensiona il font per adattarlo alla finestra

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);

        add(winLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void restartGame() {
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        currentFrame.dispose(); // Chiudi il frame corrente
    
        // Crea un nuovo frame con il GamePanel
        JFrame newFrame = new JFrame();
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setResizable(false);
        newFrame.setTitle("THIS IS SNAKE BRO");
        newFrame.add(new GamePanel());
        newFrame.pack();
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Disegna l'immagine di sfondo
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}