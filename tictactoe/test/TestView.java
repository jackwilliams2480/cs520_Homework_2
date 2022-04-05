import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.accessibility.AccessibleAttributeSequence;

import model.RowBlockModel;
import model.RowGameModel;
import view.RowGameGUI;
import controller.RowGameController;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestView {
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
    public void testNewView() {
        RowGameGUI gui = game.getRowGameView();
        game.move(game.getRowGameView().getBlocks()[0][0]);
        assertEquals(gui.getBlocks()[0][0].getText(),"X");
    }
}
