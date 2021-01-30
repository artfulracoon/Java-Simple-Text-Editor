package Commands;

import Commands.Receiver_Invoker.Receiver;

public class KapatCommand implements ICommand {
    private Receiver receiver;

    public KapatCommand(Receiver thatReceiver) {
        this.setReceiver(thatReceiver);
    }

    @Override
    public void execute() {
        getReceiver().kapat();
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}
