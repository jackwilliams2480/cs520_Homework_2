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
    public RowGameModel gameModel;
    public RowGameGUI gameView;
	private static final int ROWS = 3;
    private static final int COLUMNS = 3;
    /**
     * Creates a new game initializing the GUI.
     */  
    public RowGameController() {
	gameModel = new RowGameModel();
	gameView = new RowGameGUI(this);

        for(int row = 0; row<3; row++) {
            for(int column = 0; column<3 ;column++) {
	        gameModel.blocksData[row][column].setContents("");
		gameModel.blocksData[row][column].setIsLegalMove(true);
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
			if(!gameView.playerturn.getText().contains("This box has already been taken | ")){
				gameView.playerturn.setText("This box has already been taken | " + gameView.playerturn.getText());
			}
		}else{
			gameModel.movesLeft--;
			if(gameModel.movesLeft%2 == 1) {
				gameView.playerturn.setText("'X': Player 1");
			} else{
				gameView.playerturn.setText("'O': Player 2");
			}
			
			if(gameModel.player.equals("1")) {
				// Check whether player 1 won
				checkWinner(block, "X", "Player 1 wins!");
			} else {
				checkWinner(block, "O", "Player 2 wins!");
			}
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
			gameModel.player = "2";
		}else{
			gameModel.player = "1";
		}
		//Top left, check across, down, and diagonal
		for(int row = 0;row < ROWS;row++){
			for(int col = 0; col < COLUMNS;col++){
				if(block == gameView.blocks[row][col]) {
					gameModel.blocksData[row][col].setContents(playerChar);
					gameView.updateBlock(gameModel, row, col);
					if(gameModel.movesLeft < 7) {
						//check horizontal and vertical win condition
						if(checkHorizontal(row) || checkVertical(col)) {
							gameModel.setFinalResult(result);
							endGame();
						//check diagonal if applicable
						} else if (checkDiagonal(row, col)) {
							gameModel.setFinalResult(result);
							endGame();
						} else if(gameModel.movesLeft==0) {
							gameModel.setFinalResult(RowGameModel.GAME_END_NOWINNER);
						}
						if (gameModel.getFinalResult() != null) {
							gameView.playerturn.setText(gameModel.getFinalResult());
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
			if(!gameModel.blocksData[row][i].getContents().equals(gameModel.blocksData[row][i + 1].getContents())){
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
			if(!gameModel.blocksData[i][col].getContents().equals(gameModel.blocksData[i + 1][col].getContents())){
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
		return (gameModel.blocksData[0][0].getContents().equals(gameModel.blocksData[1][1].getContents()) &&
						gameModel.blocksData[1][1].getContents().equals(gameModel.blocksData[2][2].getContents()) && 
						gameModel.blocksData[2][2].getContents().equals(gameModel.blocksData[row][col].getContents())) || 

						(gameModel.blocksData[0][2].getContents().equals(gameModel.blocksData[1][1].getContents()) &&
						gameModel.blocksData[1][1].getContents().equals(gameModel.blocksData[2][0].getContents())) && 
						gameModel.blocksData[2][0].getContents().equals(gameModel.blocksData[row][col].getContents());
	}

    /**
     * Ends the game disallowing further player turns.
     */
    public void endGame() {
	for(int row = 0;row<3;row++) {
	    for(int column = 0;column<3;column++) {
		gameView.blocks[row][column].setEnabled(false);
	    }
	}
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public void resetGame() {
        for(int row = 0;row<3;row++) {
            for(int column = 0;column<3;column++) {
                gameModel.blocksData[row][column].reset();
		gameModel.blocksData[row][column].setIsLegalMove(true);
		gameView.updateBlock(gameModel,row,column);
            }
        }
        gameModel.player = "1";
        gameModel.movesLeft = 9;
	gameModel.setFinalResult(null);
        gameView.playerturn.setText("Player 1 to play 'X'");
    }
}
