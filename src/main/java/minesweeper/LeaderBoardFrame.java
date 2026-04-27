package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LeaderBoardFrame extends JFrame {
    private LeaderBoard leaderboard;

    private void initComponents(){
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        this.setLayout(new BorderLayout());

        JPanel leaderBoardPanel = new JPanel(new BorderLayout());
        leaderBoardPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        JLabel label = new JLabel("Leaderboard");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        leaderBoardPanel.add(label, BorderLayout.NORTH);

        JLabel beginnerLabel = new JLabel();
        beginnerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        beginnerLabel.setForeground(Color.BLACK);
        beginnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        beginnerLabel.setVerticalAlignment(SwingConstants.CENTER);
        beginnerLabel.setFocusable(false);
        beginnerLabel.setText(leaderboard.beginnersToString());

        JLabel intermediateLabel = new JLabel();
        intermediateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        intermediateLabel.setForeground(Color.BLACK);
        intermediateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        intermediateLabel.setVerticalAlignment(SwingConstants.CENTER);
        intermediateLabel.setFocusable(false);
        intermediateLabel.setText(leaderboard.intermediatesToString());

        JLabel expertLabel = new JLabel();
        expertLabel.setFont(new Font("Arial", Font.BOLD, 20));
        expertLabel.setForeground(Color.BLACK);
        expertLabel.setHorizontalAlignment(SwingConstants.CENTER);
        expertLabel.setVerticalAlignment(SwingConstants.CENTER);
        expertLabel.setFocusable(false);
        expertLabel.setText(leaderboard.expertsToString());

        leaderBoardPanel.add(beginnerLabel, BorderLayout.NORTH);
        leaderBoardPanel.add(intermediateLabel, BorderLayout.CENTER);
        leaderBoardPanel.add(expertLabel, BorderLayout.SOUTH);

        JLabel title = new JLabel("Leaderboard");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusable(false);
        backButton.setHorizontalAlignment(SwingConstants.CENTER);
        backButton.setVerticalAlignment(SwingConstants.CENTER);
        backButton.addActionListener(e -> {
           dispose();
        });

        this.add(leaderBoardPanel, BorderLayout.CENTER);
        this.add(title, BorderLayout.NORTH);
        this.add(backButton, BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                try{
                    String filename = "leaderboard.txt";
                    FileOutputStream fout = new FileOutputStream(filename);
                    ObjectOutputStream oout = new ObjectOutputStream(fout);

                    oout.writeObject(leaderboard);

                    oout.close();
                    fout.close();

                } catch (FileNotFoundException exp) {
                    throw new RuntimeException(exp);
                } catch (IOException exp) {
                    throw new RuntimeException(exp);
                }
            }
        });
    }

    public LeaderBoardFrame(LeaderBoard leaderBoard) {
        this.leaderboard = leaderBoard;
        initComponents();
        pack();
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setLocation(center.x - getWidth() / 2, center.y - getHeight() / 2);
    }
}
