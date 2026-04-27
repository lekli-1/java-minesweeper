package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameOverFrame extends JFrame {

    JFrame Ancestor;
    LeaderBoard leaderboard;

    public void initComponents(){
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setPreferredSize(new Dimension(400, 200));
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel msg = new JLabel("Game Over, Better luck next time!");
        msg.setFont(new Font("Arial", Font.BOLD, 20));
        msg.setForeground(Color.BLACK);
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        msg.setVerticalAlignment(SwingConstants.CENTER);

        topPanel.add(msg);

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

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
        centerPanel.add(back);
        centerPanel.add(exit);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);

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

    public GameOverFrame(JFrame Ancestor, LeaderBoard leaderBoard) {
        this.leaderboard = leaderBoard;
        this.Ancestor = Ancestor;
        initComponents();
        pack();
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setLocation(center.x - getWidth() / 2, center.y - getHeight() / 2);
    }
}
