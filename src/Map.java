import java.util.Arrays;
import java.util.List;

public interface Map {
    public List<List<Integer>> listMapBrick = Arrays.asList();
    public List<List<Integer>> listMapSteel = Arrays.asList();
    public List<List<Integer>> listMapBush = Arrays.asList();
    public List<List<Integer>> listMapFirstTank = Arrays.asList();
    public List<List<Integer>> listMapSecondTank = Arrays.asList();
    public List<List<Integer>> listEnemyTank = Arrays.asList();
    public void addObjectListMap();
    public void addListMapFirstTank();
    public void addListMapSecondTank();
    public void addListEnemyTank();
    public void addListMapBush();
    public void addListMapBrick();
    public void addListMapSteel();
    public List<List<Integer>> getListMapBrick();
    public List<List<Integer>> getListMapSteel();
    public List<List<Integer>> getListMapBush();
    public List<List<Integer>> getListMapFirstTank();
    public List<List<Integer>> getListMapSecondTank();
    public List<List<Integer>> getListEnemyTank();
}
