public class Bullet extends WObject {
    private int rotationAngle;
    public Bullet(int x, int y) {
        super(x, y);
        rotationAngle = 0;
    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(int rotationAngle) {
        this.rotationAngle = rotationAngle;
    }
}
