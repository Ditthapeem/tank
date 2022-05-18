public class Tank extends WObject {
    private int rotationAngle;
    private boolean show;
    public Tank(int x, int y){
        super(x, y);
        rotationAngle = 0;
        show = true;
    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(int rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
