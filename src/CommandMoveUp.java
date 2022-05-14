public class CommandMoveUp extends Command {

    public CommandMoveUp(Tank tank) {
        super(tank);
    }

    @Override
    public void execute() {
        this.getTank().turnNorth();
    }

}
