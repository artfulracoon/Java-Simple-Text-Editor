package Commands;

import Commands.Receiver_Invoker.Receiver;

public class YeniCommand implements ICommand {
    Receiver receiver;

    public YeniCommand(Receiver thatReceiver) {
        this.receiver = thatReceiver;
    }

    @Override
    public void execute() {
        receiver.yeni();
    }
}
