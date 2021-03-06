package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import controller.RowGameController;

public class RowGameGUI {
    private JFrame gui = new JFrame("Tic Tac Toe");
    private RowGameModel gameModel = new RowGameModel();
    private JButton[][] blocks = new JButton[3][3];
    private JButton reset = new JButton("Reset");
    private JButton undo = new JButton("Undo");
    private JTextArea playerturn = new JTextArea();

    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameGUI(RowGameController controller) {
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(new Dimension(500, 350));
        gui.setResizable(true);

        JPanel gamePanel = new JPanel(new FlowLayout());
        JPanel game = new JPanel(new GridLayout(3,3));
        gamePanel.add(game, BorderLayout.CENTER);

        JPanel options = new JPanel(new FlowLayout());
        options.add(reset);
        options.add(undo);
        JPanel messages = new JPanel(new FlowLayout());
        messages.setBackground(Color.white);

        gui.add(gamePanel, BorderLayout.NORTH);
        gui.add(options, BorderLayout.CENTER);
        gui.add(messages, BorderLayout.SOUTH);

        messages.add(playerturn);
        playerturn.setText("Player 1 to play 'X'");

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.resetGame();
            }
        });

        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.undoLastMove();
            }
        });

        // Initialize a JButton for each cell of the 3x3 game board.
        for(int row = 0; row<3; row++) {
            for(int column = 0; column<3 ;column++) {
                blocks[row][column] = new JButton();
                blocks[row][column].setPreferredSize(new Dimension(75,75));
                game.add(blocks[row][column]);
                blocks[row][column].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
			controller.move((JButton)e.getSource());
                    }
                });
            }
        }
    }

    /**
     * Updates the block at the given row and column 
     * after one of the player's moves.
     *
     * @param gameModel The RowGameModel containing the block
     * @param row The row that contains the block
     * @param column The column that contains the block
     */
    public void updateBlock(RowGameModel gameModel, int row, int column) {
	    blocks[row][column].setText(gameModel.getBlockModel()[row][column].getContents());
	    blocks[row][column].setEnabled(gameModel.getBlockModel()[row][column].getIsLegalMove());
    }

    /**
     * gets the JButton block array
     *
     * @return JButton block array
     */
    public JButton[][] getBlocks(){
        return this.blocks;
    }

    /**
     * gets the GUI
     *
     * @return JFrame
     */
    public JFrame getGui() {
        return this.gui;
    }

    /**
     * Sets the GUI
     *
     * @param JFrame new gui
     */
    public void setGui(JFrame gui) {
        this.gui = gui;
    }

    /**
     * gets the RowGameModel
     *
     * @return RowGameModel
     */
    public RowGameModel getGameModel() {
        return this.gameModel;
    }

    /**
     * gets the JTextArea playerTurn
     *
     * @return JTextArea playerTurn
     */
    public JTextArea getPlayerturn() {
        return this.playerturn;
    }

    /**
     * sets the JTextArea playerTurn
     *
     * @param JTextArea playerTurn
     */
    public void setPlayerturn(JTextArea playerturn) {
        this.playerturn = playerturn;
    }

}
