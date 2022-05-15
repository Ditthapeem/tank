import java.util.ArrayList;
import java.util.List;

public class World {

    private Map map;
    private int size;

    private Tank tank;
    private Tank enemyTank;

    private int player1Score = 0;
    private int player2Score = 0;

    private List<Bush> bushList;
    private List<Brick> brickList;
    private List<Steel> steelList;

    public World(int size) {
        this.size = size;
        bushList = new ArrayList<Bush>();
        brickList = new ArrayList<Brick>();
        steelList = new ArrayList<Steel>();
    }

    private void addObjectList() {
        addBushList();
        addBrickList();
        addSteelList();
        addMyTank();
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

    private void addMyTank() {
        List<List<Integer>> myTank = this.map.getListMapMyTank();
        for (List<Integer> t: myTank) {
            tank = new Tank(t.get(0)-1, t.get(1)-1);
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

    public Tank getTank() {
        return tank;
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
}
