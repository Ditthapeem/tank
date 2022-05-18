import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class World {

    private Map map;
    private int size;

    private Tank firstTank;
    private Tank secondTank;

    private int player1Score = 0;
    private int player2Score = 0;

    private List<Bush> bushList;
    private List<Brick> brickList;
    private List<Brick> brickToRemove;
    private List<Steel> steelList;
    private List<Bullet> bulletList;
    private List<Bullet> bulletToRemove;
    private List<Tank> enemyTankList;
    private boolean isStart;

    public void setSoloMode(boolean soloMode) {
        this.soloMode = soloMode;
    }

    public void setDuoMode(boolean duoMode) {
        this.duoMode = duoMode;
    }

    private boolean isOver;

    public boolean soloMode = false;
    public boolean duoMode = false;

    public World(int size) {
        this.size = size;
        isOver = false;
        bushList = new ArrayList<Bush>();
        brickList = new ArrayList<Brick>();
        steelList = new ArrayList<Steel>();
        bulletList = new ArrayList<Bullet>();
        bulletToRemove = new ArrayList<Bullet>();
        brickToRemove = new ArrayList<Brick>();
        enemyTankList = new ArrayList<Tank>();
    }

    public void addObjectList() {
        addBushList();
        addBrickList();
        addSteelList();
        addFirstTank();
        if (duoMode) {
            addSecondTank();
        } else {
            addSecondTank();
        }
        addEnemyTank();
    }

    public void setMapDefault() {
        this.map = new MapDefault();
        addObjectList();
    }

    public void setMapTomb() {
        this.map = new MapTomb();
        addObjectList();
    }

    public void setMapSpecial() {
        this.map = new MapSpecial();
        addObjectList();
    }

    private void addBushList() {
        List<List<Integer>> listMapBush = this.map.getListMapBush();
        for (List<Integer> b: listMapBush) {
            bushList.add(new Bush(b.get(0)-1, b.get(1)-1));
        }
    }

    private void addSteelList() {
        List<List<Integer>> listMapSteel = this.map.getListMapSteel();
        for (List<Integer> s: listMapSteel) {
            steelList.add(new Steel(s.get(0)-1, s.get(1)-1));
        }
    }

    private void addBrickList() {
        List<List<Integer>> listBrick = this.map.getListMapBrick();
        for (List<Integer> b: listBrick) {
            brickList.add(new Brick(b.get(0)-1, b.get(1)-1));
        }
    }

    private void addFirstTank() {
        List<List<Integer>> myTank = this.map.getListMapFirstTank();
        for (List<Integer> t: myTank) {
            firstTank = new Tank(t.get(0)-1, t.get(1)-1);
        }
    }

    public void addSecondTank() {
        List<List<Integer>> myTank = this.map.getListMapSecondTank();
        for (List<Integer> t: myTank) {
            secondTank = new Tank(t.get(0)-1, t.get(1)-1);
        }
    }

    public void addEnemyTank() {
        List<List<Integer>> listEnemyTank = this.map.getListEnemyTank();
        for (List<Integer> t: listEnemyTank) {
            enemyTankList.add(new Tank(t.get(0)-1, t.get(1)-1));
        }
    }

    public void addBullet(Tank srcTank) {
        Bullet newBullet = new Bullet(srcTank.getX() + srcTank.getdX(), srcTank.getY() + srcTank.getdY());
        newBullet.setdX(srcTank.getdX());
        newBullet.setdY(srcTank.getdY());
        newBullet.setRotationAngle(srcTank.getRotationAngle());
        bulletList.add(newBullet);
    }

    public void moveFirstTank() {
        if (canMove(firstTank)) {
            firstTank.move();
        }
    }

    public void moveSecondTank() {
        if (canMove(secondTank)) {
            secondTank.move();
        }
    }

    public void moveEnemyTank() {
        moveEnemy();
    }

    public boolean canMove(WObject obj) {
        int newX = obj.getX() + obj.getdX();
        int newY = obj.getY() + obj.getdY();
        return isInBoundary(newX, newY) && !isInBrick(newX, newY) && !isInSteel(newX, newY) && !firstTankExist(newX, newY) && !secondTankExist(newX, newY);
    }

    public boolean canEnemyMove(int newX, int newY) {
        return isInBoundary(newX, newY) && !isInBrick(newX, newY) && !isInSteel(newX, newY) && !firstTankExist(newX, newY) && !enemyTankExist(newX, newY);
    }

    public boolean firstTankExist(int x, int y) {
        return firstTank.getX() == x && firstTank.getY() == y;
    }
    public boolean secondTankExist(int x, int y) {
        if (secondTank != null) {
            return secondTank.getX() == x && secondTank.getY() == y;
        }
        return false;
    }
    public boolean enemyTankExist(int x, int y) {
        return enemyTankList.stream().anyMatch(tank -> tank.getX() == x && tank.getY() == y);
    }

    public boolean canFire(Tank tank) {
        int myY = firstTank.getY();
        boolean inRangeX;
        boolean inRangeY;
        int sideX = tank.getX() + tank.getdX();
        int sideY = tank.getY() + tank.getdY();
        while (true) {
            if (isInSteel(sideX, tank.getY())) {
                inRangeX = false;
                break;
            } else if (firstTankExist(sideX, tank.getY())) {
                inRangeX = true;
                break;
            } else if (isInBrick(sideX, tank.getY())) {
                inRangeX = false;
                break;
            } else if (!isInBoundary(sideX, tank.getY())) {
                inRangeX = false;
                break;
            } else if (tank.getdX() == 0) {
                inRangeX = false;
                break;
            }
            sideX += tank.getdX();
        }
        while (true) {
            if (isInSteel(tank.getX(), sideY)) {
                inRangeY = false;
                break;
            } else if (firstTankExist(tank.getX(), sideY)) {
                inRangeY = true;
                break;
            } else if (isInBrick(tank.getX(), sideY)) {
                inRangeY = false;
                break;
            } else if (!isInBoundary(tank.getX(), sideY)) {
                inRangeY = false;
                break;
            } else if (tank.getdY() == 0) {
                inRangeY = false;
                break;
            }
            sideY += tank.getdY();
        }
        return inRangeX || inRangeY;
    }

    public List<Integer> calculateEnemyMove(Tank tank) {
        int myX = firstTank.getX();
        int myY = firstTank.getY();
        int enemyX = tank.getX();
        int enemyY = tank.getY();
        int rotationAngle = tank.getRotationAngle();
        if (enemyX < myX && enemyY < myY) {
            enemyX += 1;
            rotationAngle = 90 ;
        } else if (enemyX < myX && enemyY > myY) {
            enemyX += 1;
            rotationAngle = 90;
        } else if (enemyX > myX && enemyY < myY) {
            enemyX -= 1;
            rotationAngle = 270;
        } else if (enemyX > myX && enemyY > myY) {
            enemyX -= 1;
            rotationAngle = 270;
        } else if (enemyX == myX && enemyY > myY) {
            enemyY -= 1;
            rotationAngle = 180;
        } else if (enemyX > myX && enemyY == myY) {
            enemyX -= 1;
            rotationAngle = 270;
        } else if (enemyX == myX && enemyY < myY) {
            enemyY += 1;
            rotationAngle = 0;
        } else if (enemyX < myX && enemyY == myY) {
            enemyX += 1;
            rotationAngle = 90;
        }
        List<Integer> position = new ArrayList<>();
        position.add(enemyX);
        position.add(enemyY);
        position.add(rotationAngle);
        return position;
    }

    public boolean isInBush(int x, int y) {
        return bushList.stream().anyMatch(bush -> bush.getX() == x && bush.getY() == y);
    }

    public boolean isInSteel(int x, int y) {
        return steelList.stream().anyMatch(steel -> steel.getX() == x && steel.getY() == y);
    }

    public boolean isInBrick(int x, int y) {
        return brickList.stream().anyMatch(brick -> brick.getX() == x && brick.getY() == y);
    }

    public boolean isInBoundary(int x, int y) {
        return 0 <= x && x < size && 0 <= y && y < size;
    }

    public void moveBullet() {
        try {
            bulletToRemove.clear();
            for (Bullet bullet : bulletList) {
                if (!isInBoundary(bullet.getX(), bullet.getY())) {
                    bulletToRemove.add(bullet);
                } else if (isInSteel(bullet.getX(), bullet.getY())) {
                    bulletToRemove.add(bullet);
                } else if (isInBrick(bullet.getX(), bullet.getY())) {
                    bulletToRemove.add(bullet);
                    brickToRemove.add(brickList.stream().filter(brick -> brick.getX() == bullet.getX() && brick.getY() == bullet.getY())
                            .findFirst().orElse(null));
                } else if (firstTankExist(bullet.getX(), bullet.getY())) {
                    bulletToRemove.add(bullet);
                    showMessageDialog(null, "Game Over. Player 2 wins.");
                } else if (secondTankExist(bullet.getX(), bullet.getY())) {
                    bulletToRemove.add(bullet);
                    showMessageDialog(null, "Game Over. Player 1 wins.");
                    isOver = true;
                } else if (enemyTankExist(bullet.getX(), bullet.getY())) {
                    bulletToRemove.add(bullet);
                    showMessageDialog(null, "Game Over. Player wins.");
                    isOver = true;
                } else {
                    bullet.move();
                }
            }
            for (Bullet bullet : bulletToRemove) {
                bulletList.remove(bullet);
            }
            for (Brick brick : brickToRemove) {
                brickList.remove(brick);
            }
        } catch (ConcurrentModificationException e) {
            System.out.println(e);
        }
    }

    public void moveEnemy() {
        int myX = firstTank.getX();
        int myY = firstTank.getY();
        for (Tank t: enemyTankList) {
            if (canMove(t)) {
                int enemyX = t.getX();
                int enemyY = t.getY();
                if (enemyX < myX && enemyY < myY) {
                    enemyX += 1;
                } else if (enemyX < myX && enemyY > myY) {
                    enemyX += 1;
                } else if (enemyX > myX && enemyY < myY) {
                    enemyX -= 1;
                } else if (enemyX > myX && enemyY > myY) {
                    enemyX -= 1;
                } else if (enemyX == myX && enemyY > myY) {
                    enemyY -= 1;
                } else if (enemyX > myX && enemyY == myY) {
                    enemyX -= 1;
                } else if (enemyX == myX && enemyY < myY) {
                    enemyY += 1;
                } else if (enemyX < myX && enemyY == myY) {
                    enemyX += 1;
                }
                if (canEnemyMove(enemyX, enemyY)) {
                    t.setPosition(enemyX, enemyY);
                }

            }
        }
    }

    public List<Tank> getEnemyTankList() {
        return enemyTankList;
    }

    public List<Bush> getBushList() {
        return bushList;
    }

    public List<Brick> getBrickList() {
        return brickList;
    }

    public List<Steel> getSteelList() {
        return steelList;
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }

    public Tank getFirstTank() {
        return firstTank;
    }

    public Tank getSecondTank() {
        return secondTank;
    }
    
    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }
        
    public boolean getIsStart() {
        return isStart;
    }

    public boolean getIsOver() {
        return isOver;
    }

    public void setIsStart(boolean status) {
        isStart = status;
    }
}
