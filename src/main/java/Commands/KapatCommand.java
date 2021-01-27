package Commands;

import Commands.Receiver_Invoker.Receiver;

public class KapatCommand implements ICommand {
    Receiver receiver;

    public KapatCommand(Receiver thatReceiver) {
        this.receiver = thatReceiver;
    }

    @Override
    public void execute() {
        receiver.kapat();
    }
}
