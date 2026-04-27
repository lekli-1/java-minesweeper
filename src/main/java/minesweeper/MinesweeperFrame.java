package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class MinesweeperFrame extends JFrame {

    private final int size;
    private final int difficulty;
    private int numOfBombs;

    boolean noMercy;
    boolean isNoMercy = false;

    private int time = 0;
    private int tilesRemaining;

    private Board board ;
    private ButtonWithIndex[][] buttons;
    private static LeaderBoard leaderboard = new LeaderBoard();



    private void initComponents() {
        board = new Board(size,numOfBombs);
        buttons = new ButtonWithIndex[size][size];
        board.randomize();
        board.numbers();
        tilesRemaining = (size*size) - numOfBombs;



        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        this.setLayout(new BorderLayout());


        // topPanel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 50, 5, 50));


        JLabel bombCounter = new JLabel();
        bombCounter.setText(String.valueOf(numOfBombs));
        bombCounter.setFont(new Font("Arial", Font.BOLD, 20));

        bombCounter.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        bombCounter.setVerticalTextPosition(SwingConstants.CENTER);
        bombCounter.setHorizontalTextPosition(SwingConstants.CENTER);

        JLabel emoji = new JLabel("😊");
        emoji.setHorizontalAlignment(SwingConstants.CENTER);
        emoji.setFont(new Font("Unicode MS", Font.BOLD, 20));
        emoji.setPreferredSize(new Dimension(50, 50));

        JLabel timePassed = new JLabel(String.valueOf(time));
        timePassed.setFont(new Font("Arial", Font.BOLD, 20));
        timePassed.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        timePassed.setVerticalTextPosition(SwingConstants.CENTER);
        timePassed.setHorizontalTextPosition(SwingConstants.CENTER);
        Timer timer = new Timer(1000, e -> {
            time++;
            timePassed.setText(String.valueOf(time));
        });
        timer.start();

        // BoardPanel
        GridLayout gridLayout = new GridLayout(size, size);
        gridLayout.setVgap(0);
        gridLayout.setHgap(0);
        JPanel boardPanel = new JPanel(gridLayout);
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {

                buttons[i][j] = new ButtonWithIndex(i,j);
                buttons[i][j].setPreferredSize(new Dimension(30, 30));
                buttons[i][j].setMargin(new Insets(0,0,0,0));
                buttons[i][j].setFont(new Font("Unicode MS", Font.BOLD, 20));
                buttons[i][j].setBorder(BorderFactory.createRaisedBevelBorder());

                switch(board.getTile(i,j).getNum()){
                    case 1:{
                        buttons[i][j].setForeground(Color.blue);
                        break;
                    }
                    case 2:{
                        buttons[i][j].setForeground(Color.green);
                        break;
                    }
                    case 3:{
                        buttons[i][j].setForeground(Color.red);
                        break;
                    }
                    case 4:{
                        buttons[i][j].setForeground(new Color(0,0,100));
                        break;
                    }
                    case 5:{
                        buttons[i][j].setForeground(new Color(66,40,14));
                        break;
                    }
                    case 6:{
                        buttons[i][j].setForeground(Color.cyan);
                        break;
                    }
                    case 7:{
                        buttons[i][j].setForeground(Color.black);
                        break;
                    }
                    case 8:{
                        buttons[i][j].setForeground(Color.gray);
                        break;
                    }
                    default: {
                        break;
                    }
                }
                int row = i;
                int col = j;


                buttons[i][j].addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ButtonWithIndex button = (ButtonWithIndex) e.getSource();

                        if(e.getButton() == MouseEvent.BUTTON1){
                            if(isNoMercy){
                                board.getTile(row,col).setBomb();
                            }
                            if(noMercy) {
                                isNoMercy = true;
                                if(board.getTile(row,col).isBomb()){
                                    board.getTile(row,col).setNotBomb();
                                }
                                noMercy = false;
                            }



                            if(button.getText().equals("🚩")){}

                            else if(board.getTile(row,col).isBomb()){
                                button.revealAllBombs(buttons,board);
                                emoji.setText("💀");
                                timer.stop();
                                GameOverFrame fr = new GameOverFrame(MinesweeperFrame.this,leaderboard);
                            }
                            else if(board.getTile(row,col).getNum() != 0){
                                button.setText(String.valueOf(board.getTile(row,col).getNum()));
                                button.setBackground(Color.gray);

                                tilesRemaining--;
                                button.setSelected(true);
                                button.removeMouseListener(this);


                            }
                            else if(board.getTile(row,col).getNum() == 0){
                                tilesRemaining -= button.reveal(row,col,buttons,board,this);
                            }

                        }
                        else if(e.getButton() == MouseEvent.BUTTON3){
                            if(button.getText().equals("🚩")){
                                button.setText("");
                                bombCounter.setText(String.valueOf(++numOfBombs));
                            }
                            else {
                                button.setText("🚩");
                                bombCounter.setText(String.valueOf(--numOfBombs));
                                button.setForeground(Color.red);
                            }
                        }
                        if(tilesRemaining == 0){
                            timer.stop();
                            new GameWonFrame(MinesweeperFrame.this,time,difficulty,leaderboard);
                        }
                    }
                });
                boardPanel.add(buttons[i][j]);
            }
        }



        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem restart = new JMenuItem("Restart");
        restart.addActionListener(e -> {
           new MinesweeperFrame(size, numOfBombs, difficulty, leaderboard, noMercy);
           dispose();
        });

        JMenuItem back = new JMenuItem("Back");
        back.addActionListener(e -> {
            new MenuFrame(leaderboard);
            dispose();
        });

        gameMenu.add(back);
        gameMenu.add(restart);

        JMenu helpMenu = new JMenu("Info");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                URI uri = new URI("https://en.wikipedia.org/wiki/Minesweeper_(video_game)");
                desktop.browse(uri);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JMenuItem help = new JMenuItem("Help");
        help.addActionListener(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                URI uri = new URI("https://minesweepergame.com/strategy/patterns.php");
                desktop.browse(uri);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        helpMenu.add(about);
        helpMenu.add(help);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);


        topPanel.add(bombCounter, BorderLayout.WEST);
        topPanel.add(emoji,BorderLayout.CENTER);
        topPanel.add(timePassed, BorderLayout.EAST);


        this.add(boardPanel, BorderLayout.CENTER);
        this.add(topPanel, BorderLayout.NORTH);
        this.setJMenuBar(menuBar);

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

    MinesweeperFrame(int size, int numOfBombs, int difficulty, LeaderBoard leaderBoard, boolean noMercy) {
        this.size = size;
        this.numOfBombs=numOfBombs;
        this.difficulty = difficulty;
        this.leaderboard = leaderBoard;
        this.noMercy = noMercy;
        initComponents();
        pack();
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setLocation(center.x - getWidth() / 2, center.y - getHeight() / 2);
    }


    public static void main(String[] args){

        String filename = "leaderboard.txt";
        FileInputStream fis = null;
        LeaderBoard lb = null;

        try {
            fis = new FileInputStream(filename);
            ObjectInputStream oin = new ObjectInputStream(fis);

            lb = (LeaderBoard) oin.readObject();
            oin.close();
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        MenuFrame fr = new MenuFrame(lb);
    }
}
