package Commands.Receiver_Invoker;

import Commands.ICommand;

public class Invoker {
    private static final Invoker invoker = new Invoker();

    private Invoker() {
    }

    public void executeGeriAl(ICommand commands) {
        commands.execute();
    }

    public void executeKapat(ICommand commands) {
        commands.execute();
    }

    public void executeKaydet(ICommand commands) {
        commands.execute();
    }

    public void executeYeni(ICommand commands) {
        commands.execute();
    }

    public void executeAc(ICommand commands) {
        commands.execute();
    }

    public static Invoker getInvoker() {
        return invoker;
    }
}
