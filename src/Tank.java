public class Tank extends WObject {
    private int rotationAngle;
    public Tank(int x, int y){
        super(x, y);
        rotationAngle = 0;
    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(int rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    // TODO Make a tank can move
    // TODO Make a tank can fire
}
