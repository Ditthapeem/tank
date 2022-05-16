import java.util.ArrayList;
import java.util.List;

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
    private boolean isStart;
    private boolean isOver;

    public World(int size) {
        this.size = size;
        isOver = false;
        bushList = new ArrayList<Bush>();
        brickList = new ArrayList<Brick>();
        steelList = new ArrayList<Steel>();
        bulletList = new ArrayList<Bullet>();
        bulletToRemove = new ArrayList<Bullet>();
        brickToRemove = new ArrayList<Brick>();
    }

    public void addObjectList() {
        addBushList();
        addBrickList();
        addSteelList();
        addFirstTank();
        addSecondTank();
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

    public void addBullet(Tank srcTank) {
        Bullet newBullet = new Bullet(srcTank.getX() + srcTank.getdX(), srcTank.getY() + srcTank.getdY());
        newBullet.setdX(srcTank.getdX());
        newBullet.setdY(srcTank.getdY());
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

    public boolean canMove(WObject obj) {
        int newX = obj.getX() + obj.getdX();
        int newY = obj.getY() + obj.getdY();
        return isInBoundary(newX, newY) && !isInBrick(newX, newY) && !isInSteel(newX, newY) && !cellOccupied(newX, newY);
    }

    public boolean cellOccupied(int x, int y) {
        return firstTank.getX() == x && firstTank.getY() == y || secondTank.getX() == x && secondTank.getY() == y;
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

    public void move() {
        bulletToRemove.clear();
        for (Bullet bullet: bulletList) {
            if (!isInBoundary(bullet.getX(), bullet.getY())) {
                bulletToRemove.add(bullet);
            } else if (isInSteel(bullet.getX(), bullet.getY())) {
                bulletToRemove.add(bullet);
            } else if (isInBrick(bullet.getX(), bullet.getY())) {
                bulletToRemove.add(bullet);
                brickToRemove.add(brickList.stream().filter(brick -> brick.getX() == bullet.getX() && brick.getY() == bullet.getY())
                                    .findFirst().orElse(null));
            } else {
                bullet.move();
            }
        }
        for (Bullet bullet: bulletToRemove) {
            bulletList.remove(bullet);
        }
        for (Brick brick: brickToRemove) {
            brickList.remove(brick);
        }
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

    public int getSize() {
        return size;
    }
}
