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
    private SIPthread server;

    public Interface(SIPHandler sh) {
        this.sh = sh;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Socket s = null;
        String ip = "";
        boolean isClient = false;
        SIPthread trad = null;

        do {
            showMessage("state: " + sh.getState());
            switch (sh.getState()) {
                case WAITING:
                    showMessage("type IP to call\nor 0 to exit");
                    break;
                case TALKING:
                    showMessage("press 0 enter to hang up");
                    break;
                default:
                    showMessage("press 0 enter to reset");
                    break;
            }
            ip = scanner.nextLine();
            if(ip.equals("")){
                ip=" ";
            }
            try {
                switch (sh.getState()) {
                    case WAITING:
                        if(ip.equals("0")){
                            System.exit(0);
                        }
                        try {
                            s = new Socket(ip, 4321);
                            trad = new SIPthread(s, sh);
                            trad.start();
                            isClient = true;
                            showMessage("Calling ... ");
                        } catch (UnknownHostException e) {
                            showMessage("The ip you entered is not correct");
                        } catch (IOException e) {
                            showMessage("Could not connect to the ip adress");
                        }
                        break;
                    case TALKING:
                        if (ip.equals("0")) {
                            showMessage("You have pressed hang up");
                            if (isClient) {
                                trad.hangUp();
                                isClient = false;
                            } else {
                                server.hangUp();
                            }
                        }
                        break;
                    default:
                        if (ip.equals("0")) {
                            sh.forceWaiting();
                        }
                }
            } catch (NumberFormatException e) {
                showMessage("You have to write 1 or 2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public synchronized void updateServer(SIPthread s) {
        server = s;
    }

    public synchronized void showMessage(String msg) {
        System.out.println(msg);
    }
}
