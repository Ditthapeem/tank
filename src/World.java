import java.util.ArrayList;
import java.util.List;

public class World {

    private Map map;
    private int size;

    private Tank tank;
    private Tank enemyTank;

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

    public void setMap(int x) {
        if (x == 1) {
            this.map = new MapDefault();
            addObjectList();
        } else if(x == 2) {
            this.map = new MapTomb();
            addObjectList();
        }
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
}
