import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.accessibility.AccessibleAttributeSequence;

import model.RowBlockModel;
import model.RowGameModel;
import controller.RowGameController;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestController {
    private RowGameController game;

    @Before
    public void setUp() {
	game = new RowGameController();
    }

    @After
    public void tearDown() {
	game = null;
    }

    //Illegal arguments and moves
    @Test(expected = NullPointerException.class)
    public void testControllerWithNullJButton() {
	    game.move(null);
    }

    //I felt that it doesnt make sense to end the game if the user clicks on the wrong tile,
    //however this is still checking that the move was infact illegal.
    @Test
    public void IllegalMove() {
	    game.move(game.getRowGameView().getBlocks()[0][0]);
        game.move(game.getRowGameView().getBlocks()[0][0]);
        assertTrue(game.getRowGameView().getPlayerturn().getText().contains("This box has already been taken | "));
    }

    @Test
    public void testPlayer1Wins() {
        RowGameModel model = game.getRowGameModel();

        game.move(game.getRowGameView().getBlocks()[0][0]);
        game.move(game.getRowGameView().getBlocks()[1][0]);
        game.move(game.getRowGameView().getBlocks()[0][1]);
        game.move(game.getRowGameView().getBlocks()[2][0]);
        game.move(game.getRowGameView().getBlocks()[0][2]);

        assertEquals("Player 1 wins!", model.getFinalResult());
        assertEquals(4, model.getMovesLeft());
    }

    @Test
    public void testPlayer2Wins() {
        RowGameModel model = game.getRowGameModel();

        game.move(game.getRowGameView().getBlocks()[0][0]);
        game.move(game.getRowGameView().getBlocks()[1][0]);
        game.move(game.getRowGameView().getBlocks()[0][1]);
        game.move(game.getRowGameView().getBlocks()[1][1]);
        game.move(game.getRowGameView().getBlocks()[2][2]);
        game.move(game.getRowGameView().getBlocks()[1][2]);

        assertEquals("Player 2 wins!", model.getFinalResult());
        assertEquals(3, model.getMovesLeft());
    }

    @Test
    public void testPlayerTie() {
        RowGameModel model = game.getRowGameModel();

        game.move(game.getRowGameView().getBlocks()[0][0]);
        game.move(game.getRowGameView().getBlocks()[1][0]);
        game.move(game.getRowGameView().getBlocks()[0][1]);
        game.move(game.getRowGameView().getBlocks()[1][1]);
        game.move(game.getRowGameView().getBlocks()[2][2]);
        game.move(game.getRowGameView().getBlocks()[0][2]);
        game.move(game.getRowGameView().getBlocks()[1][2]);
        game.move(game.getRowGameView().getBlocks()[2][1]);
        game.move(game.getRowGameView().getBlocks()[2][0]);

        assertEquals("Game ends in a draw", model.getFinalResult());
        assertEquals(0, model.getMovesLeft());
    }

    @Test
    public void testReset() {
        RowGameModel model = game.getRowGameModel();

        game.move(game.getRowGameView().getBlocks()[0][0]);
        game.move(game.getRowGameView().getBlocks()[1][0]);
        game.move(game.getRowGameView().getBlocks()[0][1]);
        game.move(game.getRowGameView().getBlocks()[1][1]);
        game.move(game.getRowGameView().getBlocks()[2][2]);
        game.move(game.getRowGameView().getBlocks()[0][2]);
        game.move(game.getRowGameView().getBlocks()[1][2]);
        game.move(game.getRowGameView().getBlocks()[2][1]);
        game.move(game.getRowGameView().getBlocks()[2][0]);

        game.resetGame();

        game.move(game.getRowGameView().getBlocks()[0][0]);
        game.move(game.getRowGameView().getBlocks()[1][0]);
        game.move(game.getRowGameView().getBlocks()[0][1]);
        game.move(game.getRowGameView().getBlocks()[2][0]);
        game.move(game.getRowGameView().getBlocks()[0][2]);

        assertEquals("Player 1 wins!", model.getFinalResult());
        assertEquals(4, model.getMovesLeft());
    }
}
