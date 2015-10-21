package start;

import SIP.SIPHandler;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Julia on 2015-10-13.
 */
public class Interface extends Thread {
    private SIPHandler sh;

    public Interface(SIPHandler sh) {
        this.sh = sh;
    }

    @Override
    public void run() {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        Socket s=null;
        SIPthread trad=null;
       // boolean call = false;
        do {
            //TODO should be checking current state
            showMessage("type 0 to quit");
            //TODO: vänta på att personen ska svara och vänta på att starta upp audiostream
            switch (sh.getState()){
                case WAITING:
                    showMessage("type IP to call");
                    break;
                case TALKING:
                    showMessage("press enter to hang up");
                    break;
                default:
                    break;
            }
            //TODO: sent bye
            String ip = scanner.nextLine();
            try {
                //choice = Integer.parseInt(sentence);
                switch (sh.getState()) {
                    case WAITING:
                       // showMessage("Write which ip you want to call.");
                       // String invite_msg = scanner.nextLine();
                        try {
                             s = new Socket(ip, 4321);
                             trad = new SIPthread(s, false, sh,this);
                            trad.start();
                            trad.call();

                            showMessage("Calling ... ");
                           // sh.invokeReceivedInvite();

                            //call = true;
                        } catch (UnknownHostException e) {
                            showMessage("The ip you entered is not correct");
                            //  e.printStackTrace();
                        } catch (IOException e) {
                            showMessage("Could not connect to the ip adress");
                            //e.printStackTrace();
                        }
                        break;
                    case TALKING:
                        //if (call) {
                            //sh.invokeReceivedEndCall();
                            sh.callEnded();
                            trad.hangUp();
                        //    call = false;
                        //}
                        break;
                }
            } catch (NumberFormatException e) {
                showMessage("You have to write 1 or 2");
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        } while (choice != 0);
        System.exit(0);
    }

    public synchronized void showMessage(String msg) {
        System.out.println(msg);
    }
}
