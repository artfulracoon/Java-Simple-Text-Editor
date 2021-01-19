package Commands;
import Receiver_Invoker.Receiver;
public class HataliKelimeleriDuzeltCommand implements Command {

    Receiver receiver;
    public HataliKelimeleriDuzeltCommand(Receiver thatReceiver){
        this.receiver = thatReceiver;
    }
    @Override
    public void execute(){
        receiver.hataliKelimeDuzelt();
    }
}
