package Commands;
import Receiver_Invoker.Receiver;
public class KapatCommand implements Command {
    Receiver receiver;
    public KapatCommand(Receiver thatReceiver){
        this.receiver = thatReceiver;
    }

    @Override
    public void execute(){
        receiver.kapat();
    }
}
