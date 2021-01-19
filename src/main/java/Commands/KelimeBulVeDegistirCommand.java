package Commands;
import Receiver_Invoker.Receiver;
public class KelimeBulVeDegistirCommand implements Command {
    Receiver receiver;
    public KelimeBulVeDegistirCommand(Receiver thatReceiver){
        this.receiver = thatReceiver;
    }
    @Override
    public void execute(){
        receiver.kelimeBulveDegistir();
    }
}
