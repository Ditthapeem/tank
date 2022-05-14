public class CommandMoveRight extends Command {

    public CommandMoveRight(Tank tank) {
        super(tank);
    }

    @Override
    public void execute() {
        this.getTank().turnEast();
    }
}
