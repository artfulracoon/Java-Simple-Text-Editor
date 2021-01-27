package Commands;

import Commands.Receiver_Invoker.Receiver;

public class KelimeBulVeDegistirCommand implements ICommand {
    Receiver receiver;

    public KelimeBulVeDegistirCommand(Receiver thatReceiver) {
        this.receiver = thatReceiver;
    }

    @Override
    public void execute() {
        receiver.kelimeBulveDegistir();
    }
}
