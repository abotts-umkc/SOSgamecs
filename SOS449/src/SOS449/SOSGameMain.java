package SOS449;

public class SOSGameMain {
    public static void main(String[] args) {
        SOSGameController controller = new SOSGameController();
        SOSGameView view = new SOSGameView(controller);

        controller.setView(view);
    }
}
