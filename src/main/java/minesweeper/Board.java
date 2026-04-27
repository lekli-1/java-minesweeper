package minesweeper;

import java.util.*;

public class Board {
    private List<List<Tile>> matrix;
    private int numOfBombs;


    /**
     *
     * @param n height and width of n*x matrix
     * @param bombs number of bombs within the board
     */
    public Board(int n, int bombs){
        matrix = new ArrayList<>();
        numOfBombs = bombs;

        for(int i = 0; i<n; ++i){
            matrix.add(new ArrayList<>());
            for(int j = 0; j<n; ++j){
                matrix.get(i).add(new Tile());
            }
        }

        int bombsplaced=0;
        while(bombsplaced < numOfBombs){
            Random rand = new Random();
            int randIdx = rand.nextInt(matrix.size());


            for(int i = 0; i < matrix.size(); ++i){
                if(!matrix.get(randIdx).get(i).isBomb()){
                    matrix.get(randIdx).get(i).setBomb();
                    bombsplaced++;
                    break;
                }
            }

        }

    }


    /**
     * randomizes the board using Collections.shuffle.
     */
    public void randomize(){
        for(List<Tile> row: matrix){
            Collections.shuffle(row);
        }
    }

    /**
     * sets the number for each tile in the board
     */
    public void numbers(){
        for(List<Tile> row: matrix){
            for(Tile tile: row){
                if(!tile.isBomb()) {
                    tile.setNum(tile.bombsInProximity(this));
                }
            }
        }
    }

    public List<List<Tile>> getMatrix() {
        return matrix;
    }

    public int getSize(){ return matrix.size(); }

    /**
     *
     * @param row row idx of tile
     * @param col col idx of tile
     * @return returns the tile found on these coordinates within the matrix.
     */
    public Tile getTile(int row, int col){
        return matrix.get(row).get(col);
    }

    public int getNumOfBombs(){return numOfBombs;}
}
