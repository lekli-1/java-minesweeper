package minesweeper;

import java.util.List;

public class Tile {
    private boolean bomb = false;
    private int num = 0;

    public boolean isBomb() {
        return bomb;
    }

    public void setBomb(){
        bomb = true;
    }
    public void setNotBomb() {bomb = false;}

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }


    /**
     *
     * @param board the Board containing this tile
     * @return Row idx of tile in the board
     */
    public int getRow(Board board){
        for(List<Tile> row: board.getMatrix()){
            if(row.contains(this)){
                return board.getMatrix().indexOf(row);
            }
        }
        return -1;
    }


    /**
     *
     * @param board the Board containing this tile
     * @return Col idx of tile in the board
     */
    public int getCol(Board board){
        if(this.getRow(board) != -1){
            return board.getMatrix().get(this.getRow(board)).indexOf(this);
        }
        return -1;
    }

    /**
     *
     * @param board he Board containing this tile
     * @return number of bombs in this tile's 1 distance proximity
     */
    public int bombsInProximity(Board board){
        int result = 0;
        int row = getRow(board);
        int col = getCol(board);
        for(int i = row-1; i < row+2 && i < board.getSize(); ++i){
            for(int j = col-1; j<col+2 && j < board.getSize() ;++j){
                if(i >= 0 && j>=0 && board.getTile(i,j).isBomb()){
                    result++;
                }
            }
        }
        return result;
    }
}
