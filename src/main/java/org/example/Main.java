package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ChessGame game = new ChessGame();

        game.generateChess();

        Player player1 = new Player("玩家A");
        Player player2 = new Player("玩家B");
        game.setPlayers(player1, player2);

        game.startGame();
    }
}
