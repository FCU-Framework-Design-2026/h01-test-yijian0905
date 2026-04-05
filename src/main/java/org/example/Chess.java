package org.example;

public class Chess {
    private String name;
    private int weight;
    private int side;
    private int loc;
    private boolean isRevealed;

    public Chess(String name, int weight, int side, int loc){
        this.name = name;
        this.weight = weight;
        this.side = side;
        this.loc = loc;
        this.isRevealed = false;

    }

    public String getName(){
        return this.name;
    }

    public int getSide(){
        return this.side;
    }

    public int getWeight(){
        return this.weight;
    }

    public boolean getIsRevealed(){
        return this.isRevealed;
    }

    public void setRevealed(){
        this.isRevealed = true;
    }

    public String toString(){
        if (!this.isRevealed){
            return "X";
        }else {
            return this.name;
        }
    }
}
