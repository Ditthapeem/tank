import java.util.List;

public interface Map {
    public void addObjectListMap();
    public void addListMapFirstTank();
    public void addListMapSecondTank();
    public void addListMapBush();
    public void addListMapBrick();
    public void addListMapSteel();
    public List<List<Integer>> getListMapBrick();
    public List<List<Integer>> getListMapSteel();
    public List<List<Integer>> getListMapBush();
    public List<List<Integer>> getListMapFirstTank();
    public List<List<Integer>> getListMapSecondTank();
}
