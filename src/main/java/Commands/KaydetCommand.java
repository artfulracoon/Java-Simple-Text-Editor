package Commands;
import Receiver_Invoker.Receiver;
public class KaydetCommand implements Command {
    Receiver receiver;
    public KaydetCommand(Receiver thatReceiver){
        this.receiver = thatReceiver;
    }
    @Override
    public void execute(){
        receiver.kaydet();
    }
}
