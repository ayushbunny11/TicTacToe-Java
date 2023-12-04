package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MyGame extends JFrame implements ActionListener {

    JLabel heading, clockLabel;
    Font font1 = new Font("fantasy", Font.ITALIC, 40);
    Font font2 = new Font("", Font.BOLD, 25);

    JPanel mainPanel;
    JButton btns[] = new JButton[9];

    // Game Instance Variables
    int gameChances[] = { 2, 2, 2, 2, 2, 2, 2, 2, 2 };
    int activePlayer = 0;

    // Winning Conditions
    int wp[][] = {
            { 0, 1, 2 },
            { 3, 4, 5 },
            { 6, 7, 8 },
            { 0, 3, 6 },
            { 1, 4, 7 },
            { 2, 5, 8 },
            { 0, 4, 8 },
            { 2, 4, 6 }
    };
    int winner = 2;
    boolean gameOver = false;

    MyGame() {
        System.out.println("You have logged in to the game successfully.");

        super.setTitle("Tic Tac Toe");
        super.setSize(650, 650);
        super.setLocation(550, 140);
        ImageIcon image = new ImageIcon("./Img/tic1.png");
        setIconImage(image.getImage());
        createGUI();
        super.setVisible(true);

        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void createGUI() {

        this.getContentPane().setBackground(Color.BLACK);

        this.setLayout(new BorderLayout());
        heading = new JLabel();
        heading.setFont(font1);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.WHITE);
        heading.setIcon(new ImageIcon("./Img/aven.png"));
        this.add(heading, BorderLayout.NORTH);

        // Clock object
        clockLabel = new JLabel("Clock");
        clockLabel.setFont(font2);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setForeground(Color.WHITE);
        this.add(clockLabel, BorderLayout.SOUTH);

        // Clock Working
        Thread t = new Thread() {
            public void run() {
                try {
                    while (true) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String datetime = LocalDateTime.now().format(formatter);
                        clockLabel.setText(datetime);
                        Thread.sleep(1000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        // Resize the image outside the loop
        ImageIcon originalIcon = new ImageIcon("./Img/logo.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // PANEL
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            JButton btn = new JButton();
            btn.setIcon(scaledIcon);
            btn.setFont(font1);
            btn.setBackground(Color.LIGHT_GRAY);
            mainPanel.add(btn);
            btns[i] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i));
        }
        this.add(mainPanel, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton cb = (JButton) e.getSource();
        String btname = cb.getName();

        int name = Integer.parseInt(btname.trim());

        if (gameOver) {
            JOptionPane.showMessageDialog(this, "Game already over.");
            return;
        }

        if (gameChances[name] == 2) {
            if (activePlayer == 1) {
                cb.setIcon(new ImageIcon("./Img/iron.png"));
                gameChances[name] = activePlayer;
                activePlayer = 0;
            } else {
                cb.setIcon(new ImageIcon("./Img/cap.png"));
                gameChances[name] = activePlayer;
                activePlayer = 1;
            }

            // Find the Winner
            for (int temp[] : wp) {
                if ((gameChances[temp[0]] == gameChances[temp[1]]) && (gameChances[temp[1]] == gameChances[temp[2]])
                        && gameChances[temp[2]] != 2) {
                    winner = gameChances[temp[0]];
                    gameOver = true;
                    if (winner == 0)
                        JOptionPane.showMessageDialog(null, "Captain America wins.");
                    else
                        JOptionPane.showMessageDialog(null, "Iron Man wins.");

                    int optionChoosen = JOptionPane.showConfirmDialog(null, "Do you want to play more?");
                    if (optionChoosen == 0) {
                        this.setVisible(false);
                        new MyGame();
                    } else if (optionChoosen == 1) {
                        System.exit(0);
                    } else {

                    }
                    System.out.println(optionChoosen);

                    break;
                }
            }

            // Draw Logic
            int c = 0;
            for (int x : gameChances) {
                if (x == 2) {
                    c++;
                    break;
                }
            }
            if (c == 0 && gameOver == false) {
                JOptionPane.showMessageDialog(null, "Draw!!!");

                int i = JOptionPane.showConfirmDialog(this, "Do you want to play more??");
                if (i == 0) {
                    this.setVisible(false);
                    new MyGame();
                } else if (i == 1) {
                    System.exit(0);
                } else {

                }
                gameOver = true;
            }

        } else {
            JOptionPane.showMessageDialog(this, "Position already occupied");
        }

    }
}
