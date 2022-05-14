import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Window extends JFrame {

    private final World world;
    private final int worldSize = 12;
    private GameUI gameUI;
    private PregameUI pregameUI;

    public boolean gameStart = false;

    public Window() {
        world = new World(worldSize);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void initPregame() {
        pregameUI = new PregameUI();
        add(pregameUI, BorderLayout.SOUTH);
        pack();
    }

    public void deleteInitPregame() {
        remove(pregameUI);
        pack();
    }

    public void initGame() {
        gameUI = new GameUI();
        add(gameUI, BorderLayout.CENTER);
        pack();
    }

    public void deleteInitGame() {
        remove(gameUI);
        pack();
    }

    public void start() {
        initPregame();
        while (!gameStart) {
            initGame();
        }
        deleteInitPregame();

    }

    class GameUI extends JPanel {
        public static final int CELL_PIXEL_SIZE = 50;

        private List<Bush> bushList;
        private List<Brick> brickList;
        private List<Steel> steelList;

        private final Image imageTank;
        private final Image imageBrick;
        private final Image imageBush;
        private final Image imageSteel;

        public GameUI() {
            setDoubleBuffered(true);

            setPreferredSize(new Dimension(worldSize * CELL_PIXEL_SIZE,
                    worldSize * CELL_PIXEL_SIZE));
            imageTank = new ImageIcon("img/tank.png").getImage();
            imageBush = new ImageIcon("img/bush.png").getImage();
            imageBrick = new ImageIcon("img/brick.png").getImage();
            imageSteel = new ImageIcon("img/steel.png").getImage();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            paintBush(g);
            paintBrick(g);
            paintSteel(g);
//            paintTank(g);
//            paintEnemyTank(g);
        }

        public void paintTank(Graphics g) {
            Tank tank = world.getTank();
            int x = tank.getX() * CELL_PIXEL_SIZE;
            int y = tank.getY()  * CELL_PIXEL_SIZE;
            g.drawImage(imageTank, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
        }

        public void paintEnemyTank(Graphics g) {
            g.drawImage(imageTank, 11 * CELL_PIXEL_SIZE, 11 * CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
        }

        public void paintBush(Graphics g) {
            List<Bush> bushList = world.getBushList();
            for (Bush b: bushList) {
                int x = b.getX() * CELL_PIXEL_SIZE;
                int y = b.getY()  * CELL_PIXEL_SIZE;
                g.drawImage(imageBush, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
            }

        }

        public void paintBrick(Graphics g) {
            List<Brick> brickList = world.getBrickList();
            for (Brick b: brickList) {
                int x = b.getX() * CELL_PIXEL_SIZE;
                int y = b.getY()  * CELL_PIXEL_SIZE;
                g.drawImage(imageBrick, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
            }
        }

        public void paintSteel(Graphics g) {
            List<Steel> steelList = world.getSteelList();
            for (Steel s: steelList) {
                int x = s.getX() * CELL_PIXEL_SIZE;
                int y = s.getY() * CELL_PIXEL_SIZE;
                g.drawImage(imageSteel, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
            }

        }
    }

    class PregameUI extends JPanel {

        private JButton map1;
        private JButton map2;
        private JLabel mapString;

        public PregameUI() {
            setLayout(new FlowLayout());
            setButton();
        }

        private void setButton() {
            map1Button();
            map2Button();
        }

        private void map1Button() {
            map1 = new JButton("map1");
            map1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    world.start();
                    world.setMap(1);
                    map2.setEnabled(false);
                    gameStart = true;
                    Window.this.requestFocus();
                }
            });
            add(map1);
        }

        private void map2Button() {
            map2 = new JButton("map2");
            map2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    world.start();
                    world.setMap(2);
                    map1.setEnabled(false);
                    gameStart = true;
                    Window.this.requestFocus();
                }
            });
            add(map2);
        }

    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setVisible(true);
        window.start();
    }

}
