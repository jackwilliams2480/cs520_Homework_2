import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowBlockModel;
import controller.RowGameController;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameController game;

    @Before
    public void setUp() {
	game = new RowGameController();
    }

    @After
    public void tearDown() {
	game = null;
    }

    @Test
    public void testNewGame() {
        assertEquals ("1", game.gameModel.player);
        assertEquals (9, game.gameModel.movesLeft);
    }

    //Illegal arguments and moves
    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
	RowBlockModel block = new RowBlockModel(null);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testIllegalBlocksDataIndex() {
        game.gameModel.blocksData[-1][0].setContents("X");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testIllegalUpdateBlockIndex() {
        game.gameView.updateBlock(game.gameModel, -1, 0);
    }

    //Legal arguments and moves
    @Test
    public void testFirstMove() {
        game.gameModel.blocksData[0][0].setContents("X");
        game.gameView.updateBlock(game.gameModel, 0, 0);

        assertEquals("X", game.gameModel.blocksData[0][0].getContents());
    }

    @Test
    public void testQuickGame() {
        game.gameModel.blocksData[0][0].setContents("X");
        game.gameView.updateBlock(game.gameModel, 0, 0);

        game.gameModel.blocksData[0][0].setContents("O");
        game.gameView.updateBlock(game.gameModel, 0, 1);

        game.gameModel.blocksData[0][0].setContents("X");
        game.gameView.updateBlock(game.gameModel, 1, 0);

        game.gameModel.blocksData[0][0].setContents("O");
        game.gameView.updateBlock(game.gameModel, 0, 2);

        game.gameModel.blocksData[0][0].setContents("X");
        game.gameView.updateBlock(game.gameModel, 2, 0);
    }

}
