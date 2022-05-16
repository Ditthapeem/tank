public class CommandMoveLeft extends Command {

    public CommandMoveLeft(Tank tank) {
        super(tank);
    }
    @Override
    public void execute() {
        this.getTank().turnWest();
        this.getTank().setRotationAngle(270);
    }
}
