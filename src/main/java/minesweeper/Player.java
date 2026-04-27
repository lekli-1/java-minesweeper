package minesweeper;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {
    private String name;
    private int time;

    Player(String name, int time) {
        this.name = name;
        this.time = time;
    }

    @Override
    public int compareTo(Player p) {
        return time - p.time;
    }

    public String toString() {
        return name +" - "+ time +" Seconds";
    }

    public String getName() {
        return name;
    }
    public int getTime() {
        return time;
    }
}
