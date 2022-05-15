public abstract class WObject {

    private int x;
    private int y;

    private int dx;
    private int dy;
    // Random random = new Random();
    private boolean isNorth = true;
    private boolean isSouth = false;
    private boolean isWest = true;
    private boolean isEast = true;

    public WObject() {
    }

    public WObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void turnNorth() {
        dx = 0;
        dy = -1;
    }

    public void turnSouth() {
        dx = 0;
        dy = 1;
    }

    public void turnWest() {
        dx = -1;
        dy = 0;
    }

    public void turnEast() {
        dx = 1;
        dy = 0;
    }

    public void move() {
        this.x += dx;
        this.y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getdX() {
        return dx;
    }

    public int getdY() {
        return dy;
    }
    public void setdX(int dx) {
        this.dx = dx;
    }
    public void setdY(int dy) {
        this.dy = dy;
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isNorth() {
        return isNorth;
    }

    public boolean isSouth() {
        return isSouth;
    }

    public boolean isWest() {
        return isWest;
    }

    public boolean isEast() {
        return isEast;
    }
}