import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

/**
 * GUI is pretty messy.
 */

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int width = 768;
        int height = 768;

        /**
         * Initialize the game object.
         */
        GameOfLife game = new GameOfLife(width, height, 4);

        /**
         * Setup the main window.
         */
        JFrame frame = new JFrame();
        frame.setTitle("GameOfLife");
        frame.setSize(width, height + 32);
        frame.setBackground(Color.white);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(new GridBagLayout());

        /**
         * Clear button.
         */
        JButton buttonClear = new JButton();
        buttonClear.setText("Clear");
        buttonClear.setVisible(true);
        buttonClear.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int x = 1; x < game.GetColumns() - 1; x++) {
                    for (int y = 1; y < game.GetRows() - 1; y++) {
                        game.OverrideLifeState(x, y, 0);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        /**
         * Random button.
         */
        JButton buttonRandom = new JButton();
        buttonRandom.setText("Random Map");
        buttonRandom.setVisible(true);
        buttonRandom.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.SetMap(game.RandomMap());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        /**
         * Glider gun button.
         */
        JButton buttonGun = new JButton();
        buttonGun.setText("Glider Gun");
        buttonGun.setVisible(true);
        buttonGun.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.SetMap(game.GliderGun());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        /**
         * Create the canvas to draw on.
         */
        Canvas canvas = new Canvas();
        canvas.setSize(width, height);
        canvas.setBackground(Color.darkGray);
        canvas.setVisible(true);
        canvas.setFocusable(false);
        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / game.GetScale();
                int y = e.getY() / game.GetScale();

                if (SwingUtilities.isLeftMouseButton(e)) {
                    for (int i = 0; i <= 2; i++) {
                        game.OverrideLifeState(x, y + i, 1);
                    }
                } else {
                    for (int i = -8; i <= 8; i++) {
                        for (int j = -8; j <= 8; j++) {
                            game.OverrideLifeState(x + i, y + j, 1);
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        /**
         * Create a panel holding the buttons.
         */
        JPanel panelButton = new JPanel(new GridBagLayout());
        panelButton.add(buttonClear);
        panelButton.add(buttonRandom);
        panelButton.add(buttonGun);

		/**
		 * Create a panel holding the canvas.
		 */
        JPanel panelCanvas = new JPanel(new GridBagLayout());
        panelCanvas.add(canvas);

		/**
		 * Define the layout constraints.
		 */
        GridBagConstraints buttonCons = new GridBagConstraints();
        buttonCons.anchor = GridBagConstraints.NORTH;
        buttonCons.weighty = 1.0;
        buttonCons.gridy = 1;

        GridBagConstraints panelCons = new GridBagConstraints();
        panelCons.anchor = GridBagConstraints.SOUTH;
        panelCons.weighty = 32;
        panelCons.gridy = 2;
		
		/**
		 * Add the pannels to the frame.
		 */
        frame.add(panelButton, buttonCons);
        frame.add(panelCanvas, panelCons);

        /**
         * Setup the buffer strategy.
         */
        canvas.createBufferStrategy(2);

        /**
         * Declare storage variables.
         */
        BufferStrategy bufferStrategy;
        Graphics graphics;

        /**
         * Create the drawing loop.
         */
        while (true) {
            /**
             * Sleep for some time to improve visualization.
             */
            Thread.sleep(120);

            /**
             * Get the drawing layer.
             */
            bufferStrategy = canvas.getBufferStrategy();
            graphics = bufferStrategy.getDrawGraphics();

            /**
             * Clear previously drawn content.
             */
            graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            /**
             * Evolve the game's lifecycle.
             */
            game.Evolve();

            /**
             * Iterate over all cells.
             */
            for (int i = 0; i < game.GetColumns(); i++) {
                for (int j = 0; j < game.GetRows(); j++) {

                    /**
                     * Determine the color of the cell based on it's life state.
                     */
                    if ((game.GetMap()[i][j] == 1))
                        graphics.setColor(Color.red);
                    else
                        graphics.setColor(Color.darkGray);

                    /**
                     * Draw the cell.
                     */
                    graphics.fillRect(i * game.GetScale(), j * game.GetScale(), game.GetScale(), game.GetScale());
                }
            }

            /**
             * Display the canvas' drawing buffer.
             */
            bufferStrategy.show();

            /**
             * Destruct the drawing buffer.
             */
            graphics.dispose();
        }
    }
}
