package start;
//TODO: package
/**
 * Created by Julia on 2015-10-12.
 */
public class main {
    public static void main(String[] args) {
        SIPHandler sh = new SIPHandler(); int choice = -1;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("State: " + sh.getState());
            System.out.println("0. Quit");
            System.out.println("1. Call");
            System.out.println("2. Pick up");
            System.out.println("3. Hang up");
            System.out.println("4. Accept up");
            choice = scan.nextInt();

            switch(choice) {
                case 1: sh.invokeReceivedInvite(); break;
                case 2: sh.invokeReceivedCall(); break;
                case 3: sh.invokeReceivedEndCall(); break;
                case 4: sh.invokeReceivedBye(); break;
            }
        }while(choice != 0);

    }
}
