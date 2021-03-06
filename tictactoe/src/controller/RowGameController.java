package controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.html.BlockView;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import view.RowGameGUI;

public class RowGameController {
    private RowGameModel gameModel;
    private RowGameGUI gameView;
	private static final int ROWS = 3;
    private static final int COLUMNS = 3;
	private static int lastMoveRow = -1;
	private static int lastMoveColumn = -1;
	private static boolean recentUndoFlag = false;
    
	/**
     * Creates a new game initializing the GUI.
     */  
    public RowGameController() {
	gameModel = new RowGameModel();
	gameView = new RowGameGUI(this);

        for(int row = 0; row<3; row++) {
            for(int column = 0; column<3 ;column++) {
	        gameModel.getBlockModel()[row][column].setContents("");
		gameModel.getBlockModel()[row][column].setIsLegalMove(true);
		gameView.updateBlock(gameModel,row,column);
            }
        }
    }

    /**
     * Moves the current player into the given block.
     *
     * @param block The block to be moved to by the current player
     */
    public void move(JButton block) {
		if(!block.getText().isEmpty()){
			if(!gameView.getPlayerturn().getText().contains("This box has already been taken | ")){
				gameView.getPlayerturn().setText("This box has already been taken | " + gameView.getPlayerturn().getText());
			}
		}else{
			gameModel.setMovesLeft(gameModel.getMovesLeft() - 1);
			recentUndoFlag = false;
			if(gameModel.getMovesLeft() % 2 == 1) {
				gameView.getPlayerturn().setText("'X': Player 1");
			} else{
				gameView.getPlayerturn().setText("'O': Player 2");
			}
			
			if(gameModel.getCurentPlayer().equals("1")) {
				// Check whether player 1 won
				checkWinner(block, "X", "Player 1 wins!");
			} else {
				// Check whether player 2 won
				checkWinner(block, "O", "Player 2 wins!");
			}
		}
    }


	/**
     * Undos the prevous move if called upon
     */
    public void undoLastMove() {
		if(lastMoveRow == -1 || lastMoveColumn == -1 || gameModel.getMovesLeft() == 9){
			gameView.getPlayerturn().setText("You cannot undo when no moves have been played!");
		} else if(gameModel.getFinalResult() != null){
			gameView.getPlayerturn().setText("You cannot undo a move after the game has finished!");
		} else if(recentUndoFlag){
			gameView.getPlayerturn().setText("You already undid your prevous move.");
		}else{
			recentUndoFlag = true;
			gameModel.getBlockModel()[lastMoveRow][lastMoveColumn].setContents("");
			gameModel.getBlockModel()[lastMoveRow][lastMoveColumn].setIsLegalMove(true);
			gameModel.setMovesLeft(gameModel.getMovesLeft() + 1);
			if(gameModel.getMovesLeft() % 2 == 1) {
				gameView.getPlayerturn().setText("'X': Player 1");
				gameModel.setCurrentPlayer("1");
			} else{
				gameView.getPlayerturn().setText("'O': Player 2");
				gameModel.setCurrentPlayer("2");
			}
			gameView.updateBlock(gameModel, lastMoveRow, lastMoveColumn);
		}
    }

	/**
     * Checks whether the given player has won
     *
     * @param block The block to be moved to by the current player
	 * @param playerChar String of the current player (X or O)
	 * @param result String to be printed proclaiming the winner
     */
	private void checkWinner(JButton block, String playerChar, String result){
		if(playerChar.equals("X")){
			gameModel.setCurrentPlayer("2");
		}else{
			gameModel.setCurrentPlayer("1");
		}
		//Top left, check across, down, and diagonal
		for(int row = 0;row < ROWS;row++){
			for(int col = 0; col < COLUMNS;col++){
				if(block == gameView.getBlocks()[row][col]) {
					lastMoveRow = row;
					lastMoveColumn = col;
					gameModel.getBlockModel()[row][col].setContents(playerChar);
					gameModel.getBlockModel()[row][col].setIsLegalMove(false);
					gameView.updateBlock(gameModel, row, col);
					if(gameModel.getMovesLeft() < 7) {
						//check horizontal and vertical win condition
						if(checkHorizontal(row) || checkVertical(col)) {
							gameModel.setFinalResult(result);
							endGame();
						//check diagonal if applicable
						} else if (checkDiagonal(row, col)) {
							gameModel.setFinalResult(result);
							endGame();
						} else if(gameModel.getMovesLeft() == 0) {
							gameModel.setFinalResult(RowGameModel.GAME_END_NOWINNER);
						}
						if (gameModel.getFinalResult() != null) {
							gameView.getPlayerturn().setText(gameModel.getFinalResult());
						}
					}
				} 
			}
		}
	}

	/**
     * Checks whether the player has won by horizontal
     *
     * @param row the row of the most recent player move
     */
	private boolean checkHorizontal(int row){
		for(int i = 0;i < COLUMNS - 1;i++){
			if(!gameModel.getBlockModel()[row][i].getContents().equals(gameModel.getBlockModel()[row][i + 1].getContents())){
				return false;
			}
		}
		return true;
	}

	/**
     * Checks whether the player has won by vertical
     *
     * @param col the row of the most recent player move
     */
	private boolean checkVertical(int col){
		for(int i = 0;i < ROWS - 1;i++){
			if(!gameModel.getBlockModel()[i][col].getContents().equals(gameModel.getBlockModel()[i + 1][col].getContents())){
				return false;
			}
		}
		return true;
	}

	/**
     * Checks whether the player has won by diagonal
     *
	 * @param row the row of the most recent player move
     * @param col the col of the most recent player move
     */
	private boolean checkDiagonal(int row, int col){
		return (gameModel.getBlockModel()[0][0].getContents().equals(gameModel.getBlockModel()[1][1].getContents()) &&
						gameModel.getBlockModel()[1][1].getContents().equals(gameModel.getBlockModel()[2][2].getContents()) && 
						gameModel.getBlockModel()[2][2].getContents().equals(gameModel.getBlockModel()[row][col].getContents())) || 

						(gameModel.getBlockModel()[0][2].getContents().equals(gameModel.getBlockModel()[1][1].getContents()) &&
						gameModel.getBlockModel()[1][1].getContents().equals(gameModel.getBlockModel()[2][0].getContents())) && 
						gameModel.getBlockModel()[2][0].getContents().equals(gameModel.getBlockModel()[row][col].getContents());
	}

	/**
     * Returns RowGameModel object
     */
    public RowGameModel getRowGameModel() {
		return this.gameModel;
	}

	/**
     * Returns RowGameView object
     */
    public RowGameGUI getRowGameView() {
		return this.gameView;
	}



    /**
     * Ends the game disallowing further player turns.
     */
    public void endGame() {
	for(int row = 0;row<3;row++) {
	    for(int column = 0;column<3;column++) {
		gameView.getBlocks()[row][column].setEnabled(false);
	    }
	}
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public void resetGame() {
        for(int row = 0;row<3;row++) {
            for(int column = 0;column<3;column++) {
                gameModel.getBlockModel()[row][column].reset();
		gameModel.getBlockModel()[row][column].setIsLegalMove(true);
		gameView.updateBlock(gameModel,row,column);
            }
        }
        gameModel.setCurrentPlayer("1");
        gameModel.setMovesLeft(9);
	gameModel.setFinalResult(null);
        gameView.getPlayerturn().setText("Player 1 to play 'X'");
    }
}
