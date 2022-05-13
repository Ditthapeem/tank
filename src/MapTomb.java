import java.util.Arrays;
import java.util.List;

public class MapTomb implements Map {

    public List<List<Integer>> listMapBrick = Arrays.asList();
    public List<List<Integer>> listMapSteel = Arrays.asList();
    public List<List<Integer>> listMapBush = Arrays.asList();

    public List<List<Integer>> listMapMyTank = Arrays.asList();

    public MapTomb() {
        addObjectListMap();
    }

    @Override
    public void addObjectListMap() {
        addListMapBush();
        addListMapBrick();
        addListMapSteel();
        addListMapMyTank();
    }

    @Override
    public void addListMapMyTank() {
        listMapMyTank = Arrays.asList( Arrays.asList(1, 1));
    }

    @Override
    public void addListMapBush() {
        listMapBush = Arrays.asList(    Arrays.asList(3, 1),
                                        Arrays.asList(3, 2),
                                        Arrays.asList(3, 3),
                                        Arrays.asList(3, 4),
                                        Arrays.asList(3, 7),
                                        Arrays.asList(3, 8),
                                        Arrays.asList(3, 9),
                                        Arrays.asList(3, 11),
                                        Arrays.asList(3, 12),
                                        Arrays.asList(5, 1),
                                        Arrays.asList(5, 2),
                                        Arrays.asList(5, 3),
                                        Arrays.asList(5, 4),
                                        Arrays.asList(5, 5),
                                        Arrays.asList(5, 6),
                                        Arrays.asList(5, 9),
                                        Arrays.asList(5, 10),
                                        Arrays.asList(5, 11),
                                        Arrays.asList(5, 12),
                                        Arrays.asList(8, 1),
                                        Arrays.asList(8, 2),
                                        Arrays.asList(8, 3),
                                        Arrays.asList(8, 4),
                                        Arrays.asList(8, 7),
                                        Arrays.asList(8, 8),
                                        Arrays.asList(8, 9),
                                        Arrays.asList(8, 10),
                                        Arrays.asList(8, 11),
                                        Arrays.asList(8, 12),
                                        Arrays.asList(10, 1),
                                        Arrays.asList(10, 2),
                                        Arrays.asList(10, 3),
                                        Arrays.asList(10, 4),
                                        Arrays.asList(10, 5),
                                        Arrays.asList(10, 6),
                                        Arrays.asList(10, 9),
                                        Arrays.asList(10, 10),
                                        Arrays.asList(10, 11),
                                        Arrays.asList(10, 12));
    }

    @Override
    public void addListMapBrick() {
        listMapBrick = Arrays.asList(   Arrays.asList(2, 5),
                                        Arrays.asList(2, 6),
                                        Arrays.asList(2, 7),
                                        Arrays.asList(2, 8),
                                        Arrays.asList(2, 9),
                                        Arrays.asList(2, 10),
                                        Arrays.asList(2, 11),
                                        Arrays.asList(2, 12),
                                        Arrays.asList(11, 1),
                                        Arrays.asList(11, 2),
                                        Arrays.asList(11, 3),
                                        Arrays.asList(11, 4),
                                        Arrays.asList(11, 5),
                                        Arrays.asList(11, 6),
                                        Arrays.asList(11, 7),
                                        Arrays.asList(11, 8));
    }

    @Override
    public void addListMapSteel() {
        listMapSteel = Arrays.asList( Arrays.asList(1, 9),
                Arrays.asList(1, 10),
                Arrays.asList(1, 11),
                Arrays.asList(1, 12),
                Arrays.asList(2, 1),
                Arrays.asList(2, 2),
                Arrays.asList(2, 3),
                Arrays.asList(2, 4),
                Arrays.asList(3, 5),
                Arrays.asList(3, 6),
                Arrays.asList(4, 9),
                Arrays.asList(4, 10),
                Arrays.asList(4, 11),
                Arrays.asList(4, 12),
                Arrays.asList(5, 7),
                Arrays.asList(5, 8),
                Arrays.asList(6, 1),
                Arrays.asList(6, 2),
                Arrays.asList(6, 3),
                Arrays.asList(6, 4),
                Arrays.asList(7, 9),
                Arrays.asList(7, 10),
                Arrays.asList(7, 11),
                Arrays.asList(7, 12),
                Arrays.asList(8, 5),
                Arrays.asList(8, 6),
                Arrays.asList(9, 1),
                Arrays.asList(9, 2),
                Arrays.asList(9, 3),
                Arrays.asList(9, 4),
                Arrays.asList(10, 7),
                Arrays.asList(10, 8),
                Arrays.asList(11, 9),
                Arrays.asList(11, 10),
                Arrays.asList(11, 11),
                Arrays.asList(11, 12),
                Arrays.asList(12, 1),
                Arrays.asList(12, 2),
                Arrays.asList(12, 3),
                Arrays.asList(12, 4));
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
    public List<List<Integer>> getListMapMyTank() {
        return listMapMyTank;
    }
}
