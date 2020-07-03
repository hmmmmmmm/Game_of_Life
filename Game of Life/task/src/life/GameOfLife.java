package life;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {
    private JLabel generation = new JLabel();
    private JLabel alive = new JLabel();

    private static final int size = 20;
    private static int generations = 20;
    static RandomCellsGenerator generator = new RandomCellsGenerator(20);
    private static Cell[][] cells = generator.generate(size);
    static Grid grid = new Grid(cells);

    public static class JPanelGrid extends JPanel {
        JPanelGrid() {
            setPreferredSize(new Dimension(420, 500));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int row = 0; row < cells.length; row++) {
                for (int col = 0; col < cells.length; col++) {
                    if (cells[row][col].state == State.ALIVE) {
                        g.setColor(Color.BLACK);
                        g.fillRect((row * 20) + 5, (col * 20) + 3, 20, 20);
                    } else {
                        g.setColor(Color.BLACK);
                        g.drawRect((row * 20) + 5, (col * 20) + 3, 20, 20);
                    }
                }
            }
        }
    }

    public void setupToolbar(JPanel toolbar) {
        JPanel labels = new JPanel();
        JToggleButton playButton = new JToggleButton();
        JButton resetButton = new JButton();

        labels.add(generation);
        generation.setName("GenerationLabel");
        generation.setText("Generation #0");

        labels.add(alive);
        alive.setName("AliveLabel");
        alive.setText(String.format("Alive: %d", grid.countTotal(cells)));

        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));

        toolbar.add(labels);
        toolbar.add(Box.createHorizontalGlue());

        toolbar.add(playButton); //TODO: make start/stop functional
        playButton.setName("PlayToggleButton");
        playButton.setText("Play/Pause");

        toolbar.add(Box.createRigidArea(new Dimension(5, 0)));

        toolbar.add(resetButton); //TODO: make reset functional
        resetButton.setName("ResetButton");
        resetButton.setText("Reset");

        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
        toolbar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void setupWindow(JPanel toolbar, JPanelGrid gridPanel) {
        setupToolbar(toolbar);
        add(toolbar);
        add(gridPanel);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setName("Game of Life");
        setSize(425, 490);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public GameOfLife() {
        super("Game of Life");
        JPanel toolbar = new JPanel();
        JPanelGrid gridPanel = new JPanelGrid();
        setupWindow(toolbar, gridPanel);

        try {
            for (int gen = 0; gen < generations; gen++) {
                Thread.sleep(1000L);
                cells = grid.next();
                grid.setCells(cells);
                gridPanel.paintComponent(gridPanel.getGraphics());
                generation.setText(String.format("Generation #%d", gen+1));
                alive.setText(String.format("Alive: %d", grid.countTotal(cells)));
                repaint();
            }
        } catch (InterruptedException e) {
        }
    }
}
