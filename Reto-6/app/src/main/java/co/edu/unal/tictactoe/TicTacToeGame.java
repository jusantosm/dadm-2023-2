package co.edu.unal.tictactoe;


import android.util.Log;

/* TicTacToeConsole.java
 * By Frank McCown (Harding University)
 *
 * This is a tic-tac-toe game that runs in the console window.  The human
 * is X and the computer is O.
 */
import java.util.Random;
public class TicTacToeGame {

    public enum DifficultyLevel {Easy, Harder, Expert}

    private DifficultyLevel mDifficultyLevel = DifficultyLevel.Expert;
    public char[] mBoard = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    public static final int BOARD_SIZE = 9;
    public static final char OPEN_SPOT = ' ';

    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';

    public static final String TAG = "game.TicTacToeGame";
    private final Random mRand;

    public TicTacToeGame() {

        // Seed the random number generator
        mRand = new Random();


    }

    public DifficultyLevel getDifficultyLevel() {
        return mDifficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        mDifficultyLevel = difficultyLevel;
    }

    public void clearBoard() {
        for (int i = 0; i < 9; i++) {
            mBoard[i] = OPEN_SPOT;
        }
    }

    public boolean setMove(char player, int location) {
        if (mBoard[location] == ' ') {
            mBoard[location] = player;
            return true;
        }
        return false;
    }

    public int getBoardCellWidth() {
        //return getWidth() / 3;
        return 300/3;
    }
    public int getBoardCellHeight() {
        //return getHeight() / 3;
        return 300/3;
    }
    public char getBoardOccupant(int i){
        return mBoard[i];
    }

    public char[] getBoardState() {
        return mBoard;
    }

    public void setBoardState(char[] mBoard) {
        this.mBoard = mBoard;
    }

    public int checkForWinner() {

        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i + 1] == HUMAN_PLAYER &&
                    mBoard[i + 2] == HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i + 1] == COMPUTER_PLAYER &&
                    mBoard[i + 2] == COMPUTER_PLAYER)
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i + 3] == HUMAN_PLAYER &&
                    mBoard[i + 6] == HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i + 3] == COMPUTER_PLAYER &&
                    mBoard[i + 6] == COMPUTER_PLAYER)
                return 3;
        }

        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER) ||
                (mBoard[2] == HUMAN_PLAYER &&
                        mBoard[4] == HUMAN_PLAYER &&
                        mBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((mBoard[0] == COMPUTER_PLAYER &&
                mBoard[4] == COMPUTER_PLAYER &&
                mBoard[8] == COMPUTER_PLAYER) ||
                (mBoard[2] == COMPUTER_PLAYER &&
                        mBoard[4] == COMPUTER_PLAYER &&
                        mBoard[6] == COMPUTER_PLAYER))
            return 3;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] == OPEN_SPOT) {
                return 0;
            }
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;
    }


    public int getComputerMove() {
        int move = -1;
        if (mDifficultyLevel == DifficultyLevel.Easy)
            move = getRandomMove();
        else if (mDifficultyLevel == DifficultyLevel.Harder) {
            move = getWinningMove();
            if (move == -1)
                move = getRandomMove();
        } else if (mDifficultyLevel == DifficultyLevel.Expert) {
// Try to win, but if that's not possible, block.
// If that's not possible, move anywhere.
            move = getWinningMove();
            if (move == -1)
                move = getBlockingMove();
            if (move == -1)
                move = getRandomMove();
        }
        return move;
    }

    public int getRandomMove() {
        int move;
        // Generate random move
        do {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] == HUMAN_PLAYER
                || mBoard[move] == COMPUTER_PLAYER);

        return move;
    }

    public int getWinningMove() {
        int move = -1;
        // First see if there's a move O can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    Log.d(TAG, "Computer is moving to " + (i + 1));
                    move = i;
                    break;
                } else
                    mBoard[i] = curr;
            }
        }
        return move;
    }

    public int getBlockingMove() {
        int move = -1;
        // See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i]; // Save the current number
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    mBoard[i] = COMPUTER_PLAYER;
                    Log.d(TAG, "Computer is moving to " + (i + 1));
                    move = i;
                    break;
                } else
                    mBoard[i] = curr;
            }
        }
        return move;
    }


}