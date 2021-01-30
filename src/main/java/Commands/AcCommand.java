package Commands;

import Commands.Receiver_Invoker.Receiver;

public class AcCommand implements ICommand {
    private Receiver receiver;

    public AcCommand(Receiver thatReceiver) {
        this.setReceiver(thatReceiver);
    }

    @Override
    public void execute() {
        getReceiver().ac();
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}

