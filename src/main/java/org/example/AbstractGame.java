package org.example;

public abstract class AbstractGame {
    public abstract void setPlayers(Player p1, Player p2);

    public abstract boolean gameOver();

    public abstract boolean move(int location);
}
