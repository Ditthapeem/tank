import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Window extends JFrame {

    private final World world;
    private final int worldSize = 12;

    private GameUI gameUI;
    private PreGameUI preGameUI;
    private InGameUI inGameUI;

    public boolean selectMap = false;
    public boolean selectMode = false;

    public boolean soloMode = false;
    public boolean duoMode = false;

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

    public void initInGame() {
        inGameUI = new InGameUI();
        add(inGameUI, BorderLayout.SOUTH);
        pack();
    }

    public void start() {
        setVisible(true);
        initPregame();
        while (true) {
            initGame();
            if(selectMap && selectMode) {
                break;
            }
        }
        deleteInitPregame();
        initInGame();
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
        private final Image imageLogo;

        public GameUI() {
            setDoubleBuffered(true);

            setPreferredSize(new Dimension(worldSize * CELL_PIXEL_SIZE,
                    worldSize * CELL_PIXEL_SIZE));
            imageTank = new ImageIcon("img/tank.png").getImage();
            imageBush = new ImageIcon("img/bush.png").getImage();
            imageBrick = new ImageIcon("img/brick.png").getImage();
            imageSteel = new ImageIcon("img/steel.png").getImage();
            imageLogo = new ImageIcon("img/logo.png").getImage();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (!selectMap) {
                paintLogo(g);
            }
            paintBush(g);
            paintBrick(g);
            paintSteel(g);
//            paintTank(g);
//            paintEnemyTank(g);
        }

        public void paintLogo(Graphics g) {
            g.drawImage(imageLogo, 0, 0, worldSize * CELL_PIXEL_SIZE, worldSize * CELL_PIXEL_SIZE, null, null);
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
