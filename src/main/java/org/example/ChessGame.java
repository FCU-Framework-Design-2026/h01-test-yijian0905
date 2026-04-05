package org.example;
import java.util.Scanner;

public class ChessGame extends AbstractGame {

    @Override
    public void setPlayers(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
    }

    @Override
    public boolean gameOver() {
        int redAlive = 0;
        int blackAlive = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null){
                    if (board[i][j].getSide() == 1){
                        redAlive++;
                    } else if (board[i][j].getSide() == 2) {
                        blackAlive++;
                    }
                }
            }
        }

        if (redAlive == 0){
            System.out.println("黑方獲勝！紅方棋子已被吃光");
            return true;
        } else if (blackAlive == 0) {
            System.out.println("紅方獲勝！黑方棋子已被吃光");
            return true;
        }

        return false;
    }

    @Override
    public boolean move(int location) {
        return false;
    }

    private Chess[][] board = new Chess[4][8];
    private Player p1;
    private Player p2;
    private Player currentPlayer;

    public void generateChess(){
        Chess[] pool = new Chess[32];
        int index = 0;

        String[] redNames = {"帥","仕","相","俥","傌","炮","兵"};
        String[] blackNames= {"將","士","象","車","馬","包","卒"};
        // 根據存放棋子的陣列順序定義不同棋子的權重
        int[] weights = {7,6,5,4,3,2,1};
        // 根據不同階級的棋子定義其需要印出的數量
        int[] counts = {1,2,2,2,2,2,5};

        // 外層迴圈：走訪7種不同階級的棋子
        for (int i = 0; i < 7; i++) {
            //内層迴圈：根據 count[i] 的數量決定要印出幾顆棋子
            for (int j = 0; j < counts[i]; j++) {
                // 將產生的棋子存入pool裡
                pool[index] = new Chess(redNames[i], weights[i], 1, 0);
                index++;

                pool[index] = new Chess(blackNames[i], weights[i], 2, 0);
                index++;
            }
        }

        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < pool.length; i++) {
            int randomIndex = rand.nextInt(pool.length);

            // 將目前位置 (i) 的棋子與隨機位置 (randomIndex) 的棋子互換
            Chess temp = pool[i]; // 將目前位置（i）的棋子存入temp
            pool[i] = pool[randomIndex]; // 將當前位置的棋子替換成隨機位置的棋子
            pool[randomIndex] = temp; // 將隨機位置的棋子替換成temp裡存放的棋子
        }

        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = pool[count]; // 將pool裡的棋子放入board[i][j]
                count++;
            }
        }
    }

    public void showAllChess(){
        System.out.print("  ");
        for (int col = 1; col <= 8; col++) {
            System.out.print(col);
            if (col < 8){
                System.out.print(" ");
            }else {
                System.out.println();
            }
        }

        String[] rowLetters = {"A", "B", "C", "D"};

        for (int i = 0; i < rowLetters.length; i++) {
            System.out.print(rowLetters[i] + " ");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null){
                    System.out.print("  ");
                }else {
                    System.out.print(board[i][j]);
                }
                if (j < 7){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void switchPlayer(){
        if (currentPlayer == p1){
            currentPlayer = p2;
        }else {
            currentPlayer = p1;
        }
    }

    public int[] convertCoordinate(String input){
        input = input.toUpperCase(); // 將輸入字串統一轉換成大寫（避免玩家輸入小寫導致程式宕機）

        // 取input的第一個字元將其轉換成 row 索引
        char rowChar = input.charAt(0);
        int row = rowChar - 'A';

        // 取input的第二個字元將其轉換成 col 索引
        char colChar = input.charAt(1);
        int col = colChar - '1';

        // 將換算好的 row 和 col 放進陣列回傳
        return new int[]{row, col};
    }

    public void startGame(){
        Scanner scanner = new Scanner(System.in);

        while (!gameOver()){
            showAllChess();

            System.out.println("================================");
            System.out.println("現在輪到：" + currentPlayer.getName());
            System.out.print("請輸入要操作的棋子坐標：");
            String input = scanner.nextLine();

            int[] pos = convertCoordinate(input);
            int row = pos[0];
            int col = pos[1];

            Chess target = board[row][col];
            if (!target.getIsRevealed()){
                target.setRevealed();

                if (currentPlayer.getSide() == 0){
                    currentPlayer.setSide(target.getSide());

                    if (currentPlayer.getSide() == 1){
                        p2.setSide(2);
                    }else {
                        p2.setSide(1);
                    }
                }
                switchPlayer();
            }else {
                if (currentPlayer.getSide() == target.getSide()){
                    System.out.print("請選擇要移動到的坐標：");
                    String destInput = scanner.nextLine();

                    int[] destPos = convertCoordinate(destInput);
                    int destRow = destPos[0];
                    int destCol = destPos[1];

                    int rowDiff = Math.abs(row - destRow);
                    int colDiff = Math.abs(col - destCol);

                    Chess destPiece = board[destRow][destCol];
                    //  判斷移動的合法性
                    if(rowDiff + colDiff == 1){
                        //基礎移動
                        if (destPiece == null){
                            board[destRow][destCol] = target;
                            board[row][col] = null;
                            switchPlayer();
                        } else if (!destPiece.getIsRevealed()) {
                            System.out.println("不能移動到未翻開的棋子上，請重新操作");
                        } else if (destPiece.getSide() == currentPlayer.getSide()) {
                            System.out.println("不能吃自己的棋子，請重新操作");
                        }else {
                            //吃子邏輯
                            if (target.getWeight() == 1 && destPiece.getWeight() == 7){
                                board[destRow][destCol] = target;
                                board[row][col] = null;
                                System.out.println("你吃掉了對方的" + destPiece.getName());
                                switchPlayer();
                            }else if (target.getWeight() == 7 && destPiece.getWeight() == 1){
                                System.out.println("你的棋子被對方剋制，吃子失敗！請重新操作");
                            }else if (target.getWeight() == 2){
                                if (target.getSide() == 1){
                                    System.out.println("炮吃子必須間隔一個棋子。吃子失敗，請重新操作");
                                } else if (target.getSide() == 2) {
                                    System.out.println("包吃子必須間隔一個棋子。吃子失敗，請重新操作");
                                }
                            }else if (target.getWeight() >= destPiece.getWeight()){
                                board[destRow][destCol] = target;
                                board[row][col] = null;
                                System.out.println("你吃掉了對方的" + destPiece.getName());
                                switchPlayer();
                            }else {
                                System.out.println("棋子的階級比對方的小，吃子失敗！請重新操作");
                            }
                        }
                    }else if (target.getWeight() == 2){
                        if (row == destRow || col == destCol){
                            if (destPiece == null || !destPiece.getIsRevealed() || destPiece.getSide() == currentPlayer.getSide()){
                                System.out.println("炮/包只能跳吃已翻開的敵方棋子！請重新選擇");
                            }else{
                                int count = 0;  //中間障礙物計數
                                if (row == destRow){
                                    int minCol = Math.min(col,destCol);
                                    int maxCol = Math.max(col,destCol);

                                    for (int i = minCol+1; i < maxCol; i++) {
                                        if (board[row][i] != null){
                                            count++;
                                        }
                                    }
                                } else{
                                    int minRow = Math.min(row,destRow);
                                    int maxRow = Math.max(row,destRow);

                                    for (int i = minRow+1; i < maxRow; i++) {
                                        if (board[i][col] != null){
                                            count++;
                                        }
                                    }
                                }

                                if (count == 1){
                                    board[destRow][destCol] = target;
                                    board[row][col] = null;
                                    System.out.println("你飛越障礙吃掉了對方的 " + destPiece.getName());
                                    switchPlayer();
                                }else {
                                    System.out.println("炮吃子中間必須剛好隔一顆棋子！請重新操作");
                                }
                            }
                        }else {
                            System.out.println("棋子不能斜著走，請重新選擇！");
                        }
                    }else {
                        System.out.println("棋子只能上下左右移動，請重新選擇！");
                    }

                }else {
                    System.out.println("這不是你的棋子，請重新選擇");
                }
            }

            System.out.println("================================");
        }
    }
}
