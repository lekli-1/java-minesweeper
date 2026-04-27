package minesweeper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MenuFrame extends JFrame {

    int difficulty;
    boolean noMercy;
    private LeaderBoard leaderboard;

    public void initComponents() {

        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        this.setLayout(new BorderLayout());


        JPanel difficultyPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Select Difficulty");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        difficultyPanel.add(label, BorderLayout.NORTH);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        Border b1 = BorderFactory.createEmptyBorder(30, 30, 30, 30);
        Border b2 = BorderFactory.createRaisedSoftBevelBorder();
        Border compoundBorder = BorderFactory.createCompoundBorder(b2,b1);

        JButton beginner = new JButton("<html><p align = center>Beginner<br>8x8<br>10 bombs</p></html>");
        beginner.setBorder(compoundBorder);
        beginner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                difficulty = 1;
                new MinesweeperFrame(8,10,1,leaderboard,noMercy);
                dispose();
            }
        });

        JButton intermediate = new JButton("<html><p align = center>Intermediate<br>16x16<br>40 bombs</p></html>");
        intermediate.setBorder(compoundBorder);
        intermediate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                difficulty = 2;
                new MinesweeperFrame(16,40,2,leaderboard,noMercy);
                dispose();
            }
        });

        JButton expert = new JButton("<html><p align = center>Expert<br>24x24<br>99 bombs</p></html>");
        expert.setBorder(compoundBorder);
        expert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                difficulty = 3;
                new MinesweeperFrame(24,99,3,leaderboard,noMercy);
                dispose();
            }
        });

        difficultyPanel.add(beginner, BorderLayout.WEST);
        difficultyPanel.add(intermediate, BorderLayout.CENTER);
        difficultyPanel.add(expert, BorderLayout.EAST);

        JPanel modePanel = new JPanel(new FlowLayout());
        JCheckBox noMercyMode = new JCheckBox("No mercy mode");
        noMercyMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                noMercy = true;
            }
        });

        JButton leaderBoard = new JButton("Leaderboard");
        leaderBoard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LeaderBoardFrame(leaderboard);
            }
        });
        modePanel.add(noMercyMode);
        modePanel.add(leaderBoard);

        this.add(difficultyPanel, BorderLayout.CENTER);
        this.add(modePanel, BorderLayout.SOUTH);

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

    public MenuFrame(LeaderBoard leaderBoard) {
        this.leaderboard = leaderBoard;
        initComponents();
        pack();
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setLocation(center.x - getWidth() / 2, center.y - getHeight() / 2);
    }
}
