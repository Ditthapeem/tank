import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Window extends JFrame {


    private final World world;
    private final int worldSize = 12;

    private GameUI gameUI;
    private PreGameUI preGameUI;
    private InGameUI inGameUI;
    private WelcomeUI welcomeUI;

    public boolean selectMap = false;
    public boolean selectMode = false;

    public boolean soloMode = false;
    public boolean duoMode = false;

    private Thread bulletThread;
    private Thread aiTankThread;
    private PlayerOneController playerOneController;
    private PlayerTwoController playerTwoController;


    public Window() {
        world = new World(worldSize);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void initPregame() {
        preGameUI = new PreGameUI();
        add(preGameUI, BorderLayout.SOUTH);
        pack();
    }

    public void deleteInitPregame() {
        remove(preGameUI);
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

    public void initMainLogo() {
        WelcomeUI welcomeUI = new WelcomeUI();
        add(welcomeUI, BorderLayout.CENTER);
        pack();
    }

    public void initInGame() {
        inGameUI = new InGameUI();
        add(inGameUI, BorderLayout.SOUTH);
        pack();
    }

    private void startGame() {
        world.setIsStart(true);
        gameUI = new GameUI();
        playerOneController = new PlayerOneController();
        addKeyListener(playerOneController);
        if (duoMode) {
            playerTwoController = new PlayerTwoController();
            addKeyListener(playerTwoController);
        }
        bulletThread = new Thread(() -> {
            while (!world.getIsOver()) {
                world.moveBullet();
                gameUI.repaint();
                try {
                    Thread.sleep(120);  // set bullet speed to 120ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        if (soloMode) {
            aiTankThread = new Thread(() -> {
                while (!world.getIsOver()) {
                    world.moveEnemyTank();
                    gameUI.repaint();
                    try {
                        Thread.sleep(500);  // set tank sensitive to 500ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            aiTankThread.start();
        }
        bulletThread.start();
        add(gameUI);
        pack();
    }

    public void start() {
        setVisible(true);
        initPregame();
        initMainLogo();
    }

    public void mapSelected() {
        if (selectMode) {
            initGame();
            deleteInitPregame();
            initInGame();
            startGame();
        }
    }

    public void modeSelected() {
        if (selectMap) {
            initGame();
            deleteInitPregame();
            initInGame();
            startGame();
        }
    }

    class GameUI extends JPanel {
        public static final int CELL_PIXEL_SIZE = 50;

        private List<Bush> bushList;
        private List<Brick> brickList;
        private List<Steel> steelList;
        private List<Tank> enemyTankList;

        private final Image imageTank;
        private final Image imageEnemyTank;
        private BufferedImage firstTankOnScreen;
        private BufferedImage secondTankOnScreen;
        private BufferedImage enemyTankOnscreen;
        private BufferedImage bulletOnScreen;

        private final Image imageBrick;
        private final Image imageBush;
        private final Image imageSteel;
        private final Image imageBullet;
        private final Image imageLogo;

        private boolean showFirstTank;
        private boolean showSecondTank;

        public GameUI() {
            setPreferredSize(new Dimension(worldSize * CELL_PIXEL_SIZE,
                    worldSize * CELL_PIXEL_SIZE));
            imageTank = new ImageIcon("img/tank.png").getImage();
            imageBush = new ImageIcon("img/bush.png").getImage();
            imageBrick = new ImageIcon("img/brick.png").getImage();
            imageSteel = new ImageIcon("img/steel.png").getImage();
            imageLogo = new ImageIcon("img/logo.png").getImage();
            imageBullet = new ImageIcon("img/bullet.png").getImage();
            imageEnemyTank = new ImageIcon("img/enemyTank.png").getImage();
            showFirstTank = true;
            showSecondTank = true;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (!selectMap) {
                paintLogo(g);
            }else {
                if(duoMode){
                    paintBush(g);
                    paintBrick(g);
                    paintSteel(g);
                    paintBullet(g);
                    if (showFirstTank) {
                        paintFirstTank(g);
                    }
                    if (showSecondTank) {
                        paintSecondTank(g);
                    }
                } else {
                    paintBush(g);
                    paintBrick(g);
                    paintSteel(g);
                    paintBullet(g);
                    if (showFirstTank) {
                        paintFirstTank(g);
                    }
                    paintEnemyTank(g);
                }
            }
        }

        public void paintLogo(Graphics g) {
            g.drawImage(imageLogo, 0, 0, worldSize * CELL_PIXEL_SIZE, worldSize * CELL_PIXEL_SIZE, null, null);
        }

        public void paintFirstTank(Graphics g) {
            firstTankOnScreen = rotateImage(convertToBufferedImage(imageTank), world.getFirstTank().getRotationAngle());
            Tank tank = world.getFirstTank();
            int x = tank.getX() * CELL_PIXEL_SIZE;
            int y = tank.getY()  * CELL_PIXEL_SIZE;
            g.drawImage(firstTankOnScreen, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
        }

        public void paintSecondTank(Graphics g) {
            secondTankOnScreen = rotateImage(convertToBufferedImage(imageTank), world.getSecondTank().getRotationAngle());
            Tank tank = world.getSecondTank();
            int x = tank.getX() * CELL_PIXEL_SIZE;
            int y = tank.getY()  * CELL_PIXEL_SIZE;
            g.drawImage(secondTankOnScreen, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
        }

        public void paintEnemyTank(Graphics g) {
            enemyTankList = world.getEnemyTankList();
            for (Tank t: enemyTankList) {
                enemyTankOnscreen = rotateImage(convertToBufferedImage(imageEnemyTank), t.getRotationAngle());
                int x = t.getX() * CELL_PIXEL_SIZE;
                int y = t.getY() * CELL_PIXEL_SIZE;
                g.drawImage(enemyTankOnscreen, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
            }
        }

        public void paintBush(Graphics g) {
            bushList = world.getBushList();
            for (Bush b: bushList) {
                int x = b.getX() * CELL_PIXEL_SIZE;
                int y = b.getY()  * CELL_PIXEL_SIZE;
                g.drawImage(imageBush, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
            }

        }

        public void paintBrick(Graphics g) {
            brickList = world.getBrickList();
            for (Brick b: brickList) {
                int x = b.getX() * CELL_PIXEL_SIZE;
                int y = b.getY()  * CELL_PIXEL_SIZE;
                g.drawImage(imageBrick, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
            }
        }

        public void paintSteel(Graphics g) {
            steelList = world.getSteelList();
            for (Steel s: steelList) {
                int x = s.getX() * CELL_PIXEL_SIZE;
                int y = s.getY() * CELL_PIXEL_SIZE;
                g.drawImage(imageSteel, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
            }
        }

        public void paintBullet(Graphics g) {
            List<Bullet> bulletList = world.getBulletList();
            try {
                for (Bullet bullet : bulletList) {
                    bulletOnScreen = rotateImage(convertToBufferedImage(imageBullet), bullet.getRotationAngle());
                    int x = bullet.getX() * CELL_PIXEL_SIZE;
                    int y = bullet.getY() * CELL_PIXEL_SIZE;
                    if (!world.isInBush(bullet.getX(), bullet.getY())) {
                        g.drawImage(bulletOnScreen, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
                    }
                }
            } catch (ConcurrentModificationException e) {
                System.out.println(e);
            }
        }
        public void setShowFirstTank(boolean status) {
            showFirstTank = status;
        }

        public void setShowSecondTank(boolean status) {
            showSecondTank = status;
        }

        private BufferedImage convertToBufferedImage(Image image)
        {
            BufferedImage newImage = new BufferedImage(
                    image.getWidth(null), image.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            return newImage;
        }

        public BufferedImage rotateImage(BufferedImage src, int rotationAngle) {
            double theta = (Math.PI * 2) / 360 * rotationAngle;
            int width = src.getWidth();
            int height = src.getHeight();
            BufferedImage dest;
            if (rotationAngle == 90 || rotationAngle == 270) {
                dest = new BufferedImage(src.getHeight(), src.getWidth(), src.getType());
            } else {
                dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
            }

            Graphics2D graphics2D = dest.createGraphics();

            if (rotationAngle == 90) {
                graphics2D.translate((height - width) / 2, (height - width) / 2);
                graphics2D.rotate(theta, height / 2, width / 2);
            } else if (rotationAngle == 270) {
                graphics2D.translate((width - height) / 2, (width - height) / 2);
                graphics2D.rotate(theta, height / 2, width / 2);
            } else {
                graphics2D.translate(0, 0);
                graphics2D.rotate(theta, width / 2, height / 2);
            }
            graphics2D.drawRenderedImage(src, null);
            return dest;
        }
    }

    class PlayerOneController extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_W) {
                Command c = new CommandMoveUp(world.getFirstTank());
                c.execute();
                world.moveFirstTank();
            } else if(e.getKeyCode() == KeyEvent.VK_A) {
                Command c = new CommandMoveLeft(world.getFirstTank());
                c.execute();
                world.moveFirstTank();
            } else if(e.getKeyCode() == KeyEvent.VK_S) {
                Command c = new CommandMoveDown(world.getFirstTank());
                c.execute();
                world.moveFirstTank();
            } else if(e.getKeyCode() == KeyEvent.VK_D) {
                Command c = new CommandMoveRight(world.getFirstTank());
                c.execute();
                world.moveFirstTank();
            } else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                world.addBullet(world.getFirstTank());
            } else{
                // Don't allow starting when press key except direction key
                return;
            }
            gameUI.setShowFirstTank(!world.isInBush(
                    world.getFirstTank().getX(),
                    world.getFirstTank().getY()));
            gameUI.repaint();
        }
    }

    class PlayerTwoController extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                Command c = new CommandMoveUp(world.getSecondTank());
                c.execute();
                world.moveSecondTank();
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                Command c = new CommandMoveDown(world.getSecondTank());
                c.execute();
                world.moveSecondTank();
            } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                Command c = new CommandMoveLeft(world.getSecondTank());
                c.execute();
                world.moveSecondTank();
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                Command c = new CommandMoveRight(world.getSecondTank());
                c.execute();
                world.moveSecondTank();
            } else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                world.addBullet(world.getSecondTank());
            } else{
                // Don't allow starting when press key except direction key
                return;
            }
            gameUI.setShowSecondTank(!world.isInBush(
                    world.getSecondTank().getX(),
                    world.getSecondTank().getY()));
            gameUI.repaint();
        }
    }

    class WelcomeUI extends JPanel {
        public static final int CELL_PIXEL_SIZE = 50;
        private final Image imageLogo;

        public WelcomeUI() {
            setPreferredSize(new Dimension(worldSize * CELL_PIXEL_SIZE,
                    worldSize * CELL_PIXEL_SIZE));
            imageLogo = new ImageIcon("img/logo.png").getImage();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            paintLogo(g);
        }

        public void paintLogo(Graphics g) {
            g.drawImage(imageLogo, 0, 0, worldSize * CELL_PIXEL_SIZE, worldSize * CELL_PIXEL_SIZE, null, null);
        }
    }

    class PreGameUI extends JPanel {
        private JButton map1;
        private JButton map2;
        private JButton map3;
        private JButton solo;
        private JButton duo;

        public PreGameUI() {
            setLayout(new FlowLayout());
            JLabel preGameLabel = new JLabel("Super Tank Fury 2015   ");
            add(preGameLabel);
            pack();
            setButton();
        }

        private void setButton() {
            map1Button();
            map2Button();
            map3Button();
            soloButton();
            duoButton();
        }

        private void map1Button() {
            map1 = new JButton("Map 1");
            map1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.setMapDefault();
                    selectMap = true;
                    map2.setEnabled(false);
                    map3.setEnabled(false);
                    mapSelected();
                    Window.this.requestFocus();
                }
            });
            add(map1);
        }

        private void map2Button() {
            map2 = new JButton("Map 2");
            map2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.setMapTomb();
                    selectMap = true;
                    map1.setEnabled(false);
                    map3.setEnabled(false);
                    mapSelected();
                    Window.this.requestFocus();
                }
            });
            add(map2);
        }

        private void map3Button() {
            map3 = new JButton("Map 3");
            map3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.setMapSpecial();
                    selectMap = true;
                    map1.setEnabled(false);
                    map2.setEnabled(false);
                    mapSelected();
                    Window.this.requestFocus();
                }
            });
            add(map3);
        }

        private void soloButton() {
            solo = new JButton("1 Player");
            solo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectMode = true;
                    duo.setEnabled(false);
                    soloMode = true;
                    world.setSoloMode(true);
                    modeSelected();
                    Window.this.requestFocus();
                }
            });
            add(solo);
        }

        private void duoButton() {
            duo = new JButton("2 Players");
            duo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectMode = true;
                    solo.setEnabled(false);
                    world.setDuoMode(true);
                    duoMode = true;
                    modeSelected();
                    Window.this.requestFocus();
                }
            });
            add(duo);
        }

    }

    class InGameUI extends JPanel {
        private JLabel soloHealth;
        private JLabel duoHealth1;
        private JLabel duoHealth2;

        private Image wasd;
        private Image spaceBar;
        private Image arrow;
        private Image enter;

        public InGameUI() {
            setDoubleBuffered(true);
            wasd = new ImageIcon("img/wasd.png").getImage();
            spaceBar = new ImageIcon("img/space-bar-png-7.png").getImage();
            arrow = new ImageIcon("img/arrowkey.png").getImage();
            enter = new ImageIcon("img/enter.png").getImage();
            setPreferredSize(new Dimension(50 * worldSize, 50));
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (soloMode) {
                paintSolo(g);
            } else if (duoMode) {
                paintDuo(g);
            }
        }

        public void paintSolo(Graphics g) {
            g.drawString("Player1's: ", 20, 20);
            g.drawImage(wasd, 40, 20, 20, 20, null, null);
            g.drawImage(spaceBar, 70, 20, 20, 20, null, null);
        }

        public void paintDuo(Graphics g) {
            g.drawString("Player1's: ", 20, 20);
            g.drawImage(wasd, 40, 20, 20, 20, null, null);
            g.drawImage(spaceBar, 70, 20, 20, 20, null, null);
            g.drawString("Player2's: ", 20, 40);
            g.drawImage(arrow, 40, 40, 20, 20, null, null);
            g.drawImage(enter, 70, 40, 20, 20, null, null);
        }
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setTitle("Super Tank Fury 2015");
        window.start();
    }

}
