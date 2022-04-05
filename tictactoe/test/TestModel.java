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
public class TestModel {
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
    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
	    RowBlockModel block = new RowBlockModel(null);
    }

    @Test
    public void testBlockModelContents() {
        RowGameModel model = game.getRowGameModel();

        game.move(game.getRowGameView().getBlocks()[0][0]);
        game.move(game.getRowGameView().getBlocks()[1][0]);

        assertEquals(model.getBlockModel()[0][0].getContents() , "X");
    }
}
