package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class ButtonWithIndex extends JButton {

    int row;
    int col;


    public ButtonWithIndex(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /***
     *
     * @param rowIdx row idx of the tile to be revealed
     * @param colIdx coloumn idx of the tile to be revealed
     * @param buttons matrix of buttons
     * @param board board containing all tiles
     * @param mouseListener mouseListener of buttons to be removed when the button gets revealed
     * @return the number of tiles changed.
     */
    public int reveal(int rowIdx, int colIdx, ButtonWithIndex[][] buttons, Board board, MouseListener mouseListener) {
        int tilesChanged;

        if(rowIdx<0 || colIdx<0 || rowIdx>= buttons.length || colIdx>= buttons.length){
            return 0;
        }
        ButtonWithIndex button = buttons[rowIdx][colIdx];
        if(button.isSelected()){
            return 0;
        }

        if((board.getTile(rowIdx,colIdx).getNum()!=0)){
            button.setText(String.valueOf(board.getTile(rowIdx,colIdx).getNum()));
            button.setSelected(true);
            button.setBackground(Color.gray);
            button.removeMouseListener(mouseListener);
            tilesChanged=1;
            return tilesChanged;
        }
        else {
            button.setSelected(true);
            button.setText("");
            button.setBackground(Color.lightGray);
            button.removeMouseListener(mouseListener);
            tilesChanged=1;
        }


        for(int i = rowIdx-1; i < rowIdx+2; i++){
            for(int j = colIdx-1; j < colIdx+2; j++){
                    tilesChanged += reveal(i, j,buttons,board,mouseListener);
            }

        }
        return tilesChanged;
    }

    /**
     * In case of clicking a bomb, iterates trough the
     * whole board and reveals all of the bombs.
     *
     * @param buttons Matrix of buttons
     * @param board minesweeper.Board containing Tiles
     */
    public void revealAllBombs(ButtonWithIndex[][] buttons, Board board) {
        if(board.getTile(row,col).isBomb()){
            setText("💣");
            setBackground(Color.red);
            setForeground(Color.black);

            for(int i = 0; i<board.getSize(); ++i){
                for(int j = 0; j< board.getSize(); ++j){
                    if(board.getTile(i,j).isBomb()){
                        buttons[i][j].setText("💣");
                        buttons[i][j].setForeground(Color.black);
                    }
                }
            }
        }
    }
}
