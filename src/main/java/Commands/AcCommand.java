package Commands;

import Commands.Receiver_Invoker.Receiver;

public class AcCommand implements ICommand {
    Receiver receiver;

    public AcCommand(Receiver thatReceiver) {
        this.receiver = thatReceiver;
    }

    @Override
    public void execute() {
        receiver.ac();
    }
}

