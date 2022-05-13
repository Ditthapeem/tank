import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Window extends JFrame {

    private World world;
    private int worldSize = 12;
    private GridUI gridUI;

    public Window() {
        world = new World(worldSize);
        gridUI = new GridUI();
        add(gridUI);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void start() {
        setVisible(true);
    }

    class GridUI extends JPanel {
        public static final int CELL_PIXEL_SIZE = 50;

        private List<Bush> bushList;
        private List<Brick> brickList;
        private List<Steel> steelList;

        private Image imageTank;
        private Image imageBrick;
        private Image imageBush;
        private Image imageSteel;

        public GridUI() {
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

    public static void main(String[] args) {
        Window window = new Window();
        window.start();
    }

}
