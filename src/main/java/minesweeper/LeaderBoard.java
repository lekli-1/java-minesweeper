package minesweeper;

import java.io.Serializable;
import java.util.*;

public class LeaderBoard implements Serializable {
    List<Player> beginners;
    List<Player> intermediates;
    List<Player> experts;

    LeaderBoard() {
        beginners = new ArrayList<>();
        intermediates = new ArrayList<>();
        experts = new ArrayList<>();
    }

    /***
     *
     * @param p Player to be added to the LeaderBoard
     * @param difficulty which difficulty the player will be added to.
     */
    public void addPlayer(Player p, int difficulty){

        Collections.sort(beginners);
        Collections.sort(intermediates);
        Collections.sort(experts);

        switch(difficulty){
            case 1:{
                if(beginners.size()<5){
                    beginners.add(p);
                    Collections.sort(beginners);
                }
                else if(beginners.getLast().getTime()>p.getTime()){
                    beginners.removeLast();
                    beginners.add(p);
                    Collections.sort(beginners);

                }
                break;
            }
            case 2:{
                if(intermediates.size()<5){
                    intermediates.add(p);
                    Collections.sort(intermediates);
                }
                else if(intermediates.getLast().getTime()>p.getTime()){
                    intermediates.removeLast();
                    intermediates.add(p);
                    Collections.sort(intermediates);
                }
                break;
            }
            case 3:{
                if(experts.size()<5){
                    experts.add(p);
                    Collections.sort(experts);
                }
                else if(experts.getLast().getTime()>p.getTime()){
                    experts.removeLast();
                    experts.add(p);
                    Collections.sort(experts);
                }
                break;
            }
            default:{
                break;
            }
        }
    }

    public List<Player> getBeginners(){
        return beginners;
    }
    public List<Player> getIntermediates(){
        return intermediates;
    }
    public List<Player> getExperts(){
        return experts;
    }

    public String beginnersToString(){
        String result = "<html><p align = center>";
        result += "Beginners:<br>";
        for(Player p : beginners){
            result += p.toString() + "<br>";
        }
        result += "----------";
        result += "</p></html>";
        return result;
    }

    public String intermediatesToString(){
        String result = "<html><p align = center>";
        result += "Intermediates:<br>";
        for(Player p : intermediates){
            result += p.toString() + "<br>";
        }
        result += "----------";
        result += "</p></html>";
        return result;
    }

    public String expertsToString(){
        String result = "<html><p align = center>";
        result += "Experts:<br>";
        for(Player p : experts){
            result += p.toString() + "<br>";
        }
        result += "----------";
        result += "</p></html>";
        return result;
    }


}