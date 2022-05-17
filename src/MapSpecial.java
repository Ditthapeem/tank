import java.util.Arrays;
import java.util.List;

public class MapSpecial implements Map {

    public List<List<Integer>> listMapBrick = Arrays.asList();
    public List<List<Integer>> listMapSteel = Arrays.asList();
    public List<List<Integer>> listMapBush = Arrays.asList();

    public List<List<Integer>> listMapFirstTank = Arrays.asList();

    public List<List<Integer>> listMapSecondTank = Arrays.asList();

    public List<List<Integer>> listEnemyTank = Arrays.asList();

    public MapSpecial() {
        addObjectListMap();
    }

    @Override
    public void addObjectListMap() {
        addListMapBush();
        addListMapBrick();
        addListMapSteel();
        addListMapFirstTank();
        addListMapSecondTank();
        addListEnemyTank();
    }

    @Override
    public void addListMapFirstTank() {
        listMapFirstTank = Arrays.asList( Arrays.asList(3, 1));
    }

    @Override
    public void addListMapSecondTank() {
        listMapSecondTank = Arrays.asList(Arrays.asList(10, 12));
    }

    @Override
    public void addListEnemyTank() {
        listEnemyTank = Arrays.asList(  Arrays.asList(10, 12),
                                        Arrays.asList(5, 1),
                                        Arrays.asList(12, 1));
    }

    @Override
    public void addListMapBush() {
        listMapBush  = Arrays.asList(   Arrays.asList(2, 5),
                                        Arrays.asList(2, 6),
                                        Arrays.asList(2, 7),
                                        Arrays.asList(2, 8),
                                        Arrays.asList(11, 5),
                                        Arrays.asList(11, 6),
                                        Arrays.asList(11, 7),
                                        Arrays.asList(11, 8),
                                        Arrays.asList(5, 2),
                                        Arrays.asList(6, 2),
                                        Arrays.asList(7, 2),
                                        Arrays.asList(8, 2),
                                        Arrays.asList(5, 11),
                                        Arrays.asList(6, 11),
                                        Arrays.asList(7, 11),
                                        Arrays.asList(8, 11));
    }

    @Override
    public void addListMapBrick() {
        listMapBrick = Arrays.asList(   Arrays.asList(5, 3),
                                        Arrays.asList(5, 4),
                                        Arrays.asList(5, 9),
                                        Arrays.asList(5, 10),
                                        Arrays.asList(4, 5),
                                        Arrays.asList(4, 6),
                                        Arrays.asList(4, 7),
                                        Arrays.asList(4, 8),
                                        Arrays.asList(8, 3),
                                        Arrays.asList(8, 4),
                                        Arrays.asList(8, 9),
                                        Arrays.asList(8, 10),
                                        Arrays.asList(9, 5),
                                        Arrays.asList(9, 6),
                                        Arrays.asList(9, 7),
                                        Arrays.asList(9, 8));
    }

    @Override
    public void addListMapSteel() {
        listMapSteel = Arrays.asList(   Arrays.asList(4, 1),
                                        Arrays.asList(2, 2),
                                        Arrays.asList(3, 2),
                                        Arrays.asList(4, 2),
                                        Arrays.asList(9, 2),
                                        Arrays.asList(10, 2),
                                        Arrays.asList(11, 2),
                                        Arrays.asList(2, 3),
                                        Arrays.asList(11, 2),
                                        Arrays.asList(2, 4),
                                        Arrays.asList(11, 4),
                                        Arrays.asList(2, 11),
                                        Arrays.asList(3, 11),
                                        Arrays.asList(4, 11),
                                        Arrays.asList(9, 11),
                                        Arrays.asList(10, 11),
                                        Arrays.asList(11, 11),
                                        Arrays.asList(2, 9),
                                        Arrays.asList(2, 10),
                                        Arrays.asList(11, 9),
                                        Arrays.asList(11, 10),
                                        Arrays.asList(9, 12),
                                        Arrays.asList(5, 5),
                                        Arrays.asList(5, 8),
                                        Arrays.asList(6, 7),
                                        Arrays.asList(6, 8),
                                        Arrays.asList(6, 9),
                                        Arrays.asList(7, 4),
                                        Arrays.asList(7, 5),
                                        Arrays.asList(7, 6),
                                        Arrays.asList(8, 5),
                                        Arrays.asList(8, 8));
    }

    @Override
    public List<List<Integer>> getListMapBrick() {
        return listMapBrick;
    }

    @Override
    public List<List<Integer>> getListMapSteel() {
        return listMapSteel;
    }

    @Override
    public List<List<Integer>> getListMapBush() {
        return listMapBush;
    }

    @Override
    public List<List<Integer>> getListMapFirstTank() {
        return listMapFirstTank;
    }

    @Override
    public List<List<Integer>> getListMapSecondTank() {
        return listMapSecondTank;
    }

    @Override
    public List<List<Integer>> getListEnemyTank() {
        return listEnemyTank;
    }
}
