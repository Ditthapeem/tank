import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.image.BufferedImage;
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

    private Thread thread;
    private Controller controller;


    public Window() {
        controller = new Controller();
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

    public void initWelcome() {
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
        gameUI = new GameUI();
        addKeyListener(controller);
        thread = new Thread() {
            @Override
            public void run() {
                while (!world.getIsOver()) {

                    moving();
                    gameUI.repaint();
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        thread.start();
        add(gameUI);
        pack();
    }

    private void moving() {
        if (world.getIsStart()) {
            world.move();
        }
    }

    public void start() {
        setVisible(true);
        initPregame();
        initWelcome();
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

        private final Image imageTank;
        private BufferedImage firstTankOnScreen;
        private BufferedImage secondTankOnScreen;
        private BufferedImage bulletOnScreen;

        private final Image imageBrick;
        private final Image imageBush;
        private final Image imageSteel;
        private Image imageBullet;
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
            for (Bullet bullet: bulletList) {
                bulletOnScreen = rotateImage(convertToBufferedImage(imageBullet), bullet.getRotationAngle());
                int x = bullet.getX() * CELL_PIXEL_SIZE;
                int y = bullet.getY() * CELL_PIXEL_SIZE;
                if (!world.isInBush(bullet.getX(), bullet.getY())) {
                    g.drawImage(bulletOnScreen, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
                }
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

    class Controller extends KeyAdapter {
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
            } else if(e.getKeyCode() == KeyEvent.VK_UP) {
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
            } else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                world.addBullet(world.getFirstTank());
            } else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                world.addBullet(world.getSecondTank());
            } else{
                // Don't allow starting when press key except direction key
                return;
            }
            gameUI.setShowFirstTank(!world.isInBush(
                    world.getFirstTank().getX(),
                    world.getFirstTank().getY()));
            gameUI.setShowSecondTank(!world.isInBush(
                    world.getSecondTank().getX(),
                    world.getSecondTank().getY()));
            gameUI.repaint();
            world.setIsStart(true);
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

        public InGameUI() {
            setDoubleBuffered(true);
            setPreferredSize(new Dimension(50 * worldSize, 50));
            repaint();
        }

        public void setPlayerLabel() {
            if (soloMode) {
                soloHealth = new JLabel("Player: ");
                add(soloHealth);
            } else if (duoMode) {
                duoHealth1 = new JLabel("Player1: ");
                duoHealth2 = new JLabel("Player2: ");
                add(duoHealth1);
                add(duoHealth2);
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (soloMode) {
                paintScoreSolo(g);
            } else if (duoMode) {
                paintScoreDuo(g);
            }
        }

        public void paintScoreSolo(Graphics g) {
            g.drawString("PlayerScore: " + world.getPlayer1Score(), 20, 20);
        }

        public void paintScoreDuo(Graphics g) {
            g.drawString("Player1's Score: " + world.getPlayer1Score(), 20, 20);
            g.drawString("Player2's Score: " + world.getPlayer2Score(), 20, 40);
        }
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setTitle("Super Tank Fury 2015");
        window.start();
    }

}
