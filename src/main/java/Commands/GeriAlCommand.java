package Commands;

import Commands.Receiver_Invoker.Receiver;

public class GeriAlCommand implements ICommand {

    private Receiver receiver;

    public GeriAlCommand(Receiver thatReceiver) {
        this.setReceiver(thatReceiver);
    }

    @Override
    public void execute() {
        getReceiver().geriAl();
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}