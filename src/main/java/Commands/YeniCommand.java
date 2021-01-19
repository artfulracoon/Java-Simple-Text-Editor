package Commands;
import Receiver_Invoker.Receiver;
public class YeniCommand implements Command {
    Receiver receiver;
    public YeniCommand(Receiver thatReceiver){
        this.receiver = thatReceiver;
    }
    @Override
    public void execute(){
        receiver.yeni();
    }
}
