package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameWonFrame extends JFrame {
    JFrame Ancestor;
    private final int time;
    private final int difficulty;
    private LeaderBoard leaderboard;

    public void initComponents(){
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(800, 400));
        this.setLayout(new BorderLayout());

        //topPanel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel msg = new JLabel("<html><p align = center>Congratulations!, You Won!<br>Your Time Was:</p></html>");
        msg.setFont(new Font("Arial", Font.BOLD, 20));
        msg.setForeground(Color.BLACK);
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        msg.setVerticalAlignment(SwingConstants.CENTER);

        JLabel timeofplay = new JLabel(String.valueOf(time + " Seconds"));
        timeofplay.setFont(new Font("Times New Roman", Font.BOLD, 20));
        timeofplay.setForeground(Color.BLACK);
        timeofplay.setHorizontalAlignment(SwingConstants.CENTER);
        timeofplay.setVerticalAlignment(SwingConstants.CENTER);

        JLabel msg2 = new JLabel("If you want to submit your time to the leaderboards, enter your name:");
        msg2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        msg2.setForeground(Color.BLACK);
        msg2.setHorizontalAlignment(SwingConstants.CENTER);
        msg2.setVerticalAlignment(SwingConstants.CENTER);


        topPanel.add(msg, BorderLayout.NORTH);
        topPanel.add(timeofplay, BorderLayout.CENTER);
        topPanel.add(msg2, BorderLayout.SOUTH);

        //centerPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel nameLabel = new JLabel("Your Name: ");
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        nameLabel.setForeground(Color.BLACK);


        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        nameField.setFont(new Font("Times New Roman", Font.BOLD, 20));
        nameField.setForeground(Color.BLACK);
        nameField.setEditable(true);

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Times New Roman", Font.BOLD, 20));
        submit.setForeground(Color.BLACK);
        submit.setHorizontalAlignment(SwingConstants.CENTER);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                Player p = new Player(name,time);
                leaderboard.addPlayer(p,difficulty);
                new MenuFrame(leaderboard);
                dispose();
                Ancestor.dispose();
            }
        });


        centerPanel.add(nameLabel);
        centerPanel.add(nameField);
        centerPanel.add(submit);


        //bottomPanel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JButton back = new JButton("Main Menu");
        back.setFont(new Font("Arial", Font.BOLD, 20));
        back.addActionListener(e -> {
            new MenuFrame(leaderboard);
            dispose();
            Ancestor.dispose();
        });

        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.BOLD, 20));
        exit.addActionListener(e -> System.exit(0));
        bottomPanel.add(back);
        bottomPanel.add(exit);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

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

    public GameWonFrame(JFrame Ancestor, int time, int difficulty, LeaderBoard leaderBoard) {
        this.Ancestor = Ancestor;
        this.time = time;
        this.difficulty = difficulty;
        this.leaderboard = leaderBoard;
        initComponents();
        pack();
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setLocation(center.x - getWidth() / 2, center.y - getHeight() / 2);
    }
}
