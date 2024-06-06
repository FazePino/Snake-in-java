import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeScreen extends JPanel {
    private JFrame parentFrame;

    public WelcomeScreen(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(240, 240, 240)); // Colore di sfondo chiaro

        // Pannello per il titolo
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false); // Trasparenza per consentire il colore di sfondo della finestra
        JLabel titleLabel = new JLabel("BENVENUTO IN 'THIS IS SNAKE BRO'", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        this.add(titlePanel, BorderLayout.NORTH);

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 20)); // Layout a griglia per i pulsanti
        buttonPanel.setOpaque(false); // Trasparenza per consentire il colore di sfondo della finestra

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setBackground(new Color(0, 128, 0)); // Colore verde scuro per il pulsante
        startButton.setForeground(Color.WHITE); // Testo bianco per il pulsante
        startButton.setFocusPainted(false); // Rimuove il bordo di focus
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 30));
        exitButton.setBackground(new Color(128, 0, 0)); // Colore rosso scuro per il pulsante
        exitButton.setForeground(Color.WHITE); // Testo bianco per il pulsante
        exitButton.setFocusPainted(false); // Rimuove il bordo di focus
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        this.add(buttonPanel, BorderLayout.CENTER);
    }

    private void startGame() {
        JFrame gameFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setTitle("THIS IS SNAKE BRO");
        gameFrame.add(new GamePanel());
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        parentFrame.dispose(); // Chiude la finestra iniziale
    }
}