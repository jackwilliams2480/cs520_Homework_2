package model;


public class RowGameModel 
{
    public static final String GAME_END_NOWINNER = "Game ends in a draw";
    public static final int ROWS = 3;
    public static final int COLUMNS = 3;

    private RowBlockModel[][] blocksData = new RowBlockModel[ROWS][COLUMNS];

    /**
     * The current player taking their turn
     */
    private String player = "1";
    private int movesLeft = ROWS * COLUMNS;

    private String finalResult = null;


    /**
     * Class constructor
     */
    public RowGameModel() {
        super();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
            blocksData[row][col] = new RowBlockModel(this);
            } // end for col
        } // end for row
    }

    /**
     * Get the finalResult String
     * @return finalResult String
     */
    public String getFinalResult() {
	    return this.finalResult;
    }

    /**
     * Set the finalResult String
     */
    public void setFinalResult(String finalResult) {
	    this.finalResult = finalResult;
    }

    /**
     * Set the remaining moves left
     */
    public void setMovesLeft(int moves){
        this.movesLeft = moves;
    }

    /**
     * Get the remaining moves left
     * @return movesLeft 
     */
    public int getMovesLeft(){
        return this.movesLeft;
    }

    /**
     * Get the current player
     * @return String of current player
     */
    public String getCurentPlayer(){
        return this.player;
    }

    /**
     * Set the current player
     * @return finalResult String
     */
    public void setCurrentPlayer(String player){
        this.player = player;
    }

    /**
     * get the RowBlockModel object
     * @return RowBlockModel object
     */
    public RowBlockModel[][] getBLockModel(){
        return this.blocksData;
    }
}
