package model;


public class RowGameModel 
{
    public static final String GAME_END_NOWINNER = "Game ends in a draw";
    public static final int ROWS = 3;
    public static final int COLUMNS = 3;

    public RowBlockModel[][] blocksData = new RowBlockModel[ROWS][COLUMNS];

    /**
     * The current player taking their turn
     */
    public String player = "1";
    public int movesLeft = ROWS * COLUMNS;

    private String finalResult = null;


    public RowGameModel() {
	super();

	for (int row = 0; row < ROWS; row++) {
	    for (int col = 0; col < COLUMNS; col++) {
		blocksData[row][col] = new RowBlockModel(this);
	    } // end for col
	} // end for row
    }

    public String getFinalResult() {
	return this.finalResult;
    }

    public void setFinalResult(String finalResult) {
	this.finalResult = finalResult;
    }
}
