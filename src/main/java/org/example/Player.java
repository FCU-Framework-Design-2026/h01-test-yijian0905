package org.example;

public class Player {
    private String name;
    private int side;

    public Player(String name){
        this.name = name;
        this.side = 0;  // 0 = undefined, 1 = red, 2 = black
    }

    public String getName(){
        return this.name;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }
}
