import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.List;

public class Window extends JFrame {

    private World world;
    private int worldSize = 12;
    private GridUI gridUI;
    private Thread thread;
    private Controller controller;

    public Window() {
        controller = new Controller();
        addKeyListener(controller);
        world = new World(worldSize);
        gridUI = new GridUI();
        thread = new Thread() {
            @Override
            public void run() {
                while (!world.getIsOver()) {
                    gridUI.repaint();
                    moving();
                    try {
                        Thread.sleep(180);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        thread.start();
        add(gridUI);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void moving() {
        if (world.getIsStart()) {
            world.move();
        }
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
        private boolean showTank;
        private Image imageBrick;
        private Image imageBush;
        private Image imageSteel;
        private Image imageBullet;
        public GridUI() {
            setPreferredSize(new Dimension(worldSize * CELL_PIXEL_SIZE,
                    worldSize * CELL_PIXEL_SIZE));
            imageTank = new ImageIcon("img/tank.png").getImage();
            imageBush = new ImageIcon("img/bush.png").getImage();
            imageBrick = new ImageIcon("img/brick.png").getImage();
            imageSteel = new ImageIcon("img/steel.png").getImage();
            imageBullet = new ImageIcon("img/bullet.png").getImage();
            showTank = true;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            paintBush(g);
            paintBrick(g);
            paintSteel(g);
            paintBullet(g);
            if (showTank) {
                paintTank(g);
            }
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

        public void paintBullet(Graphics g) {
            List<Bullet> bulletList = world.getBulletList();
            for (Bullet bullet: bulletList) {
                int x = bullet.getX() * CELL_PIXEL_SIZE;
                int y = bullet.getY() * CELL_PIXEL_SIZE;
                g.drawImage(imageBullet, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
            }
        }
        public void setShowTank(boolean status) {
            showTank = status;
        }
    }

    class Controller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                Command c = new CommandMoveUp(world.getTank());
                c.execute();
                world.moveFirstTank();
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                Command c = new CommandMoveDown(world.getTank());
                c.execute();
                world.moveFirstTank();
            } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                Command c = new CommandMoveLeft(world.getTank());
                c.execute();
                world.moveFirstTank();
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                Command c = new CommandMoveRight(world.getTank());
                c.execute();
                world.moveFirstTank();
            } else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                world.addBullet(world.getTank());
            }else {
                // Don't allow starting when press key except direction key
                return;
            }
            if (world.isInBush(
                    world.getTank().getX(),
                    world.getTank().getY())) {
                gridUI.setShowTank(false);
            } else {
                gridUI.setShowTank(true);
            }
            gridUI.repaint();
            world.setIsStart(true);
        }
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.start();
    }

}
