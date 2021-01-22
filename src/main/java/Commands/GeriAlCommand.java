package Commands;

import Commands.Receiver_Invoker.Receiver;

public class GeriAlCommand implements Command {

    Receiver receiver;

    public GeriAlCommand(Receiver thatReceiver) {
        this.receiver = thatReceiver;
    }

    @Override
    public void execute() {
        receiver.geriAl();
    }
}