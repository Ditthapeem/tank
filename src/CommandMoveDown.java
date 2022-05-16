public class CommandMoveDown extends Command {

    public CommandMoveDown(Tank tank) {
        super(tank);
    }
    @Override
    public void execute() {
        this.getTank().turnSouth();
        this.getTank().setRotationAngle(180);
    }
}
