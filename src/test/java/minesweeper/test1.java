package minesweeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class test1 {
    Board b1;

    @BeforeEach
    void setUp() {
        b1 = new Board(3,0);
        b1.getTile(1,1).setBomb();
    }

    @Test
    void testCtor(){
        Board b1 = new Board(4,10);

        assertEquals(10,b1.getNumOfBombs());
        assertEquals(4,b1.getSize());
    }

    @Test
    void testRowAndColumn(){
        Tile t1 = b1.getTile(1,1);
        int row = t1.getRow(b1);
        int col = t1.getCol(b1);
        assertEquals(1,row);
        assertEquals(1,col);
        assertTrue(b1.getTile(1,1).isBomb());
    }

    @Test
    void testBombsInProximity() {
        int numOfBombs = b1.getTile(1,1).bombsInProximity(b1);
        assertEquals(1,numOfBombs);
    }

    @Test
    void testNumbers(){
        b1.getTile(1,2).setBomb();
        b1.numbers();
        assertEquals(1,b1.getTile(1,0).getNum());
        assertEquals(2,b1.getTile(2,2).getNum());
    }

    @Test
    void LeaderBoardAddBeginner(){
        LeaderBoard leaderBoard = new LeaderBoard();
        Player p = new Player("testname",20);
        leaderBoard.addPlayer(p,1);

        assertEquals(p,leaderBoard.getBeginners().getFirst());
    }

    @Test
    void LeaderBoardWhenFull(){
        LeaderBoard leaderBoard = new LeaderBoard();
        Player p1 = new Player("testname1",1);
        Player p2 = new Player("testname2",2);
        Player p3 = new Player("testname3",3);
        Player p4 = new Player("testname4",4);
        Player p5 = new Player("testname5",5);

        Player p6 = new Player("testname6",6);

        leaderBoard.addPlayer(p1,1);
        leaderBoard.addPlayer(p2,1);
        leaderBoard.addPlayer(p3,1);
        leaderBoard.addPlayer(p4,1);
        leaderBoard.addPlayer(p5,1);
        leaderBoard.addPlayer(p6,1);

        assertEquals(p5,leaderBoard.getBeginners().getLast());
    }

    @Test
    void LeaderBoardSorted(){
        LeaderBoard leaderBoard = new LeaderBoard();
        Player p1 = new Player("testname1",1);
        Player p2 = new Player("testname2",2);
        Player p3 = new Player("testname3",3);
        Player p4 = new Player("testname4",4);
        Player p5 = new Player("testname5",5);

        leaderBoard.addPlayer(p1,1);
        leaderBoard.addPlayer(p2,1);
        leaderBoard.addPlayer(p3,1);
        leaderBoard.addPlayer(p4,1);

        Collections.shuffle(leaderBoard.getBeginners());

        leaderBoard.addPlayer(p5,1);




        assertEquals(p1,leaderBoard.getBeginners().getFirst());
        assertEquals(p5,leaderBoard.getBeginners().getLast());
    }

    @Test
    void testReveal() {
        Board b1 = new Board(3, 0);
        ButtonWithIndex[][] buttons = new ButtonWithIndex[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new ButtonWithIndex(i,j);
            }
        }
        MouseListener mouseListener = new MouseAdapter() {};
        int res = buttons[1][1].reveal(1, 1, buttons, b1, mouseListener);

        assertEquals(9,res);
    }

    @Test
    void testMenuFrame(){
        LeaderBoard lb = new LeaderBoard();
        MenuFrame mf = new MenuFrame(lb);

        assertTrue(mf.isShowing());
        assertEquals("Minesweeper",mf.getTitle());
        assertFalse(mf.isResizable());
        assertEquals(JFrame.EXIT_ON_CLOSE,mf.getDefaultCloseOperation());
    }

    @Test
    void testGameWonFrame() {
        JFrame fr = new JFrame();
        GameWonFrame gwf = new GameWonFrame(fr, 10, 1, new LeaderBoard());

        assertEquals("Minesweeper",gwf.getTitle());
        assertTrue(gwf.isShowing());
        assertFalse(gwf.isResizable());
        assertEquals(JFrame.EXIT_ON_CLOSE,gwf.getDefaultCloseOperation());
        assertEquals(800,gwf.getWidth());
        assertEquals(400,gwf.getHeight());
    }
}
