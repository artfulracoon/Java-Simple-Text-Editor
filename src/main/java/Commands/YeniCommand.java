package Commands;

import Commands.Receiver_Invoker.Receiver;

public class YeniCommand implements ICommand {
    private Receiver receiver;

    public YeniCommand(Receiver thatReceiver) {
        this.setReceiver(thatReceiver);
    }

    @Override
    public void execute() {
        getReceiver().yeni();
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}
