package FunctionCommands;

import Commands.ICommand;
import Commands.Receiver_Invoker.Receiver;

public class KaydetCommand implements ICommand {
    private Receiver receiver;

    public KaydetCommand(Receiver thatReceiver) {
        this.setReceiver(thatReceiver);
    }

    @Override
    public void execute() {
        getReceiver().kaydet();
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}
